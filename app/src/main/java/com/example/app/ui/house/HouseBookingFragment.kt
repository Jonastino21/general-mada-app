package com.example.app.ui.house

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.data.model.common.BookingStatus
import com.example.app.data.model.house.HouseBooking
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentHouseBookingBinding
import com.example.app.ui.common.BaseFragment
import com.example.app.ui.common.Utils
import java.util.Calendar

class HouseBookingFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_house_booking
    private lateinit var binding: FragmentHouseBookingBinding
    private lateinit var viewModel: HouseViewModel
    private var houseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            houseId = it.getInt("houseId", -1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHouseBookingBinding.bind(view)

        setupViewModel()
        loadHouseDetails()
        setupDatePickers()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HouseViewModel(ServiceRepositoryImpl()) as T
            }
        })[HouseViewModel::class.java]
    }

    private fun loadHouseDetails() {
        if (houseId != -1) {
            viewModel.loadHouseDetails(houseId)
            observeHouseDetails()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeHouseDetails() {
        viewModel.selectedHouse.observe(viewLifecycleOwner) { house ->
            house?.let {
                binding.tvHouseTitle.text = it.title
                binding.tvPrice.text = "${Utils.formatPrice(it.pricePerNight)}/nuit"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupDatePickers() {
        val calendar = Calendar.getInstance()
        binding.etCheckIn.setOnClickListener {
            DatePickerDialog(requireContext(), { _, year, month, day ->
                binding.etCheckIn.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.etCheckOut.setOnClickListener {
            DatePickerDialog(requireContext(), { _, year, month, day ->
                binding.etCheckOut.setText("$day/${month + 1}/$year")
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupListeners() {
        binding.btnConfirmBooking.setOnClickListener {
            val booking = HouseBooking(
                id = 0,
                houseId = houseId,
                userId = 1, // À remplacer par l'ID utilisateur réel
                checkInDate = binding.etCheckIn.text.toString(),
                checkOutDate = binding.etCheckOut.text.toString(),
                guests = binding.etGuests.text.toString().toIntOrNull() ?: 1,
                totalNights = calculateNights(),
                totalPrice = calculateTotalPrice(),
                guestName = binding.etGuestName.text.toString(),
                guestPhone = binding.etGuestPhone.text.toString(),
                specialRequests = binding.etSpecialRequests.text.toString(),
                status = BookingStatus.PENDING
            )

            viewModel.bookHouse(booking)
            observeBookingResult()
        }
    }

    private fun calculateNights(): Int {
        // Implémentez la logique réelle
        return 1
    }

    private fun calculateTotalPrice(): Double {
        // Implémentez la logique réelle
        return 0.0
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
