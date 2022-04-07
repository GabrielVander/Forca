package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation

import android.provider.ContactsContract
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages.InitialPage
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages.NewGame
import br.edu.ifsp.aluno.vander.gabriel.hangman.ui.theme.HangmanTheme

@Composable
fun HangmanApp() {
    val navController = rememberNavController()

    HangmanTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            NavHost(navController, startDestination = "main") {
                composable("main") { InitialPage(navController = navController) }
                composable("new_game") {
                    NewGame(navController = navController)
                }
            }
        }
    }
}