package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.mappers

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.models.WordModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word

object WordMapper {
    fun fromModel(model: WordModel): Word {
        return Word(
            identifier = model.identifier,
            value = model.value,
            amountOfLetters = model.amountOfLetters,
            difficulty = buildDifficulty(model.difficulty)
        )
    }

    private fun buildDifficulty(difficultyCode: Int): Difficulty {
        return when (difficultyCode) {
            1 -> Difficulty.EASY
            2 -> Difficulty.MEDIUM
            3 -> Difficulty.HARD
            else -> Difficulty.EASY
        }
    }
}