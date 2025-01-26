package crystal.physionow.ui.theme.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchExercisesPage(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Suche nach Übungen",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Gib hier ein, was du üben willst") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Deine Aktion hier, z.B. Suche nach Übungen */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Suchen (Frage Gemini)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchExercisesPagePreview() {
    SearchExercisesPage()
}
