package com.example.app.ui.taxi_brousse

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentTaxiBrousseListBinding
import com.example.app.ui.common.BaseFragment

class TaxiBrousseListFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_taxi_brousse_list

    private lateinit var viewModel: TaxiBrousseViewModel
    private lateinit var adapter: TaxiBrousseAdapter
    private lateinit var binding: FragmentTaxiBrousseListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaxiBrousseListBinding.bind(view)

        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupSearchFunctionality()

        viewModel.loadRoutes()
    }

    private fun setupViewModel() {
        val repository = ServiceRepositoryImpl()
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaxiBrousseViewModel(repository) as T
            }
        })[TaxiBrousseViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = TaxiBrousseAdapter { route ->
            findNavController().navigate(
                TaxiBrousseListFragmentDirections.actionTaxiBrousseListToBooking(route.id)
            )
        }

        binding.recyclerViewRoutes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TaxiBrousseListFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.routes.observe(viewLifecycleOwner) { routes ->
            adapter.submitList(routes)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let { showError(it) }
        }
    }

    private fun setupSearchFunctionality() {
        binding.btnSearch.setOnClickListener {
            val departure = binding.etDeparture.text.toString()
            val destination = binding.etDestination.text.toString()

            if (departure.isNotEmpty() && destination.isNotEmpty()) {
                viewModel.searchRoutes(departure, destination)
            }
        }
    }
}

private fun TaxiBrousseListFragmentDirections.Companion.actionTaxiBrousseListToBooking(id: Int) {}
