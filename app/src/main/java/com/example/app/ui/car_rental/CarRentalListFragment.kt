package com.example.app.ui.car_rental

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.R
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentCarRentalListBinding
import com.example.app.ui.common.BaseFragment
class CarRentalListFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_car_rental_list
    private lateinit var binding: FragmentCarRentalListBinding
    private lateinit var viewModel: CarRentalViewModel
    private lateinit var adapter: CarRentalAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarRentalListBinding.bind(view)

        setupViewModel()
        setupRecyclerView()
        setupSearch()
        loadCars()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CarRentalViewModel(ServiceRepositoryImpl()) as T
            }
        })[CarRentalViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = CarRentalAdapter { car ->
            val action = CarRentalListFragmentDirections
                .actionCarRentalListToDetail(car.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CarRentalListFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchCars(query, null)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun loadCars() {
        viewModel.loadCars()
        observeCars()
    }

    private fun observeCars() {
        viewModel.cars.observe(viewLifecycleOwner) { cars ->
            adapter.submitList(cars)
        }
    }
}
