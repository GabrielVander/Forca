package br.edu.ifsp.aluno.vander.gabriel.hangman.core.data.repositories

import br.edu.ifsp.aluno.vander.gabriel.hangman.config.Source
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.repositories.WordRepository

object WordRepositoryFactory {

    fun build(source: Source = Source.LOCAL): WordRepository {
        return when (source) {
            Source.LOCAL -> WordRepositoryInMemoryImpl()
            Source.REMOTE -> WordRepositoryRemoteImpl()
        }
    }

}