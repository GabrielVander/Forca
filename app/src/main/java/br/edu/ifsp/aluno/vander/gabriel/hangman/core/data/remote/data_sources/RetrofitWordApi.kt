package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.data_sources

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.models.WordModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@Suppress("SpellCheckingInspection")
interface RetrofitWordApi {

    companion object {
        const val BASE_URL: String = "https://nobile.pro.br/forcaws/"
        const val IDENTIFIERS_ENDPOINT: String = "identificadores/{difficultyId}"
        const val WORD_ENDPOINT: String = "palavra/{wordIdentifier}"
    }

    @GET(IDENTIFIERS_ENDPOINT)
    suspend fun getIdentifiersByDifficulty(@Path("difficultyId") difficultyId: Int): Response<List<Int>>

    @GET(WORD_ENDPOINT)
    suspend fun getWordByIdentifier(@Path("wordIdentifier") wordIdentifier: Int): Response<List<WordModel>>

}