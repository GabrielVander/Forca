package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
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
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.MainViewModel

@Composable
fun GamePage(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val game: Game? by mainViewModel.currentGame.observeAsState(null)

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (game == null || game!!.currentRound == null) {
                Start(
                    onStart = { mainViewModel.startNewRound() }
                )
            } else {
                WordDisplay(game!!.currentRound!!)
                Spacer(modifier = Modifier.height(25.dp))
                GuessesDisplay(game!!.currentRound!!.guessedLetters)
                Spacer(modifier = Modifier.height(25.dp))
                Keyboard(
                    alreadyGuessedLetters = game!!.currentRound!!.guessedLetters,
                    onLetterChosen = { mainViewModel.addGuess(it) }
                )
            }
        }
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

@Composable
private fun WordDisplay(round: Round) {
    val word: Word = round.word
    val chosenLetters: List<Char> = round.guessedLetters

    Row(modifier = Modifier) {
        Text(
            modifier = Modifier,
            fontSize = 5.em,
            text = buildObfuscatedWord(
                word = word.value,
                chosenLetters = chosenLetters
            )
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

fun buildObfuscatedWord(word: String, chosenLetters: List<Char>): String {
    return buildString {
        for (character in word) {
            if (word.indexOf(character) > 0) {
                append(' ')
            }
            append(if (chosenLetters.contains(character)) character else '_')
        }
    }
}