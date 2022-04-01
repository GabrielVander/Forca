package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.data_sources

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.models.WordModel

class InMemoryDataSource {

    private val words: MutableList<String> = mutableListOf(
        "comparison",
        "power",
        "camera",
        "farmer",
        "combination",
        "classroom",
        "reception",
        "setting",
        "friendship",
        "data",
        "worker",
        "warning",
        "drawing",
        "hospital",
        "topic",
        "girl",
        "photo",
        "management",
        "candidate",
        "variety",
        "bath",
        "feedback",
        "measurement",
        "quality",
        "passenger",
        "operation",
        "improvement",
        "organization",
        "understanding",
        "nature",
        "river",
        "success",
        "weakness",
        "championship",
        "speech",
        "entry",
        "idea",
        "two",
        "meaning",
        "perception",
        "player",
        "virus",
        "preparation",
        "emphasis",
        "estate",
        "leader",
        "grocery",
        "foundation",
        "population",
        "marriage"
    )

    suspend fun getWords(): List<WordModel> {
        return words.map { toModel(it) }
    }

    private fun toModel(word: String): WordModel {
        return WordModel(
            identifier = 1,
            difficulty = getDifficultyLevel(word),
            value = word,
            amountOfLetters = word.length
        )
    }

    private fun getDifficultyLevel(word: String): Int {
        if (word.length < 6) {
            return 1
        }
        if (word.length < 10) {
            return 2
        }
        return 3
    }

}