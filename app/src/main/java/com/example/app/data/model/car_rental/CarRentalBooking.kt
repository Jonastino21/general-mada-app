package com.example.app.data.model.car_rental

import android.location.Location
import com.example.app.data.model.common.BookingStatus
import java.sql.Date
import java.sql.Driver

data class CarRentalBooking(
    val id: Int,
    val carId: Int,
    val userId: Int,
    val pickupDate: String,
    val returnDate: String,
    val pickupLocation: String,
    val returnLocation: String,
    val totalDays: Int,
    val totalPrice: Double,
    val driverName: String,
    val driverLicense: String,
    val driverPhone: String,
    val withDriver: Boolean = false,
    val status: BookingStatus
)