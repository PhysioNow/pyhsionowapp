package crystal.physionow.ui.theme.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import crystal.physionow.api.Content
import crystal.physionow.api.GeminiRequest
import crystal.physionow.api.Part
import crystal.physionow.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay


@Composable
fun ChatWithGeminiPage() {
    var userInput by remember { mutableStateOf("") }
    var chatResponse by remember { mutableStateOf("Du musst erst deine Frage, oder was du trainieren willst eingeben!") }
    var isLoading by remember { mutableStateOf(false) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var cooldownTime by remember { mutableStateOf(0) } // Verbleibende Cooldown Zeit
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Timer für den Cooldown
    LaunchedEffect(isButtonEnabled) {
        if (!isButtonEnabled) {
            cooldownTime = 10
            while (cooldownTime > 0) {
                delay(1000)
                cooldownTime--
            }
            isButtonEnabled = true // Den Button wieder aktivieren, wenn der Cooldown vorbei ist
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Frage Gemini", style = MaterialTheme.typography.headlineMedium)

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
                isButtonEnabled = false // Button ausschalten
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = RetrofitClient.api.getChatResponse(
                            request = GeminiRequest(
                                contents = listOf(Content(parts = listOf(Part(text = userInput))))
                            )
                        )
                        chatResponse = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                            ?: "Beim Antworten ist ein Fehler aufgetreten, versuche es später erneut!"
                    } catch (e: Exception) {
                        chatResponse = "Error: ${e.message}"
                        Log.e("Gemini API", "Die Server sind aufgrund von zu vielen Anfragen überlastet!", e)
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = userInput.isNotEmpty() && isButtonEnabled // Button nur aktivieren, wenn der Cooldown vorbei ist, damit die Google API nicht überlastet wird
        ) {
            if (isButtonEnabled) {
                Text("Senden")
            } else {
                Text("Bitte warte für ${cooldownTime} Sekunden") // Hier wird der Cooldown angezeigt
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            val annotatedString = formatTextWithBoldAndLinks(chatResponse)
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                            context.startActivity(intent)
                        }
                },
                modifier = Modifier.padding(16.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = Int.MAX_VALUE
            )
        }
    }
}

fun formatTextWithBoldAndLinks(input: String): AnnotatedString {
    val boldRegex = "\\*\\*(.*?)\\*\\*".toRegex()
    val linkRegex = Regex("""https?://\S+""")
    val annotatedString = AnnotatedString.Builder()

    var lastIndex = 0

    val allMatches = (boldRegex.findAll(input) + linkRegex.findAll(input))
        .sortedBy { it.range.first }

    allMatches.forEach { match ->
        annotatedString.append(input.substring(lastIndex, match.range.first))

        when (match) {
            is MatchResult -> {
                if (match.value.startsWith("**")) {
                    annotatedString.withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append(match.groupValues[1])
                    }
                } else {
                    // URL
                    annotatedString.pushStringAnnotation(
                        tag = "URL",
                        annotation = match.value
                    )
                    annotatedString.withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(match.value)
                    }
                    annotatedString.pop()
                }
            }
        }

        lastIndex = match.range.last + 1
    }

    annotatedString.append(input.substring(lastIndex))

    return annotatedString.toAnnotatedString()
}

@Preview(showBackground = true)
@Composable
fun ChatWithGeminiPagePreview() {
    ChatWithGeminiPage()
}