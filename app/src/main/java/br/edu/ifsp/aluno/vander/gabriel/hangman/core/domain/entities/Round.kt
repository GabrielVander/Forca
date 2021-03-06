package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities

data class Round(
    val word: Word,
    val roundNumber: Int = 1,
    val maxNumberOfGuesses: Int = 6,
    val guessedLetters: List<Char> = listOf(),
    val numberOfGuesses: Int = 0,
    val didPlayerWin: Boolean = false
)
