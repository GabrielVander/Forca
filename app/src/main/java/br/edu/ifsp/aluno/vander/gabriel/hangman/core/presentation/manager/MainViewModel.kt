package br.edu.ifsp.aluno.vander.gabriel.hangman.core.presentation.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _isGameRunning: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGameRunning: LiveData<Boolean> = _isGameRunning

    fun newGame() {
        _isGameRunning.value = true
    }
}