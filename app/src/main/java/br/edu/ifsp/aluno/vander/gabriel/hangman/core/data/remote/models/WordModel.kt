@file:Suppress("SpellCheckingInspection")

package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.remote.models

import com.fasterxml.jackson.annotation.JsonProperty

data class WordModel(
    @JsonProperty("Id")
    val identifier: Int,
    @JsonProperty("Palavra")
    val word: String,
    @JsonProperty("Letras")
    val letters: Int,
    @JsonProperty("Nivel")
    val level: Int,
)
