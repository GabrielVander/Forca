package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities

data class Game(
    val currentRound: Round? = null,
    val correctWords: List<Word> = listOf(),
    val incorrectWords: List<Word> = listOf(),
    val gameStatus: GameStatus = GameStatus.CONFIGURING,
)
