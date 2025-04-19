package labs.alexandre.datero.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimestampSnapshotManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val KEY_ELAPSED_SINCE_BOOT = longPreferencesKey("elapsedSinceBoot")
        val KEY_SYSTEM_TIMESTAMP_BOOT = longPreferencesKey("systemTimestampBoot")
    }

    suspend fun getElapsedSinceBoot(): Long = dataStore
        .data
        .first()[KEY_ELAPSED_SINCE_BOOT] ?: 0L

    suspend fun getSystemTimestampBoot(): Long = dataStore
        .data
        .first()[KEY_SYSTEM_TIMESTAMP_BOOT] ?: 0L

    suspend fun setElapsedSinceBoot(elapsedSinceBoot: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_ELAPSED_SINCE_BOOT] = elapsedSinceBoot
        }
    }

    suspend fun setSystemTimestampBoot(systemTimestampBoot: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_SYSTEM_TIMESTAMP_BOOT] = systemTimestampBoot
        }
    }

}