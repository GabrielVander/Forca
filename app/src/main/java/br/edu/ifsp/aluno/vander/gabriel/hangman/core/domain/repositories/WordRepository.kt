package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories

import arrow.core.Either
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.Failure

interface WordRepository {
    suspend fun getSingleWordByDifficulty(difficulty: Difficulty): Either<Failure, Word>
}