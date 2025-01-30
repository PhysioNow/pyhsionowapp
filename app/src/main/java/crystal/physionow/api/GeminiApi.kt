package crystal.physionow.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

private const val GEMINI_API_KEY = "AIzaSyAJlc_SsxLpd4UTl3FPNSSMMVyIuMbopBM"

interface GeminiApi {
    @POST("v1/models/gemini-pro:generateContent")
    suspend fun getChatResponse(
        @Query("key") apiKey: String = GEMINI_API_KEY,
        @Body request: GeminiRequest
    ): GeminiResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    val api: GeminiApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApi::class.java)

    }
}

data class GeminiRequest(val contents: List<Content>)
data class Content(val parts: List<Part>)
data class Part(val text: String)
data class GeminiResponse(val candidates: List<Candidate>)
data class Candidate(val content: String)