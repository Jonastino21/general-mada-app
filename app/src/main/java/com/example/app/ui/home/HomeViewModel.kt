package com.example.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.app.data.model.common.User
import com.example.app.data.model.common.ServiceCard
import com.example.app.data.repository.ServiceRepository
import com.example.app.ui.common.BaseViewModel

class HomeViewModel(
    private val repository: ServiceRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _services = MutableLiveData<List<ServiceCard>>()
    val services: LiveData<List<ServiceCard>> = _services

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = repository.getUserProfile()
                val services = repository.getServices()

                _user.value = user
                _services.value = services
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
