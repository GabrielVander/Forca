package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.local.models

data class WordModel(
    val identifier: Int,
    val difficulty: Int,
    val value: String,
    val amountOfLetters: Int
)
