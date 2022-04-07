package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager.MainViewModel
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages.InitialPage
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages.NewGamePage
import br.edu.ifsp.aluno.vander.gabriel.hangman.ui.theme.HangmanTheme

@Composable
fun HangmanApp(mainViewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()

    HangmanTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NavHost(navController, startDestination = "main") {
                composable("main") {
                    InitialPage(
                        navController = navController,
                        mainViewModel = mainViewModel
                    )
                }
                composable("new_game") {
                    NewGamePage(
                        navController = navController,
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}