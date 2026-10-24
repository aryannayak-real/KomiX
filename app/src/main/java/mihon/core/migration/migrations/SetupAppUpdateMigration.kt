package mihon.core.migration.migrations

import android.app.Application
import eu.kanade.tachiyomi.data.updater.AppUpdateJob
import mihon.core.migration.Migration
import mihon.core.migration.MigrationContext

class SetupAppUpdateMigration : Migration {
    override val version: Float = Migration.ALWAYS

    override suspend fun invoke(migrationContext: MigrationContext): Boolean {
        val context = migrationContext.get<Application>() ?: return false
        // Automatic app version checks now use the KomiX release page at launch. Retire any
        // previously scheduled checker for the inherited upstream repository.
        AppUpdateJob.cancelTask(context)
        return true
    }
}
