package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.data_sources.InMemoryDataSource
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.mappers.WordMapper
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.models.WordModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories.WordRepository

class WordRepositoryInMemoryImpl(
    private val inMemoryDataSource: InMemoryDataSource = InMemoryDataSource()
) :
    WordRepository {

    override suspend fun getSingleWordByDifficulty(difficulty: Difficulty): Word {
        val word: WordModel = getRandomWordWithDifficulty(difficulty)
        return WordMapper.fromModel(word)
    }

    private suspend fun getRandomWordWithDifficulty(difficulty: Difficulty): WordModel {
        return inMemoryDataSource.getWords().shuffled()
            .first { it.difficulty == difficulty.ordinal + 1 }
    }
}