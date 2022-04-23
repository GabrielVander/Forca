package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages

import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Round
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.MainViewModel

@Composable
fun GameResultPage(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val pastRounds: List<Round> by mainViewModel.pastRounds.observeAsState(listOf())
    val roundsWon: List<Round> = pastRounds.filter { it.didPlayerWin }
    val roundsLost: List<Round> = pastRounds.filter { !it.didPlayerWin }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                RoundsWonDisplay(roundsWon)
                RoundsLostDisplay(roundsLost)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(onClick = {
                navController.navigate("main") {
                    popUpTo("main") {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Finish")
            }
        }
    }
}

@Composable
private fun RoundsLostDisplay(roundsLost: List<Round>) {
    Column {
        Text(text = "You've lost ${roundsLost.size} rounds!", fontSize = 3.em)
        Text(text = "You've missed:")
        for (round in roundsLost) {
            Text(text = "\t- " + round.word.value)
        }
    }
}

@Composable
private fun RoundsWonDisplay(roundsWon: List<Round>) {
    Column {
        Text(text = "You've won ${roundsWon.size} rounds!", fontSize = 3.em)
        Text(text = "You've guessed correctly:")
        for (round in roundsWon) {
            Text(text = "\t- " + round.word.value)
        }
    }
}