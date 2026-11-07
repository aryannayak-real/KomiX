package tachiyomi.source.local.image

import android.content.Context
import com.hippo.unifile.UniFile
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.util.storage.DiskUtil
import mihon.core.archive.ZipWriter
import tachiyomi.core.common.storage.nameWithoutExtension
import tachiyomi.core.common.util.system.ImageUtil
import tachiyomi.source.local.io.LocalSourceFileSystem
import java.io.InputStream

private const val DEFAULT_COVER_NAME = "cover.jpg"
private const val COVER_ARCHIVE_NAME = "cover.cbi"

actual class LocalCoverManager(
    private val context: Context,
    private val fileSystem: LocalSourceFileSystem,
) {
    actual fun find(mangaUrl: String): UniFile? {
        return fileSystem.getFilesInMangaDirectory(mangaUrl)
            .filter { it.isFile && it.nameWithoutExtension.equals("cover", ignoreCase = true) }
            .firstOrNull {
                ImageUtil.isImage(it.name) { it.openInputStream() } || it.name == COVER_ARCHIVE_NAME
            }
    }

    actual fun update(
        manga: SManga,
        inputStream: InputStream,
        encrypted: Boolean,
    ): UniFile? {
        return inputStream.use { input ->
            val directory = fileSystem.getMangaDirectory(manga.url) ?: return@use null

            val targetFile = fileSystem.getFilesInMangaDirectory(manga.url)
                .firstOrNull {
                    it.isFile &&
                        it.nameWithoutExtension.equals("cover", ignoreCase = true) &&
                        if (encrypted) {
                            it.name == COVER_ARCHIVE_NAME
                        } else {
                            it.name != COVER_ARCHIVE_NAME &&
                                ImageUtil.isImage(it.name) { it.openInputStream() }
                        }
                }
                ?: directory.createFile(if (encrypted) COVER_ARCHIVE_NAME else DEFAULT_COVER_NAME)
                ?: return@use null

            if (encrypted) {
                ZipWriter(context, targetFile, encrypt = true).use { writer ->
                    writer.write(input.readBytes(), DEFAULT_COVER_NAME)
                }
            } else {
                targetFile.openOutputStream().use { output ->
                    input.copyTo(output)
                }
            }

            fileSystem.getFilesInMangaDirectory(manga.url)
                .filter {
                    it.isFile &&
                        it.nameWithoutExtension.equals("cover", ignoreCase = true) &&
                        if (encrypted) {
                            it.name != COVER_ARCHIVE_NAME
                        } else {
                            it.name == COVER_ARCHIVE_NAME
                        }
                }
                .forEach { it.delete() }

            DiskUtil.createNoMediaFile(directory, context)
            manga.thumbnail_url = targetFile.uri.toString()
            targetFile
        }
    }
}
