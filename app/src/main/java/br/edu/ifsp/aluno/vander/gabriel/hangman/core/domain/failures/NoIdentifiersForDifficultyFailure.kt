package br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures

import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty

class NoIdentifiersForDifficultyFailure(val difficulty: Difficulty) : Failure()