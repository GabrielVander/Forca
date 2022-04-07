package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.ConfigurationStatus
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Difficulty
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.domain.entities.Game
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.MainViewModel
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun NewGamePage(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    val game: Game? by mainViewModel.currentGame.observeAsState(null)

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            when (game?.configurationStatus) {
                ConfigurationStatus.CONFIGURING_DIFFICULTY -> DifficultyConfiguration(mainViewModel)
                ConfigurationStatus.CONFIGURING_ROUNDS -> RoundConfiguration(onSave = {
                    mainViewModel.setNumberOfRounds(it)
                })
                ConfigurationStatus.FINISHED -> navController.popBackStack()
            }
        }
    }
}

@Composable
private fun DifficultyConfiguration(mainViewModel: MainViewModel) {
    Title("Choose your difficulty")
    Spacer(modifier = Modifier.height(25.dp))
    DifficultyButtons(mainViewModel)
}

@Composable
private fun RoundConfiguration(
    onSave: (Int) -> Unit = {}
) {
    var numberOfRounds by remember { mutableStateOf(0) }

    Title(title = "How many rounds?")
    Spacer(modifier = Modifier.height(25.dp))
    RoundNumberPicker(
        currentValue = numberOfRounds,
        onChange = { numberOfRounds = it }
    )
    Button(onClick = { onSave(numberOfRounds) }) {
        Text(text = "Save")
    }
}

@Composable
private fun DifficultyButtons(mainViewModel: MainViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Difficulty.values().forEach {
            Button(onClick = { mainViewModel.setGameDifficulty(it) }) {
                Text(text = it.name)
            }
        }
    }
}

@Composable
private fun RoundNumberPicker(
    currentValue: Int,
    onChange: (value: Int) -> Unit = {}
) {

    NumberPicker(
        value = currentValue,
        range = 1..15,
        onValueChange = onChange
    )
}

@Composable
private fun Title(title: String) {
    Text(
        title,
        fontSize = 32.sp
    )
}
