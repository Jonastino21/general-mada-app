package com.example.app.data.model.house

import com.example.app.data.model.common.HouseType

data class House(
    val id: Int,
    val title: String,
    val type: HouseType,
    val address: String,
    val city: String,
    val pricePerNight: Double,
    val bedrooms: Int,
    val bathrooms: Int,
    val guests: Int,
    val description: String,
    val amenities: List<String>,
    val images: List<String>,
    val rating: Float,
    val reviews: Int,
    val ownerId: Int,
    val ownerName: String,
    val ownerPhone: String,
    val isAvailable: Boolean = true
)