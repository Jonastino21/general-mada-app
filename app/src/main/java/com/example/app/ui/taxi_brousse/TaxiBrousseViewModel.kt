import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.app.data.model.taxi_brousse.TaxiBrousseRoute
import com.example.app.data.model.taxi_brousse.TaxiBrousseBooking
import com.example.app.data.repository.ServiceRepository
import com.example.app.ui.common.BaseViewModel

class TaxiBrousseViewModel(
    private val repository: ServiceRepository
) : BaseViewModel() {

    private val _routes = MutableLiveData<List<TaxiBrousseRoute>>()
    val routes: LiveData<List<TaxiBrousseRoute>> = _routes

    private val _bookingResult = MutableLiveData<Boolean>()
    val bookingResult: LiveData<Boolean> = _bookingResult

    fun loadRoutes() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val routes = repository.getTaxiBrousseRoutes()
                _routes.value = routes
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchRoutes(departure: String, destination: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val routes = repository.searchTaxiBrousseRoutes(departure, destination)
                _routes.value = routes
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun bookRoute(booking: TaxiBrousseBooking) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = repository.bookTaxiBrousse(booking)
                _bookingResult.value = success
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
