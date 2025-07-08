package com.example.app.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.house.HouseBooking
import com.example.app.data.model.taxi_brousse.TaxiBrousseBooking
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentMyBookingsBinding
import com.example.app.ui.common.BaseFragment
import com.example.app.ui.profiles.ProfileViewModel

class MyBookingsFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_my_bookings
    private lateinit var binding: FragmentMyBookingsBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: BookingsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyBookingsBinding.bind(view)

        setupViewModel()
        setupRecyclerView()
        loadBookings()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(ServiceRepositoryImpl()) as T
            }
        })[ProfileViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = BookingsAdapter { booking ->
            when (booking) {
                is TaxiBrousseBooking -> {
                    // TODO: Navigation vers détails taxi
                }
                is HouseBooking -> {
                    // TODO: Navigation vers détails maison
                }
                is CarRentalBooking -> {
                    // TODO: Navigation vers détails voiture
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MyBookingsFragment.adapter
        }
    }

    private fun loadBookings() {
        viewModel.loadBookings(1) // ID utilisateur à remplacer
        observeBookings()
    }

    private fun observeBookings() {
        viewModel.bookings.observe(viewLifecycleOwner) { bookings ->
            val allBookings = mutableListOf<Any>()
            bookings?.get("taxiBrousse")?.let { allBookings.addAll(it as List<*>) }
            bookings?.get("houses")?.let { allBookings.addAll(it as List<*>) }
            bookings?.get("carRentals")?.let { allBookings.addAll(it as List<*>) }

            adapter.submitList(allBookings.sortedByDescending {
                when (it) {
                    is TaxiBrousseBooking -> it.bookingDate
                    is HouseBooking -> it.checkInDate
                    is CarRentalBooking -> it.pickupDate
                    else -> ""
                }
            })
        }
    }
}

private fun MutableList<Any>.addAll(elements: List<*>): Boolean {
    TODO("Not yet implemented")
}

class BookingsAdapter(
    private val onBookingClick: (Any) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bookings = listOf<Any>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newBookings: List<Any>) {
        bookings = newBookings
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (bookings[position]) {
            is TaxiBrousseBooking -> TYPE_TAXI
            is HouseBooking -> TYPE_HOUSE
            is CarRentalBooking -> TYPE_CAR
            else -> throw IllegalArgumentException("Type inconnu")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TAXI -> TaxiBookingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_taxi_booking, parent, false)
            )
            TYPE_HOUSE -> HouseBookingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_house_booking, parent, false)
            )
            TYPE_CAR -> CarBookingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_car_booking, parent, false)
            )
            else -> throw IllegalArgumentException("Type inconnu")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = bookings[position]) {
            is TaxiBrousseBooking -> (holder as TaxiBookingViewHolder).bind(item)
            is HouseBooking -> (holder as HouseBookingViewHolder).bind(item)
            is CarRentalBooking -> (holder as CarBookingViewHolder).bind(item)
        }
    }

    override fun getItemCount() = bookings.size

    companion object {
        private const val TYPE_TAXI = 1
        private const val TYPE_HOUSE = 2
        private const val TYPE_CAR = 3
    }

    inner class TaxiBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(booking: TaxiBrousseBooking) {
            // TODO: afficher les données du taxi
            itemView.setOnClickListener { onBookingClick(booking) }
        }
    }

    inner class HouseBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(booking: HouseBooking) {
            // TODO: afficher les données maison
            itemView.setOnClickListener { onBookingClick(booking) }
        }
    }

    inner class CarBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(booking: CarRentalBooking) {
            // TODO: afficher les données voiture
            itemView.setOnClickListener { onBookingClick(booking) }
        }
    }
}
