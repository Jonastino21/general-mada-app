package com.example.app.data.model.car_rental

import android.media.Rating
import com.example.app.data.model.common.VehicleType

data class CarRental(
    val id: Int,
    val brand: String,
    val model: String,
    val year: Int,
    val type: VehicleType,
    val pricePerDay: Double,
    val fuelType: String,
    val transmission: String,
    val seats: Int,
    val features: List<String>,
    val images: List<String>,
    val rating: Float,
    val location: String,
    val rentalCompany: String,
    val companyPhone: String,
    val isAvailable: Boolean = true
)