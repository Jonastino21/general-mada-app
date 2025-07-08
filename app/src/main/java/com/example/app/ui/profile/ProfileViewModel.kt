import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.app.data.model.common.User
import com.example.app.data.repository.ServiceRepository
import com.example.app.ui.common.BaseViewModel

class ProfileViewModel(
    private val repository: ServiceRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _bookings = MutableLiveData<Map<String, Any>>()
    val bookings: LiveData<Map<String, Any>> = _bookings

    fun loadProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = repository.getUserProfile()
                _user.value = user
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadBookings(userId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val bookings = repository.getUserBookings(userId)
                _bookings.value = bookings
            } catch (e: Exception) {
                handleError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
