package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities

data class Game(
    val difficulty: Difficulty = Difficulty.EASY,
    val amountOfRounds: Int = 1,
    val currentRound: Round? = null,
    val correctWords: List<Word> = listOf(),
    val incorrectWords: List<Word> = listOf(),
    val gameStatus: GameStatus = GameStatus.CONFIGURING,
    val configurationStatus: ConfigurationStatus = ConfigurationStatus.CONFIGURING_DIFFICULTY,
)
