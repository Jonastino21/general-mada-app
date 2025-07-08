package com.example.app.ui.house

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.data.model.house.House
import com.example.app.ui.common.Utils

class HouseAdapter(
    private val onHouseClick: (House) -> Unit
) : RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    private var houses = listOf<House>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newHouses: List<House>) {
        houses = newHouses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_house, parent, false)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        holder.bind(houses[position])
    }

    override fun getItemCount(): Int = houses.size

    inner class HouseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.tv_house_title)
        private val addressText: TextView = itemView.findViewById(R.id.tv_house_address)
        private val priceText: TextView = itemView.findViewById(R.id.tv_house_price)
        private val ratingText: TextView = itemView.findViewById(R.id.tv_house_rating)
        private val bedroomsText: TextView = itemView.findViewById(R.id.tv_bedrooms)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_house_image)

        @SuppressLint("SetTextI18n")
        fun bind(house: House) {
            titleText.text = house.title
            addressText.text = "${house.address}, ${house.city}"
            priceText.text = "${Utils.formatPrice(house.pricePerNight)}/nuit"
            ratingText.text = Utils.formatRating(house.rating)
            bedroomsText.text = "${house.bedrooms} chambres • ${house.guests} personnes"

            // Charger l'image avec Glide
            if (house.images.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(house.images.first())
                    .into(imageView)
            }

            itemView.setOnClickListener {
                onHouseClick(house)
            }
        }
    }
}
