package crystal.physionow.ui.theme.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import crystal.physionow.api.Content
import crystal.physionow.api.GeminiRequest
import crystal.physionow.api.Part
import crystal.physionow.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ChatWithGeminiPage() {
    var userInput by remember { mutableStateOf("") }
    var chatResponse by remember { mutableStateOf("Noch keine Antwort") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Frage Google Gemini", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Gib deine Frage ein") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitClient.api.getChatResponse(
                            request = GeminiRequest(
                                contents = listOf(Content(parts = listOf(Part(text = userInput))))
                            )
                        )
                        chatResponse = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "Keine Antwort erhalten"

                    } catch (e: Exception) {
                        chatResponse = "Fehler: ${e.message}"
                        Log.e("Gemini API", "Fehler bei der Anfrage", e)
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = userInput.isNotEmpty()
        ) {
            Text("Senden")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text("Antwort: $chatResponse", modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatWithGeminiPagePreview() {
    ChatWithGeminiPage()
}
