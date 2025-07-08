package com.example.app.ui.car_rental

import CarRentalViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.databinding.FragmentCarRentalBookingBinding
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.common.BookingStatus
import com.example.app.ui.common.BaseFragment
import com.example.app.ui.common.Utils

class CarRentalBookingFragment : BaseFragment() {
    override val layoutId = R.layout.fragment_car_rental_booking

    private lateinit var binding: FragmentCarRentalBookingBinding
    private lateinit var viewModel: CarRentalViewModel
    private var carId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        carId = arguments?.getInt("carId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarRentalBookingBinding.bind(view)

        viewModel = ViewModelProvider(this).get(CarRentalViewModel::class.java)

        setupListeners()
        setupObservers()

        carId?.let { viewModel.loadCarDetails(it) }
    }

    private fun setupListeners() {
        binding.btnConfirmBooking.setOnClickListener {
            val booking = CarRentalBooking(
                id = 0, // Généré par le backend
                carId = carId ?: 0,
                userId = 1, // À remplacer par l'ID utilisateur réel
                pickupDate = binding.etPickupDate.text.toString(),
                returnDate = binding.etReturnDate.text.toString(),
                pickupLocation = binding.etPickupLocation.text.toString(),
                returnLocation = binding.etReturnLocation.text.toString(),
                totalDays = calculateDays(),
                totalPrice = calculateTotalPrice(),
                driverName = binding.etDriverName.text.toString(),
                driverLicense = binding.etDriverLicense.text.toString(),
                driverPhone = binding.etDriverPhone.text.toString(),
                withDriver = binding.cbWithDriver.isChecked,
                status = BookingStatus.PENDING
            )
            viewModel.bookCar(booking)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.selectedCar.observe(viewLifecycleOwner) { car ->
            car?.let {
                binding.tvCarModel.text = "${car.brand} ${car.model}"
                binding.tvDailyPrice.text = Utils.formatPrice(car.pricePerDay)
            }
        }

        viewModel.bookingResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().popBackStack()
                Toast.makeText(context, "Réservation confirmée!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateDays(): Int {
        // Implémentez la logique de calcul des jours
        return 1
    }

    private fun calculateTotalPrice(): Double {
        // Implémentez le calcul du prix total
        return 0.0
    }
}
