package com.example.app.ui.taxi_brousse

import TaxiBrousseViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.data.model.taxi_brousse.TaxiBrousseBooking
import com.example.app.data.model.common.BookingStatus
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentTaxiBrousseBookingBinding
import com.example.app.ui.common.BaseFragment
import com.example.app.ui.common.Utils
import java.text.SimpleDateFormat
import java.util.*

class TaxiBrousseBookingFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_taxi_brousse_booking
    private lateinit var binding: FragmentTaxiBrousseBookingBinding
    private lateinit var viewModel: TaxiBrousseViewModel
    private var routeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            routeId = it.getInt("routeId", -1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaxiBrousseBookingBinding.bind(view)

        setupViewModel()
        loadRouteDetails()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaxiBrousseViewModel(ServiceRepositoryImpl()) as T
            }
        })[TaxiBrousseViewModel::class.java]
    }

    private fun loadRouteDetails() {
        if (routeId != -1) {
            viewModel.loadRoutes()
            observeRoutes()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeRoutes() {
        viewModel.routes.observe(viewLifecycleOwner) { routes ->
            routes.find { it.id == routeId }?.let { route ->
                binding.apply {
                    tvRoute.text = "${route.departure} → ${route.destination}"
                    tvDepartureTime.text = route.departureTime
                    tvArrivalTime.text = route.arrivalTime
                    tvPrice.text = Utils.formatPrice(route.price)
                    tvVehicleType.text = route.vehicleType
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnConfirmBooking.setOnClickListener {
            val booking = TaxiBrousseBooking(
                id = 0, // Généré par le backend
                routeId = routeId,
                userId = 1, // À remplacer par l'ID utilisateur réel
                passengerName = binding.etPassengerName.text.toString(),
                passengerPhone = binding.etPassengerPhone.text.toString(),
                seatNumber = binding.etSeatNumber.text.toString().toIntOrNull() ?: 1,
                bookingDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                travelDate = binding.etTravelDate.text.toString(),
                totalPrice = binding.tvPrice.text.toString()
                    .replace(" Ar", "").toDoubleOrNull() ?: 0.0,
                status = BookingStatus.PENDING
            )

            viewModel.bookRoute(booking)
            observeBookingResult()
        }
    }

    private fun observeBookingResult() {
        viewModel.bookingResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().popBackStack()
                Toast.makeText(context, "Réservation confirmée!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
