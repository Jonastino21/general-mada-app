package com.example.app.data.model.house

import com.example.app.data.model.common.BookingStatus

data class HouseBooking(
    val id: Int,
    val carId: Int,
    val userId: Int,
    val pickupDate: String,
    val returnDate: String,
    val totalDays: Int,
    val totalPrice: Double,
    val guestName: String,
    val guestPhone: String,
    val status: BookingStatus
)
