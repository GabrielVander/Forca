package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifsp.aluno.vander.gabriel.hangman.R
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.MainViewModel

@Composable
fun InitialPage(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    Scaffold(modifier = Modifier.padding(10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(text = "Hangman", fontSize = 52.sp)
            Icon(
                modifier = Modifier.size(300.dp),
                painter = painterResource(id = R.drawable.stickman_on_rope),
                contentDescription = null
            )
            Button(onClick = {
                mainViewModel.newGame()
                navController.navigate("new_game")
            }) {
                Text(text = "New Game")
            }
        }
    }
}
