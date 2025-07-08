package com.example.app.ui.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.data.model.common.ServiceType
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.ui.common.BaseFragment

class HomeFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_home

    private lateinit var viewModel: HomeViewModel
    private lateinit var servicesAdapter: ServicesAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupClickListeners()

        viewModel.loadHomeData()
    }

    private fun setupViewModel() {
        val repository = ServiceRepositoryImpl()
        val factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }


    private fun setupRecyclerView() {
        servicesAdapter = ServicesAdapter { serviceType ->
            when (serviceType) {
                ServiceType.TAXI_BROUSSE -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToTaxiBrousseList()
                    )
                }
                ServiceType.MAISON -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToHouseList()
                    )
                }
                ServiceType.LOCATION_VOITURE -> {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeToCarRentalList()
                    )
                }
            }
        }

        binding.recyclerViewServices.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = servicesAdapter
        }
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvUserName.text = user.name
            binding.tvUserLocation.text = user.location

            // Charger l'image de profil
            Glide.with(this)
                .load(user.profileImageUrl)
                .circleCrop()
                .into(binding.ivUserProfile)
        }

        viewModel.services.observe(viewLifecycleOwner) { services ->
            servicesAdapter.submitList(services)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let { showError(it) }
        }
    }

    private fun setupClickListeners() {
        binding.btnCreateBooking.setOnClickListener {
            // Navigation vers création de réservation
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToCreateBooking(
                    carId = 1
                )
            )
        }

        binding.ivNotifications.setOnClickListener {
            // Gérer les notifications
        }
    }
}
