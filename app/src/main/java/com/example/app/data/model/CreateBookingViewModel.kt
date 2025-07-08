package com.example.app.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.repository.ServiceRepository
import kotlinx.coroutines.launch

class CreateBookingViewModel(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _bookingResult = MutableLiveData<Boolean>()
    val bookingResult: LiveData<Boolean> = _bookingResult

    fun bookCar(booking: CarRentalBooking) {
        viewModelScope.launch {
            try {
                repository.bookCarRental(booking)
                _bookingResult.postValue(true)
            } catch (e: Exception) {
                _bookingResult.postValue(false)
            }
        }
    }
}
