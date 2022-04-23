package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures

data class NoWordFoundForIdentifier(val identifier: Int) : Failure()