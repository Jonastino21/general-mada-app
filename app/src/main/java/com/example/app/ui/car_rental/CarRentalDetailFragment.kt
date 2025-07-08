package com.example.app.ui.car_rental

import CarRentalViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.app.databinding.FragmentCarRentalDetailBinding
import com.example.app.ui.common.BaseFragment
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.ui.common.Utils
import com.example.app.R

class CarRentalDetailFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_car_rental_detail
    private lateinit var binding: FragmentCarRentalDetailBinding
    private lateinit var viewModel: CarRentalViewModel
    private var carId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            carId = it.getInt("carId", -1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarRentalDetailBinding.bind(view)

        setupViewModel()
        loadCarDetails()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CarRentalViewModel(ServiceRepositoryImpl()) as T
            }
        })[CarRentalViewModel::class.java]
    }

    private fun loadCarDetails() {
        if (carId != -1) {
            viewModel.loadCarDetails(carId)
            observeCarDetails()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeCarDetails() {
        viewModel.selectedCar.observe(viewLifecycleOwner) { car ->
            car?.let {
                binding.apply {
                    tvBrandModel.text = "${it.brand} ${it.model} (${it.year})"
                    tvPrice.text = "${Utils.formatPrice(it.pricePerDay)}/jour"
                    tvType.text = it.type.toString()
                    tvFuelType.text = it.fuelType
                    tvTransmission.text = it.transmission
                    tvSeats.text = "${it.seats} places"
                    tvCompany.text = it.rentalCompany
                    tvCompanyPhone.text = it.companyPhone
                    tvLocation.text = it.location
                    ratingBar.rating = it.rating

                    // Charger les images
                    if (it.images.isNotEmpty()) {
                        Glide.with(this@CarRentalDetailFragment)
                            .load(it.images.first())
                            .into(ivCarImage)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnBookNow.setOnClickListener {
            val action = CarRentalDetailFragmentDirections
                .actionCarRentalDetailToBooking(carId)
            findNavController().navigate(action)
        }
    }
}
