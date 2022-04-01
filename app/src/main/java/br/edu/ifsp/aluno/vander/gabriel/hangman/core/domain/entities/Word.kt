package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities

data class Word(
    val identifier: Int,
    val value: String,
    val amountOfLetters: Int,
    val difficulty: Difficulty
)
