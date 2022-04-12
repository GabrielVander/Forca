package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.data_sources

import android.util.Log
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.models.WordModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class WordDataSource {

    companion object {
        private const val TAG: String = "WordDataSource"
    }

    private var retrofit = Retrofit.Builder()
        .baseUrl(RetrofitWordApi.BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    private var wordApi: RetrofitWordApi =
        retrofit.create(RetrofitWordApi::class.java)

    suspend fun getIdentifiersByDifficulty(difficultyId: Int): List<Int>? {
        val response: Response<List<Int>> =
            wordApi.getIdentifiersByDifficulty(difficultyId = difficultyId)

        if (!response.isSuccessful) {
            Log.i(
                TAG,
                "getIdentifiersByDifficulty: returned an unsuccessful response\n" +
                        "${response.errorBody()}"
            )
            return null
        }

        return response.body()
    }

    suspend fun getWordByIdentifier(wordIdentifier: Int): WordModel? {
        val response: Response<WordModel> =
            wordApi.getWordByIdentifier(wordIdentifier = wordIdentifier)

        if (!response.isSuccessful) {
            Log.i(
                TAG, "getWordByIdentifier: Returned an unsuccessful response\n" +
                        "${response.errorBody()}"
            )

            return null
        }

        return response.body()
    }

}