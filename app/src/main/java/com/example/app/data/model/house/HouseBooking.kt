package com.example.app.data.model.house

import com.example.app.data.model.common.BookingStatus

data class HouseBooking(
    val id: Int,
    val houseId: Int,
    val userId: Int,
    val checkInDate: String,
    val checkOutDate: String,
    val guests: Int,
    val totalNights: Int,
    val totalPrice: Double,
    val guestName: String,
    val guestPhone: String,
    val specialRequests: String?,
    val status: BookingStatus
)
