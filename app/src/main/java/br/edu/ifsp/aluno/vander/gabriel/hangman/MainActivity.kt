package br.edu.ifsp.aluno.vander.gabriel.hangman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.HangmanApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HangmanApp()
        }
    }
}