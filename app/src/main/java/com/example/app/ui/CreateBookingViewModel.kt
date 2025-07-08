package com.example.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.app.R
import com.example.app.data.model.CreateBookingViewModel
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.common.BookingStatus
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentCreateBookingBinding

class CreateBookingFragment : Fragment() {

    private var _binding: FragmentCreateBookingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CreateBookingViewModel
    private val args: CreateBookingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_booking, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CreateBookingViewModel(ServiceRepositoryImpl()) as T
            }
        })[CreateBookingViewModel::class.java]
    }

    private fun setupListeners() {
        binding.btnConfirmBooking.setOnClickListener {
            // Validation des champs
            if (validateInputs()) {
                val booking = CarRentalBooking(
                    id = 0,
                    carId = args.carId,
                    userId = 1,
                    pickupDate = binding.etPickupDate.text.toString(),
                    returnDate = binding.etReturnDate.text.toString(),
                    pickupLocation = "Antananarivo",
                    returnLocation = "Antananarivo",
                    totalDays = 1,
                    totalPrice = 0.0,
                    driverName = binding.etGuestName.text.toString(),
                    driverPhone = binding.etGuestPhone.text.toString(),
                    driverLicense = "XYZ123",
                    status = BookingStatus.PENDING
                )

                viewModel.bookCar(booking)
                observeBookingResult()
            }
        }
    }

    private fun validateInputs(): Boolean {
        val pickupDate = binding.etPickupDate.text.toString()
        val returnDate = binding.etReturnDate.text.toString()
        val guestName = binding.etGuestName.text.toString()
        val guestPhone = binding.etGuestPhone.text.toString()

        if (pickupDate.isEmpty()) {
            binding.etPickupDate.error = "Date de prise en charge requise"
            return false
        }

        if (returnDate.isEmpty()) {
            binding.etReturnDate.error = "Date de retour requise"
            return false
        }

        if (guestName.isEmpty()) {
            binding.etGuestName.error = "Nom du conducteur requis"
            return false
        }

        if (guestPhone.isEmpty()) {
            binding.etGuestPhone.error = "Téléphone du conducteur requis"
            return false
        }

        return true
    }

    private fun observeBookingResult() {
        viewModel.bookingResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, "Réservation confirmée !", Toast.LENGTH_SHORT).show()
                // Utiliser NavController pour la navigation
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(context, "Erreur lors de la réservation", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}