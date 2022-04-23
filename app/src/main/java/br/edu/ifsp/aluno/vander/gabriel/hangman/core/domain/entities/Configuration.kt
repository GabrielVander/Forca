package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities

data class Configuration(
    val difficulty: Difficulty = Difficulty.EASY,
    val amountOfRounds: Int = 1,
    val configurationStatus: ConfigurationStatus = ConfigurationStatus.CONFIGURING_DIFFICULTY,
)
