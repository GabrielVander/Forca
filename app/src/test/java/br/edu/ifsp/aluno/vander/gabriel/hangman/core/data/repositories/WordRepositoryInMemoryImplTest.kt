package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.data_sources.InMemoryDataSource
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.mappers.WordMapper
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.models.WordModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import io.mockk.*
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
internal class WordRepositoryInMemoryImplTest {
    private lateinit var mockInMemoryDataSource: InMemoryDataSource
    private lateinit var repository: WordRepositoryInMemoryImpl

    @Before
    fun setUp() {
        mockInMemoryDataSource = mockk(relaxed = true)

        repository = WordRepositoryInMemoryImpl(
            inMemoryDataSource = mockInMemoryDataSource
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should call data source correctly`(): Unit = runBlocking {
        val mockModels: List<WordModel> = listOf()

        coEvery { mockInMemoryDataSource.getWords() } returns mockModels

        repository.getSingleWordByDifficulty(Difficulty.HARD)

        coVerify { mockInMemoryDataSource.getWords() }
    }

    @Test
    @Parameters(
        value = [
            "EASY",
            "MEDIUM",
            "HARD",
        ]
    )
    fun `should return a word with the specified difficulty`(difficulty: String): Unit =
        runBlocking {
            val mockModels: List<WordModel> = listOf()

            coEvery { mockInMemoryDataSource.getWords() } returns mockModels

            val expectedDifficulty: Difficulty = Difficulty.valueOf(difficulty)

            val word: Word = repository.getSingleWordByDifficulty(expectedDifficulty)

            Assert.assertEquals(word.difficulty, expectedDifficulty)

        }

    @Test
    fun `should use WordMapper to map models returned by the data source`(): Unit = runBlocking {
        val mockWordModel1: WordModel = mockk(relaxed = true)
        val mockWordModel2: WordModel = mockk(relaxed = true)
        val mockWordModel3: WordModel = mockk(relaxed = true)

        val mockModels: List<WordModel> = listOf(
            mockWordModel1,
            mockWordModel2,
            mockWordModel3,
        )

        val mockWord: Word = mockk(relaxed = true)

        coEvery { mockInMemoryDataSource.getWords() } returns mockModels

        mockkObject(WordMapper)

        every { WordMapper.fromModel(mockWordModel1) } returns mockWord
        every { WordMapper.fromModel(mockWordModel2) } returns mockWord
        every { WordMapper.fromModel(mockWordModel3) } returns mockWord

        val word: Word = repository.getSingleWordByDifficulty(Difficulty.MEDIUM)

        Assert.assertEquals(mockWord, word)
    }
}