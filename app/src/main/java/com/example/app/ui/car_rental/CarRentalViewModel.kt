package com.example.app.ui.car_rental
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.app.data.model.car_rental.CarRental
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.common.VehicleType
import com.example.app.data.repository.ServiceRepository
import com.example.app.ui.common.BaseViewModel

class CarRentalViewModel(
    private val repository: ServiceRepository
) : BaseViewModel() {

    private val _cars = MutableLiveData<List<CarRental>>()
    val cars: LiveData<List<CarRental>> = _cars

    private val _selectedCar = MutableLiveData<CarRental?>()
    val selectedCar: LiveData<CarRental?> = _selectedCar

    private val _bookingResult = MutableLiveData<Boolean>()
    val bookingResult: LiveData<Boolean> = _bookingResult

    fun loadCars() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val cars = repository.getCarRentals()
                _cars.value = cars
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCars(location: String, type: VehicleType?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val cars = repository.searchCarRentals(location, type)
                _cars.value = cars
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadCarDetails(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val car = repository.getCarRentalById(id)
                _selectedCar.value = car
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun bookCar(booking: CarRentalBooking) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = repository.bookCarRental(booking)
                _bookingResult.value = success
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
