import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "reminders")

class ReminderPreferences(private val context: Context) {

    private val REMINDERS_KEY = stringPreferencesKey("reminders_list")

    val reminders: Flow<List<String>> = context.dataStore.data
        .map { preferences -> preferences[REMINDERS_KEY]?.split(";") ?: emptyList() }

    suspend fun addReminder(reminder: String) {
        context.dataStore.edit { preferences ->
            val updatedReminders = preferences[REMINDERS_KEY]?.split(";")?.toMutableList() ?: mutableListOf()
            updatedReminders.add(reminder)
            preferences[REMINDERS_KEY] = updatedReminders.joinToString(";")
        }
    }

    suspend fun removeReminder(reminder: String) {
        context.dataStore.edit { preferences ->
            val updatedReminders = preferences[REMINDERS_KEY]?.split(";")?.toMutableList() ?: mutableListOf()
            updatedReminders.remove(reminder)
            preferences[REMINDERS_KEY] = updatedReminders.joinToString(";")
        }
    }
}
