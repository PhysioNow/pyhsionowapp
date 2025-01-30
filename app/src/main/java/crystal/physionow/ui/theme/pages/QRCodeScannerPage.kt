package crystal.physionow.ui.theme.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRCodeScannerPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scanne dein Dokument hier!") }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(500.dp)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "[ QR Code Scanner View ] \nDie Kamera Ansicht ist noch nicht implementiert!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.background(Color(0xFFFFFFFF)) // Beigeton auch für die untere Leiste
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { /* Do nothing */ }) {
                    Text("Scan")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    )
}
