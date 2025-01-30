package crystal.physionow.ui.theme.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import crystal.physionow.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpressumPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Impressum", style = MaterialTheme.typography.headlineSmall) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Text-Inhalt
                    Text(
                        text = "An Android Application developed by Jannik Wappler \nSternenwinkel 5\n04821 Brandis\nDeutschland\nTel: +49 176 51079224 \n \nGrundidee: \nPhysioNow ist eine App, die Physiotherapie digitalisieren soll. Es gibt viele Funktionen, wie zum Beispiel, einen QR-Code Scanner, mit welchem man sein Dokument einscannen kann, und einem direkt passende Übungen angezeigt werden. Außerdem eine Funktion um Gemini nach passenden Übungen zu fragen. \n \n © Copyright by Jannik Wappler \ndarunter Zählt: Idee der App, jegliche Bilder und Struktur. SourceCode unter crystal.physionow ist im geistigen Eigentum von Jannik Wappler \n \n      © 2025 PhysioNow. Alle Rechte Vorbehalten.",
                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Größere quadratische Card für das Bild
                    Box(
                        modifier = Modifier
                            .size(200.dp) // Größere quadratische Größe für die Card
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp), // Innenabstand für das Bild
                        contentAlignment = Alignment.Center
                    ) {
                        // Bild in einer größeren Card mit Schatten
                        Card(
                            modifier = Modifier
                                .fillMaxSize(), // Füllt die gesamte Box aus
                            shape = RoundedCornerShape(24.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.physionowlogo),
                                contentDescription = "PhysioNow Logo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit // Skaliert das Bild, um es vollständig anzuzeigen
                            )
                        }
                    }
                }
            }
        }
    )
}