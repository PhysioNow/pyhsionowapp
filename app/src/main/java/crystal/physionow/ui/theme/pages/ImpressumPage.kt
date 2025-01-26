package crystal.physionow.ui.theme.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import crystal.physionow.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImpressumPage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Impressum") }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Text-Inhalt
                    Text(
                        text = "An Android Application developed by Jannik Wappler \nSternenwinkel 5\n04821 Brandis\nDeutschland\nTel: +49 176 51079224 \n \n \n \n \nGrundidee: \nPhysioNow ist eine App, die Physiotherrappie digitalisieren soll. Es gibt viele Funktionen, wie zum Beispiel, Einen QR-Code Scanner, mit welchem man sein Dokument einscannen kann, und einem direkt passende Übungen angezeigt werden. Außerdem eine Funktion um Gemini nach passenden Übungen zu fragen. \n \n © Copyright by Jannik Wappler \ndarunter Zählt: Idee der App, jegliche Bilder und Struktur. SourceCode unter crystal.physionow ist im geistigen Eigentum von Jannik Wappler",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 16.dp) // Abstand zum Logo
                    )

                    // PhysioNow-Logo hinzufügen
                    Image(
                        painter = painterResource(id = R.drawable.physionowlogo),
                        contentDescription = "PhysioNow Logo",
                        modifier = Modifier
                            .size(240.dp) // Größe des Logos
                            .padding(top = 8.dp) // Abstand nach oben
                    )
                }
            }
        }
    )
}
