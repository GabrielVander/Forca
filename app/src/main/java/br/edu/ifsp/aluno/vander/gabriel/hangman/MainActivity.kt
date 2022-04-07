package br.edu.ifsp.aluno.vander.gabriel.hangman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.pages.InitialPage
import br.edu.ifsp.aluno.vander.gabriel.hangman.ui.theme.HangmanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HangmanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    InitialPage()
                }
            }
        }
    }
}