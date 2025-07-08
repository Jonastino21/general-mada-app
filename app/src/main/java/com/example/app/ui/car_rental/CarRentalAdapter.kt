package com.example.app.ui.car_rental

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.data.model.car_rental.CarRental
import com.example.app.ui.common.Utils

class CarRentalAdapter(
    private val onCarClick: (CarRental) -> Unit
) : RecyclerView.Adapter<CarRentalAdapter.CarViewHolder>() {

    private var cars = listOf<CarRental>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newCars: List<CarRental>) {
        cars = newCars
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car_rental, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    override fun getItemCount(): Int = cars.size

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandModelText: TextView = itemView.findViewById(R.id.tv_brand_model)
        private val priceText: TextView = itemView.findViewById(R.id.tv_car_price)
        private val transmissionText: TextView = itemView.findViewById(R.id.tv_transmission)
        private val seatsText: TextView = itemView.findViewById(R.id.tv_seats)
        private val ratingText: TextView = itemView.findViewById(R.id.tv_car_rating)
        private val companyText: TextView = itemView.findViewById(R.id.tv_rental_company)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_car_image)

        @SuppressLint("SetTextI18n")
        fun bind(car: CarRental) {
            brandModelText.text = "${car.brand} ${car.model}"
            priceText.text = "${Utils.formatPrice(car.pricePerDay)}/jour"
            transmissionText.text = car.transmission
            seatsText.text = "${car.seats} places"
            ratingText.text = Utils.formatRating(car.rating)
            companyText.text = car.rentalCompany

            // Charger l'image avec Glide
            if (car.images.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(car.images.first())
                    .into(imageView)
            }

            itemView.setOnClickListener {
                onCarClick(car)
            }
        }
    }
}
