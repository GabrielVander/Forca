package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.use_cases

import arrow.core.Either
import br.edu.ifsp.aluno.vander.gabriel.hangman.config.Source
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories.WordRepositoryFactory
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.Failure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories.WordRepository

class GetRandomWordByDifficultyUseCase(
    private val wordRepository: WordRepository = WordRepositoryFactory.build(Source.REMOTE)
) {

    suspend fun execute(difficulty: Difficulty): Either<Failure, Word> {
        return wordRepository.getSingleWordByDifficulty(difficulty)
    }

}