package eu.kanade.tachiyomi.data.updater

import eu.kanade.tachiyomi.network.GET
import eu.kanade.tachiyomi.network.NetworkHelper
import eu.kanade.tachiyomi.network.awaitSuccess
import eu.kanade.tachiyomi.network.parseAs
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.CacheControl
import tachiyomi.core.common.preference.Preference
import tachiyomi.core.common.preference.PreferenceStore
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.net.URI

/**
 * Read-only checker for published KomiX stable releases. Updates are never downloaded or installed
 * by this checker; the user chooses whether to open the release page.
 */
class KomiXUpdateChecker(
    private val network: NetworkHelper = Injekt.get(),
    private val json: Json = Injekt.get(),
    preferenceStore: PreferenceStore = Injekt.get(),
) {
    private val lastCheck = preferenceStore.getLong(LAST_CHECK_KEY, 0L)
    private val dismissedRelease = preferenceStore.getString(DISMISSED_RELEASE_KEY, "")

    suspend fun checkForUpdate(currentVersion: String, forceCheck: Boolean = false): KomiXRelease? {
        val now = System.currentTimeMillis()
        if (!forceCheck && now - lastCheck.get() < CHECK_INTERVAL_MS) return null

        // Throttle failed as well as successful attempts so repeated launches while offline or
        // rate-limited do not make a request on every foreground transition.
        lastCheck.set(now)

        val response = network.client.newCall(
            GET(
                url = LATEST_RELEASE_API,
                cache = CacheControl.Builder().noCache().build(),
            ),
        ).awaitSuccess().use { with(json) { it.parseAs<GitHubReleaseResponse>() } }

        if (response.draft || response.preRelease) return null
        if (!isNewerVersion(currentVersion, response.tagName)) return null
        if (!isOfficialReleaseUrl(response.htmlUrl)) return null

        val version = response.tagName.removePrefix("v").removePrefix("V")
        return KomiXRelease(
            tag = response.tagName,
            version = version,
            changelog = response.body.orEmpty().trim(),
            releaseUrl = response.htmlUrl,
        )
    }

    fun wasDismissed(tag: String): Boolean = dismissedRelease.get() == tag

    fun dismiss(tag: String) {
        dismissedRelease.set(tag)
    }

    private fun isOfficialReleaseUrl(value: String): Boolean = runCatching {
        val uri = URI(value)
        uri.scheme == "https" &&
            uri.host.equals("github.com", ignoreCase = true) &&
            uri.path.startsWith("/aryannayak-real/KomiX/releases/")
    }.getOrDefault(false)

    companion object {
        private const val LATEST_RELEASE_API =
            "https://api.github.com/repos/aryannayak-real/KomiX/releases/latest"
        private const val LAST_CHECK_KEY = "komix_last_release_check"
        private const val DISMISSED_RELEASE_KEY = "komix_dismissed_release"
        private const val CHECK_INTERVAL_MS = 6 * 60 * 60 * 1000L

        private val stableVersionRegex = Regex("^[vV]?(\\d+(?:\\.\\d+)*)$")

        internal fun isNewerVersion(currentVersion: String, latestVersion: String): Boolean {
            val current = parseStableVersion(currentVersion) ?: return false
            val latest = parseStableVersion(latestVersion) ?: return false

            for (index in 0 until maxOf(current.size, latest.size)) {
                val currentPart = current.getOrElse(index) { 0 }
                val latestPart = latest.getOrElse(index) { 0 }
                if (latestPart != currentPart) return latestPart > currentPart
            }
            return false
        }

        private fun parseStableVersion(value: String): List<Int>? {
            val match = stableVersionRegex.matchEntire(value.trim()) ?: return null
            return match.groupValues[1].split('.').map { it.toIntOrNull() ?: return null }
        }
    }
}

data class KomiXRelease(
    val tag: String,
    val version: String,
    val changelog: String,
    val releaseUrl: String,
)

@Serializable
private data class GitHubReleaseResponse(
    @SerialName("tag_name")
    val tagName: String,
    @SerialName("html_url")
    val htmlUrl: String,
    val body: String? = null,
    @SerialName("prerelease")
    val preRelease: Boolean = false,
    val draft: Boolean = false,
)
