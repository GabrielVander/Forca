package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.mappers

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.models.WordModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word

class WordMapper {

    fun fromModel(model: WordModel): Word {
        return Word(
            identifier = model.identifier,
            value = model.word,
            amountOfLetters = model.letters,
            difficulty = getDifficultyByLevel(model.level)
        )
    }

    private fun getDifficultyByLevel(level: Int): Difficulty {
        return when (level) {
            1 -> Difficulty.EASY
            2 -> Difficulty.MEDIUM
            else -> Difficulty.HARD
        }
    }

}