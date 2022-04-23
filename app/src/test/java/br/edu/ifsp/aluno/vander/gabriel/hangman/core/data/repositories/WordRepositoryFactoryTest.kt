package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories

import br.edu.ifsp.aluno.vander.gabriel.hangman.config.Source
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories.WordRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class WordRepositoryFactoryTest {

    @BeforeEach
    fun setUp() {
        mockkConstructor(WordRepositoryInMemoryImpl::class)
        mockkConstructor(WordRepositoryRemoteImpl::class)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @ParameterizedTest
    @EnumSource(value = Source::class)
    fun `should return correct instance based on given source`(source: Source) {
        val result: WordRepository = WordRepositoryFactory.build(source)

        when (source) {
            Source.LOCAL -> {
                assertInstanceOf(WordRepositoryInMemoryImpl::class.java, result)
            }
            Source.REMOTE -> {
                assertInstanceOf(WordRepositoryRemoteImpl::class.java, result)
            }
        }
    }

}