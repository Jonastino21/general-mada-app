package com.example.app.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.app.R
import com.example.app.data.model.common.ServiceType
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentHomeBinding
import com.example.app.ui.common.BaseFragment
import java.util.*

class HomeFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_home

    private lateinit var viewModel: HomeViewModel
    private lateinit var servicesAdapter: ServicesAdapter
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var binding: FragmentHomeBinding

    private val handler = Handler(Looper.getMainLooper())
    private var timer: Timer? = null

    private val carouselImages = listOf(
        R.drawable.carousel_image_1,
        R.drawable.carousel_image_2,
        R.drawable.carousel_image_3
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        setupViewModel()
        setupRecyclerView()
        setupCarousel()
        setupObservers()
        setupServiceClicks()

        viewModel.loadHomeData()
    }

    private fun setupViewModel() {
        val repository = ServiceRepositoryImpl()
        val factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    private fun setupCarousel() {
        carouselAdapter = CarouselAdapter(carouselImages)
        binding.viewPagerCarousel.adapter = carouselAdapter
        binding.viewPagerCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Auto-scroll toutes les 3 secondes
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = (binding.viewPagerCarousel.currentItem + 1) % carouselAdapter.itemCount
                    binding.viewPagerCarousel.setCurrentItem(nextItem, true)
                }
            }
        }, 3000, 3000)
    }

    private fun setupRecyclerView() {
        servicesAdapter = ServicesAdapter { serviceType ->
            when (serviceType) {
                ServiceType.TAXI_BROUSSE -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeToTaxiBrousseList())
                }
                ServiceType.MAISON -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeToHouseList())
                }
                ServiceType.LOCATION_VOITURE -> {
                    findNavController().navigate(HomeFragmentDirections.actionHomeToCarRentalList())
                }
            }
        }

        binding.recyclerViewServices.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = servicesAdapter
        }
    }

    private fun setupObservers() {
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

    private fun setupServiceClicks() {
        // Gestion des clics sur les services de la CardView
        binding.cardServices.findViewById<View>(R.id.layoutTaxiBrousse)?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToTaxiBrousseList())
        }

        binding.cardServices.findViewById<View>(R.id.layoutHouse)?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToHouseList())
        }

        binding.cardServices.findViewById<View>(R.id.layoutCarRental)?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeToCarRentalList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }
}