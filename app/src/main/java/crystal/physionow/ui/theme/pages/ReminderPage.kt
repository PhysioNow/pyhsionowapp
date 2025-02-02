import android.app.Application
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import crystal.physionow.datastore.ReminderScheduler
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun ReminderPage(viewModel: ReminderViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    val scheduler = remember { ReminderScheduler(context) }
    var newReminder by remember { mutableStateOf("") }
    val reminders by viewModel.reminders.collectAsState(initial = emptyList())
    var selectedTime by remember { mutableStateOf(Calendar.getInstance()) }
    var selectedDays by remember { mutableStateOf(setOf<Int>()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = newReminder,
            onValueChange = { newReminder = it },
            label = { Text("Neue Erinnerung") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        selectedTime.set(Calendar.HOUR_OF_DAY, hour)
                        selectedTime.set(Calendar.MINUTE, minute)
                    },
                    selectedTime.get(Calendar.HOUR_OF_DAY),
                    selectedTime.get(Calendar.MINUTE),
                    true
                ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Uhrzeit auswählen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            listOf(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY).forEach { day ->
                Button(
                    onClick = {
                        selectedDays = if (selectedDays.contains(day)) selectedDays - day else selectedDays + day
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedDays.contains(day)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(dayToString(day))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newReminder.isNotEmpty() && selectedDays.isNotEmpty()) {
                    val reminderText = "$newReminder - ${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}"
                    viewModel.addReminder(reminderText)
                    scheduler.scheduleReminder(newReminder, selectedTime.get(Calendar.HOUR_OF_DAY), selectedTime.get(Calendar.MINUTE), selectedDays.toList())
                    newReminder = ""
                    Toast.makeText(context, "Erinnerung gespeichert!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hinzufügen")
        }

        LazyColumn {
            items(reminders) { reminder ->
                ReminderItem(reminder, viewModel::removeReminder)
            }
        }
    }
}

fun dayToString(day: Int): String {
    return when (day) {
        Calendar.MONDAY -> "Mo"
        Calendar.TUESDAY -> "Di"
        Calendar.WEDNESDAY -> "Mi"
        Calendar.THURSDAY -> "Do"
        Calendar.FRIDAY -> "Fr"
        Calendar.SATURDAY -> "Sa"
        Calendar.SUNDAY -> "So"
        else -> ""
    }
}

class ReminderViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = ReminderPreferences(application)

    val reminders = preferences.reminders

    fun addReminder(reminder: String) {
        viewModelScope.launch { preferences.addReminder(reminder) }
    }

    fun removeReminder(reminder: String) {
        viewModelScope.launch { preferences.removeReminder(reminder) }
    }
}

@Composable
fun ReminderItem(reminder: String, onDelete: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(reminder, modifier = Modifier.weight(1f))

            Button(onClick = { onDelete(reminder) }) {
                Text("Löschen")
            }
        }
    }
}


//TODO: App stürzt beim Hinzufügen ab...

