package com.example.app.ui.home

    import HomeViewModel
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.ViewModelProvider
    import com.example.app.data.repository.ServiceRepository

    class HomeViewModelFactory(
    private val repository: ServiceRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
    return HomeViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
    }
