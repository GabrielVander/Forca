package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories

import arrow.core.Either
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.data_sources.WordDataSource
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.mappers.WordMapper
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.models.WordModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.Failure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.NoIdentifiersForDifficultyFailure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.NoWordFoundForIdentifier
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class WordRepositoryRemoteImplTest {
    private lateinit var wordDataSource: WordDataSource
    private lateinit var wordMapper: WordMapper

    private lateinit var repository: WordRepositoryRemoteImpl

    @BeforeEach
    fun setUp() {
        wordDataSource = mockk()
        wordMapper = mockk()
        repository = WordRepositoryRemoteImpl(wordDataSource, wordMapper)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @CsvSource(
        "EASY, 438",
        "MEDIUM, 482",
        "HARD, 271"
    )
    fun `should call data source correctly`(difficultyLabel: String, wordIdentifier: Int): Unit =
        runBlocking {
            val difficulty: Difficulty = Difficulty.valueOf(difficultyLabel)
            val mockIdentifiers: List<Int> = listOf(
                wordIdentifier,
            )

            val mockWordModel: WordModel = mockk()
            val mockWordEntity: Word = mockk()

            coEvery { wordDataSource.getIdentifiersByDifficulty(any()) } returns mockIdentifiers
            coEvery { wordDataSource.getWordByIdentifier(any()) } returns mockWordModel
            every { wordMapper.fromModel(any()) } returns mockWordEntity

            repository.getSingleWordByDifficulty(difficulty)

            coVerify { wordDataSource.getIdentifiersByDifficulty(difficulty.ordinal + 1) }
            coVerify { wordDataSource.getWordByIdentifier(wordIdentifier) }
        }

    @Test
    fun `should call mapper with result from data source`(): Unit = runBlocking {
        val mockIdentifiers: List<Int> = listOf(
            705,
        )

        val mockWordModel: WordModel = mockk()
        val mockWordEntity: Word = mockk()

        coEvery { wordDataSource.getIdentifiersByDifficulty(any()) } returns mockIdentifiers
        coEvery { wordDataSource.getWordByIdentifier(any()) } returns mockWordModel
        every { wordMapper.fromModel(any()) } returns mockWordEntity

        repository.getSingleWordByDifficulty(Difficulty.HARD)

        verify { wordMapper.fromModel(mockWordModel) }
    }

    @Test
    fun `should return data received from mapper`(): Unit = runBlocking {
        val mockIdentifiers: List<Int> = listOf(
            556,
        )

        val mockWordModel: WordModel = mockk()
        val mockWordEntity: Word = mockk()

        coEvery { wordDataSource.getIdentifiersByDifficulty(any()) } returns mockIdentifiers
        coEvery { wordDataSource.getWordByIdentifier(any()) } returns mockWordModel
        every { wordMapper.fromModel(any()) } returns mockWordEntity

        val result: Either<Failure, Word> =
            repository.getSingleWordByDifficulty(difficulty = Difficulty.EASY)

        Assertions.assertTrue(result.isRight())
        result.fold(
            ifLeft = { fail("Unexpected state") },
            ifRight = { Assertions.assertEquals(mockWordEntity, it) }
        )
    }

    @Test
    fun `should return NoIdentifiers failure if data source returns no identifiers`(): Unit =
        runBlocking {
            coEvery { wordDataSource.getIdentifiersByDifficulty(any()) } returns null

            val result: Either<Failure, Word> =
                repository.getSingleWordByDifficulty(Difficulty.EASY)

            Assertions.assertTrue(result.isLeft())

            result.fold(
                ifLeft = {
                    Assertions.assertInstanceOf(NoIdentifiersForDifficultyFailure::class.java, it)
                },
                ifRight = { fail("Unexpected state") }
            )
        }

    @Test
    fun `should return WordNotFound failure if data source returns no word`(): Unit = runBlocking {
        val mockIdentifiers: List<Int> = listOf(
            556,
        )

        coEvery { wordDataSource.getIdentifiersByDifficulty(any()) } returns mockIdentifiers
        coEvery { wordDataSource.getWordByIdentifier(any()) } returns null

        val result: Either<Failure, Word> =
            repository.getSingleWordByDifficulty(difficulty = Difficulty.MEDIUM)

        Assertions.assertTrue(result.isLeft())
        result.fold(
            ifLeft = { Assertions.assertInstanceOf(NoWordFoundForIdentifier::class.java, it) },
            ifRight = { fail("Unexpected state") }
        )
    }

}