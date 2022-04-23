package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Game
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Round
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Word
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.LoadingMessage
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.MainViewModel

@Composable
fun GamePage(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val game: Game? by mainViewModel.currentGame.observeAsState(null)
    val gameIsRunning: Boolean = game == null || game!!.currentRound == null
    val loadingMessage: LoadingMessage? by mainViewModel.loading.observeAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (loadingMessage != null) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = loadingMessage!!.message)
                }
            } else {
                Content(
                    gameIsRunning = gameIsRunning,
                    currentRound = game?.currentRound,
                    onGameStart = { mainViewModel.startNewRound() },
                    onLetterChosen = { mainViewModel.addGuess(it) },
                    onEndRound = {
                        mainViewModel.finishCurrentRound()
                        if (game!!.currentRound!!.roundNumber < game!!.amountOfRounds) {
                            mainViewModel.startNewRound()
                        } else {
                            navController.navigate("game_result") {
                                popUpTo("main")
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun Content(
    gameIsRunning: Boolean,
    currentRound: Round?,
    onGameStart: () -> Unit = {},
    onLetterChosen: (Char) -> Unit = {},
    onEndRound: () -> Unit = {},
) {
    if (gameIsRunning) {
        Start(
            onStart = onGameStart
        )
    } else {
        RoundDisplay(
            currentRound = currentRound!!,
            onLetterChosen = onLetterChosen,
            onEndRound = onEndRound,
        )
    }
}

@Composable
private fun Start(
    onStart: () -> Unit = {}
) {
    Text(text = "Let's go!", fontSize = 42.sp)
    Spacer(modifier = Modifier.height(15.dp))
    Button(onClick = onStart) {
        Text(text = "Start")
    }
}

@Composable
private fun RoundDisplay(
    currentRound: Round,
    onLetterChosen: (Char) -> Unit = {},
    onEndRound: () -> Unit = {}
) {
    val didPlayerRanOutOfGuesses: Boolean =
        currentRound.numberOfGuesses == currentRound.maxNumberOfGuesses
    val didPlayerGuessCorrectly: Boolean = checkIfPlayerWon(currentRound)
    val isRoundOver: Boolean = didPlayerRanOutOfGuesses || didPlayerGuessCorrectly

    WordDisplay(currentRound)
    Spacer(modifier = Modifier.height(25.dp))
    if (isRoundOver) {
        Text(text = if (didPlayerGuessCorrectly) "You've won!" else "You've lost!")
        Button(onClick = onEndRound) {
            Text(text = "End Round")
        }
    } else {
        GuessesDisplay(currentRound.guessedLetters)
        Spacer(modifier = Modifier.height(25.dp))
        Keyboard(
            alreadyGuessedLetters = currentRound.guessedLetters,
            onLetterChosen = onLetterChosen
        )
    }
}

fun checkIfPlayerWon(currentRound: Round): Boolean {
    return currentRound.guessedLetters.containsAll(
        currentRound.word.value.uppercase().toCharArray().toList()
    )
}

@Composable
private fun WordDisplay(round: Round) {
    val word: Word = round.word
    val chosenLetters: List<Char> = round.guessedLetters

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 5.em,
            text = buildObfuscatedWord(
                word = word.value,
                chosenLetters = chosenLetters
            )
        )
    }
}

@Composable
private fun GuessesDisplay(guessedLetters: List<Char>) {
    Text(text = "You've already picked:")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (guessedLetter in guessedLetters) {
            Text(text = guessedLetter.toString())
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Keyboard(
    alreadyGuessedLetters: List<Char>,
    onLetterChosen: (Char) -> Unit = {}
) {
    val alphabet: MutableList<Char> = CharRange('A', 'Z').toMutableList()
    alphabet.removeIf { alreadyGuessedLetters.contains(it) }

    LazyVerticalGrid(
        cells = GridCells.Fixed(6),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(alphabet) {
            Button(
                modifier = Modifier,
                onClick = { onLetterChosen(it) }
            ) {
                Text(text = it.toString())
            }
        }
    }
}

fun buildObfuscatedWord(word: String, chosenLetters: List<Char>): String {
    return buildString {
        for (character in word.uppercase()) {
            if (word.uppercase().indexOf(character) > 0) {
                append(' ')
            }
            append(if (!chosenLetters.contains(character)) '_' else character)
        }
    }
}