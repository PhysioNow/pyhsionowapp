package crystal.physionow.ui.theme.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "An Android Application developed by Jannik Wappler \nSternenwinkel 5\n04821 Brandis\nDeutschland\nTel: +49 176 51079224\nGeschäftsführer: Jannik Wappler",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    )
}
