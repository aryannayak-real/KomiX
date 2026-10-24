package eu.kanade.tachiyomi.data.updater

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class KomiXUpdateCheckerTest {

    @Test
    fun `detects a newer stable semantic version`() {
        assertTrue(KomiXUpdateChecker.isNewerVersion("1.14.2", "v1.14.3"))
        assertTrue(KomiXUpdateChecker.isNewerVersion("1.14", "1.14.1"))
    }

    @Test
    fun `does not treat current or older releases as updates`() {
        assertFalse(KomiXUpdateChecker.isNewerVersion("1.14.3", "1.14.3"))
        assertFalse(KomiXUpdateChecker.isNewerVersion("1.14.3", "1.14.2"))
    }

    @Test
    fun `ignores malformed and prerelease versions`() {
        assertFalse(KomiXUpdateChecker.isNewerVersion("not-a-version", "1.14.4"))
        assertFalse(KomiXUpdateChecker.isNewerVersion("1.14.3", "1.14.4-rc.1"))
    }
}
