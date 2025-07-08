package com.example.app.data.model.taxi_brousse

import com.example.app.data.model.common.BookingStatus

data class TaxiBrousseBooking(
    val id: Int,
    val routeId: Int,
    val userId: Int,
    val passengerName: String,
    val passengerPhone: String,
    val seatNumber: Int,
    val bookingDate: String,
    val travelDate: String,
    val totalPrice: Double,
    val status: BookingStatus
)