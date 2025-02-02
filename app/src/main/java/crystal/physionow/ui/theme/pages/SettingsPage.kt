package crystal.physionow.ui.theme.pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import crystal.physionow.java.Variable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(navController: NavController) {
    val context = LocalContext.current
    var showPasswordDialog by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen (Einstellungen sind noch nicht fertig implementiert)") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Erste Einstellungsoption
                SettingOptionRow(
                    label = "Benachrichtigungen",
                    value = "Aktiviert",
                    onClick = { /* Hier könntest du eine Aktion hinzufügen */ }
                )

                // Zweite Einstellungsoption (neu hinzugefügt)
                SettingOptionRow(
                    label = "Erinnerungen",
                    value = "Mehr anzeigen",
                    onClick = { navController.navigate("reminder") }
                )

                // Größerer Abstand vor dem Entwickler-Button
                Spacer(modifier = Modifier.height(32.dp))

                // Entwicklereinstellungen-Button weiter unten
                Button(
                    onClick = { showPasswordDialog = true },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Entwicklereinstellungen")
                }
            }
        },
        bottomBar = {
            BottomAppBar {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Impressum",
                    modifier = Modifier
                        .clickable {
                            navController.navigate("impressum")
                        }
                        .padding(end = 16.dp)
                )
            }
        }
    )

    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showPasswordDialog = false },
            title = { Text("Passwort eingeben") },
            text = {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Passwort") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (password == "" + Variable.devpassword) {
                        navController.navigate("devsettings")
                        showPasswordDialog = false
                    } else {
                        Toast.makeText(context, "Falsches Passwort!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Bestätigen")
                }
            },
            dismissButton = {
                Button(onClick = { showPasswordDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }
}

@Composable
fun SettingOptionRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp)
        )
    }

}