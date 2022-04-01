package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word

interface WordRepository {
    suspend fun getSingleWordByDifficulty(difficulty: Difficulty): Word
}