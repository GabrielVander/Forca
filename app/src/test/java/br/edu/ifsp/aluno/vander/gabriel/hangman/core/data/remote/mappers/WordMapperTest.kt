package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.mappers

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.models.WordModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class WordMapperTest {

    private lateinit var mapper: WordMapper

    @BeforeEach
    fun setUp() {
        mapper = WordMapper()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @CsvSource(
        "248, quality, 413, 1",
        "324, climb, 827, 2",
        "843, confuse, 514, 3",
        "647, let, 466, 1",
        "68, sweet, 860, 2",
        "387, dark, 397, 3",
    )
    fun `should map word correctly`(
        mockIdentifier: Int,
        mockWord: String,
        mockLetters: Int,
        mockLevel: Int
    ) {
        val mockModel: WordModel = mockk {
            every { identifier } returns mockIdentifier
            every { word } returns mockWord
            every { letters } returns mockLetters
            every { level } returns mockLevel
        }

        val expectedDifficulty: Difficulty = getDifficultyByLevel(mockLevel)

        val result: Word = mapper.fromModel(mockModel)

        assertEquals(mockIdentifier, result.identifier)
        assertEquals(mockWord, result.value)
        assertEquals(mockLetters, result.amountOfLetters)
        assertEquals(expectedDifficulty, result.difficulty)
    }

    private fun getDifficultyByLevel(level: Int): Difficulty {
        return when (level) {
            1 -> Difficulty.EASY
            2 -> Difficulty.MEDIUM
            else -> Difficulty.HARD
        }
    }

}