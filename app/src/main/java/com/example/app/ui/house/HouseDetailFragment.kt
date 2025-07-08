package com.example.app.ui.house

import HouseViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentHouseDetailBinding
import com.example.app.ui.common.BaseFragment
import com.example.app.ui.common.Utils
import me.relex.circleindicator.CircleIndicator3

class HouseDetailFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_house_detail
    private lateinit var binding: FragmentHouseDetailBinding
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
        binding = FragmentHouseDetailBinding.bind(view)

        setupViewModel()
        loadHouseDetails()
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
                binding.apply {
                    tvTitle.text = it.title
                    tvAddress.text = "${it.address}, ${it.city}"
                    tvPrice.text = "${Utils.formatPrice(it.pricePerNight)}/nuit"
                    tvRating.text = Utils.formatRating(it.rating)
                    tvBedrooms.text = "${it.bedrooms} chambres"
                    tvBathrooms.text = "${it.bathrooms} salles de bain"
                    tvGuests.text = "Jusqu'à ${it.guests} personnes"
                    tvDescription.text = it.description
                    tvOwner.text = "Propriétaire: ${it.ownerName}"
                    tvOwnerPhone.text = it.ownerPhone

                    val amenitiesText = it.amenities.joinToString(" • ")
                    tvAmenities.text = amenitiesText

                    val adapter = HouseImageAdapter(it.images)
                    viewPagerImages.adapter = adapter
                    indicator.setViewPager(viewPagerImages)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.btnBookNow.setOnClickListener {
            val action = HouseDetailFragmentDirections
                .actionHouseDetailToBooking(houseId)
            findNavController().navigate(action)
        }
    }
}

class HouseImageAdapter(private val images: List<String>) :
    RecyclerView.Adapter<HouseImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_house_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(images[position])
            .into(holder.imageView)
    }

    override fun getItemCount() = images.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: androidx.appcompat.widget.AppCompatImageView =
            itemView.findViewById(R.id.ivHouseImage)
    }
}
