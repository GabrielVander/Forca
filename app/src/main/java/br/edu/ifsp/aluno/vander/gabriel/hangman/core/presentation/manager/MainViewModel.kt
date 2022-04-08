package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.*
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.use_cases.GetRandomWordByDifficultyUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val getWordUseCase: GetRandomWordByDifficultyUseCase =
        GetRandomWordByDifficultyUseCase()

    private val _currentGame: MutableLiveData<Game> = MutableLiveData()
    val currentGame: LiveData<Game> = _currentGame

    fun newGame() {
        _currentGame.value = Game()
    }

    fun setGameDifficulty(difficulty: Difficulty) {
        _currentGame.value = _currentGame.value?.copy(
            difficulty = difficulty,
            configurationStatus = ConfigurationStatus.CONFIGURING_ROUNDS
        )
    }

    fun setNumberOfRounds(numberOfRounds: Int) {
        _currentGame.value = _currentGame.value?.copy(
            amountOfRounds = numberOfRounds,
            configurationStatus = ConfigurationStatus.FINISHED
        )
    }

    fun startNewRound() {
        val currentGame = _currentGame.value

        if (currentGame != null) {
            val previousRound: Round? = currentGame.currentRound
            val hasPreviousRound: Boolean = previousRound != null

            GlobalScope.launch {
                val word: Word = getWordUseCase.execute(currentGame.difficulty)

                _currentGame.postValue(
                    currentGame.copy(
                        currentRound = Round(
                            word = word,
                            roundNumber = if (hasPreviousRound) previousRound!!.roundNumber else 1,
                        )
                    )
                )
            }
        }
    }

    fun addGuess(letter: Char) {
        val game: Game? = _currentGame.value
        if (game?.currentRound != null) {
            val guessedLetters = listOf(
                *(game.currentRound.guessedLetters.toTypedArray()),
                letter
            )
            _currentGame.value = game.copy(
                currentRound = game.currentRound.copy(
                    numberOfGuesses = getNewNumberOfGuesses(game.currentRound, letter),
                    guessedLetters = guessedLetters
                )
            )
        }
    }

    private fun getNewNumberOfGuesses(
        currentRound: Round,
        letter: Char
    ): Int {
        if (currentRound.word.value.uppercase().contains(letter)) {
            return currentRound.numberOfGuesses
        }
        return currentRound.numberOfGuesses + 1
    }
}