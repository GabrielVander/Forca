package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.use_cases

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories.WordRepositoryInMemoryImpl
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories.WordRepository

class GetRandomWordByDifficultyUseCase {
    private val wordRepository: WordRepository = WordRepositoryInMemoryImpl()

    suspend fun execute(difficulty: Difficulty): Word {
        return wordRepository.getSingleWordByDifficulty(difficulty)
    }
}