package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures

data class UnexpectedFailure(val exception: Exception): Failure()
