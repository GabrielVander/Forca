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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Game
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
            if (game?.currentRound == null) {
                Start(
                    onStart = { mainViewModel.startNewRound() }
                )
            } else {
                Text(text = game!!.currentRound!!.word.value)
            }
        }
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