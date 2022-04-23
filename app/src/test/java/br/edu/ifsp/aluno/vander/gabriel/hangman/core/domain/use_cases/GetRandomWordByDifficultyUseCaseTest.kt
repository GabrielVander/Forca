package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.use_cases

import arrow.core.Either
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.Failure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories.WordRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class GetRandomWordByDifficultyUseCaseTest {

    private lateinit var wordRepository: WordRepository

    private lateinit var useCase: GetRandomWordByDifficultyUseCase

    @BeforeEach
    fun setUp() {
        wordRepository = mockk()
        useCase = GetRandomWordByDifficultyUseCase(wordRepository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @EnumSource(value = Difficulty::class)
    fun `should call repository correctly`(difficulty: Difficulty): Unit = runBlocking {
        val word: Either<Failure, Word> = mockk()

        coEvery { wordRepository.getSingleWordByDifficulty(any()) } returns word

        useCase.execute(difficulty = difficulty)

        coVerify { wordRepository.getSingleWordByDifficulty(difficulty) }
    }

    @ParameterizedTest
    @EnumSource(value = Difficulty::class)
    fun `should return data received from repository`(difficulty: Difficulty): Unit = runBlocking {
        val mockWord: Either<Failure, Word> = mockk()

        coEvery { wordRepository.getSingleWordByDifficulty(any()) } returns mockWord

        val result: Either<Failure, Word> = useCase.execute(difficulty = difficulty)

        assertEquals(mockWord, result)
    }

}