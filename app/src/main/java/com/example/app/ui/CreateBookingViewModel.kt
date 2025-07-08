package com.example.app.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.app.R
import com.example.app.data.model.CreateBookingViewModel
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.common.BookingStatus
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentCreateBookingBinding
import com.example.app.ui.common.BaseFragment

class CreateBookingFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_create_booking
    private lateinit var binding: FragmentCreateBookingBinding
    private lateinit var viewModel: CreateBookingViewModel

    private val args: CreateBookingFragmentArgs by navArgs()  // récupère carId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBookingBinding.bind(view)

        setupViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CreateBookingViewModel(ServiceRepositoryImpl()) as T
            }
        })[CreateBookingViewModel::class.java]
    }

    private fun setupListeners() {
        binding.btnConfirmBooking.setOnClickListener {
            val booking = CarRentalBooking(
                id = 0,
                carId = args.carId,
                userId = 1, // à remplacer
                pickupDate = binding.etPickupDate.text.toString(),
                returnDate = binding.etReturnDate.text.toString(),
                pickupLocation = "Antananarivo", // exemple
                returnLocation = "Antananarivo", // exemple
                totalDays = 1,
                totalPrice = 0.0,
                driverName = binding.etGuestName.text.toString(),
                driverPhone = binding.etGuestPhone.text.toString(),
                driverLicense = "XYZ123", // exemple
                status = BookingStatus.PENDING
            )


            viewModel.bookCar(booking)
            observeBookingResult()
        }
    }

    private fun observeBookingResult() {
        viewModel.bookingResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "Réservation confirmée !", Toast.LENGTH_SHORT).show()
                // Fermer ou revenir
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(context, "Erreur lors de la réservation", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
