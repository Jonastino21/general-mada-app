package com.example.app.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    protected fun handleError(exception: Exception) {
        _error.value = "Erreur: ${exception.message}"
        _isLoading.value = false
    }
}
