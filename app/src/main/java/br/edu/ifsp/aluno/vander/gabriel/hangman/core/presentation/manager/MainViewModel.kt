package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.*
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.Failure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.failures.UnexpectedFailure
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.use_cases.GetRandomWordByDifficultyUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private val getWordUseCase: GetRandomWordByDifficultyUseCase =
        GetRandomWordByDifficultyUseCase()

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _currentGame: MutableLiveData<Game> = MutableLiveData()
    val currentGame: LiveData<Game> = _currentGame

    private val _configuration: MutableLiveData<Configuration> = MutableLiveData(Configuration())
    val configuration: LiveData<Configuration> = _configuration

    private val _pastRounds: MutableLiveData<List<Round>> = MutableLiveData(listOf())
    val pastRounds: LiveData<List<Round>> = _pastRounds

    private val _loading: MutableLiveData<LoadingMessage?> = MutableLiveData(null)
    val loading: LiveData<LoadingMessage?> = _loading

    fun newGame() {
        _pastRounds.value = listOf()
        _currentGame.value = Game()
    }

    fun setGameDifficulty(difficulty: Difficulty) {
        _configuration.postValue(
            _configuration.value!!.copy(
                difficulty = difficulty,
                configurationStatus = ConfigurationStatus.CONFIGURING_ROUNDS
            )
        )
    }

    fun setNumberOfRounds(numberOfRounds: Int) {
        _configuration.postValue(
            _configuration.value!!.copy(
                amountOfRounds = numberOfRounds,
                configurationStatus = ConfigurationStatus.FINISHED
            )
        )
    }

    fun startNewRound() {
        coroutineScope.launch {
            _loading.postValue(LoadingMessage("Fetching new word..."))

            val word: Either<Failure, Word> =
                getWordUseCase.execute(_configuration.value!!.difficulty)

            word.fold(
                ifLeft = { handleWordFailure(it) },
                ifRight = { startNewRoundWithWord(it) },
            )

            _loading.postValue(null)
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

    fun finishCurrentRound() {
        val pastRounds = _pastRounds.value!!
        val currentRound: Round = _currentGame.value!!.currentRound!!

        val finishedRound: Round = currentRound.copy(
            didPlayerWin = checkIfPlayerWonRound(currentRound)
        )
        _pastRounds.value = listOf(
            *(pastRounds.toTypedArray()),
            finishedRound
        )
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    private fun handleWordFailure(failure: Failure) {
        when (failure) {
            is UnexpectedFailure -> {
                publishUnexpectedErrorMessage(failure)
            }
        }
    }

    private fun publishUnexpectedErrorMessage(failure: UnexpectedFailure) {
        _errorMessage.postValue("Something unexpected happened\n${failure.exception.localizedMessage}")
    }

    private fun startNewRoundWithWord(word: Word) {
        val currentGame = _currentGame.value

        val previousRound: Round? = currentGame!!.currentRound
        val hasPreviousRound: Boolean = previousRound != null

        _currentGame.postValue(
            _currentGame.value!!.copy(
                currentRound = Round(
                    word = word,
                    roundNumber = if (hasPreviousRound) previousRound!!.roundNumber + 1 else 1,
                )
            )
        )
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

    private fun checkIfPlayerWonRound(round: Round): Boolean {
        return round.guessedLetters.containsAll(
            round.word.value.uppercase().toCharArray().toList()
        )
    }
}

data class LoadingMessage(val message: String)
