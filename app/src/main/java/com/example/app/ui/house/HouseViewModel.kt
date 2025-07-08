import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.app.data.model.house.House
import com.example.app.data.repository.ServiceRepository
import com.example.app.ui.common.BaseViewModel

class HouseViewModel(
    private val repository: ServiceRepository
) : BaseViewModel() {

    private val _houses = MutableLiveData<List<House>>()
    val houses: LiveData<List<House>> = _houses

    // Autoriser la nullabilité ici
    private val _selectedHouse = MutableLiveData<House?>()
    val selectedHouse: LiveData<House?> = _selectedHouse

    private val _bookingResult = MutableLiveData<Boolean>()
    val bookingResult: LiveData<Boolean> = _bookingResult

    fun loadHouses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val houses = repository.getHouses()
                _houses.value = houses
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchHouses(city: String, guests: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val houses = repository.searchHouses(city, guests)
                _houses.value = houses
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadHouseDetails(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val house = repository.getHouseById(id)
                _selectedHouse.value = house
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun bookHouse(booking: HouseBooking) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = repository.bookHouse(booking)
                _bookingResult.value = success
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
