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
        mockInMemoryDataSource = mockk()
        mockkObject(WordMapper)

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
        val mockModels: List<WordModel> = listOf(
            mockk {
                every { difficulty } returns 3
            }
        )
        val mockWordEntity: Word = mockk()

        coEvery { mockInMemoryDataSource.getWords() } returns mockModels
        every { WordMapper.fromModel(any()) } returns mockWordEntity

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
    fun `should return a word with the specified difficulty`(expectedDifficulty: String): Unit =
        runBlocking {
            val mockEasyWordModel: WordModel = mockk {
                every { difficulty } returns 1
            }

            val mockMediumWordModel: WordModel = mockk {
                every { difficulty } returns 2
            }

            val mockHardWordModel: WordModel = mockk {
                every { difficulty } returns 3
            }

            val mockModels: List<WordModel> = listOf(
                mockEasyWordModel,
                mockMediumWordModel,
                mockHardWordModel,
            )

            val mockEasyWord: Word = mockk {
                every { difficulty } returns Difficulty.EASY
            }
            val mockMediumWord: Word = mockk {
                every { difficulty } returns Difficulty.MEDIUM
            }
            val mockHardWord: Word = mockk {
                every { difficulty } returns Difficulty.HARD
            }

            coEvery { mockInMemoryDataSource.getWords() } returns mockModels
            every { WordMapper.fromModel(mockEasyWordModel) } returns mockEasyWord
            every { WordMapper.fromModel(mockMediumWordModel) } returns mockMediumWord
            every { WordMapper.fromModel(mockHardWordModel) } returns mockHardWord

            val expectedDifficultyEntity: Difficulty = Difficulty.valueOf(expectedDifficulty)

            val word: Word = repository.getSingleWordByDifficulty(expectedDifficultyEntity)

            Assert.assertEquals(expectedDifficultyEntity, word.difficulty)

        }

    @Test
    fun `should use WordMapper to map models returned by the data source`(): Unit = runBlocking {
        val mockModels: List<WordModel> = listOf(
            mockk {
                every { difficulty } returns 1
            },
            mockk {
                every { difficulty } returns 2
            },
            mockk {
                every { difficulty } returns 3
            }
        )

        val mockWordEntity: Word = mockk()

        coEvery { mockInMemoryDataSource.getWords() } returns mockModels

        every { WordMapper.fromModel(any()) } returns mockWordEntity

        val word: Word = repository.getSingleWordByDifficulty(Difficulty.MEDIUM)

        Assert.assertEquals(mockWordEntity, word)
    }
}