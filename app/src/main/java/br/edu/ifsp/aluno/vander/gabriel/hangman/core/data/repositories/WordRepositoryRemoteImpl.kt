package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories

import arrow.core.Either
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.data_sources.WordDataSource
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.mappers.WordMapper
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.Failure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.NoIdentifiersForDifficultyFailure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.NoWordFoundForIdentifier
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.UnexpectedFailure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories.WordRepository

class WordRepositoryRemoteImpl(
    private val wordDataSource: WordDataSource = WordDataSource(),
    private val wordMapper: WordMapper = WordMapper(),
) : WordRepository {
    override suspend fun getSingleWordByDifficulty(difficulty: Difficulty): Either<Failure, Word> {
        try {
            val identifiers: List<Int> =
                wordDataSource.getIdentifiersByDifficulty(difficulty.ordinal + 1)
                    ?: return Either.Left(NoIdentifiersForDifficultyFailure(difficulty = difficulty))

            val wordIdentifier: Int = identifiers.random()

            val wordModel = wordMapper.fromModel(
                wordDataSource.getWordByIdentifier(wordIdentifier) ?: return Either.Left(
                    NoWordFoundForIdentifier(identifier = wordIdentifier)
                )
            )

            return Either.Right(wordModel)
        } catch (e: Exception) {
            return Either.Left(UnexpectedFailure(exception = e))
        }
    }
}