package com.example.app.data.model.taxi_brousse

data class TaxiBrousseRoute(
    val id: Int,
    val departure: String,
    val destination: String,
    val departureTime: String,
    val arrivalTime: String,
    val duration: String,
    val price: Double,
    val vehicleType: String,
    val availableSeats: Int,
    val companyName: String,
    val rating: Float,
    val imageUrl: String
)