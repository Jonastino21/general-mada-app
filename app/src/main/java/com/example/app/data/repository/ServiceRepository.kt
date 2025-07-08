package com.example.app.data.repository

import com.example.app.data.model.car_rental.CarRental
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.common.ServiceCard
import com.example.app.data.model.common.User
import com.example.app.data.model.common.VehicleType
import com.example.app.data.model.house.House
import com.example.app.data.model.house.HouseBooking
import com.example.app.data.model.taxi_brousse.TaxiBrousseBooking
import com.example.app.data.model.taxi_brousse.TaxiBrousseRoute

interface ServiceRepository {
    // Common
    suspend fun getUserProfile(): User
    suspend fun getServices(): List<ServiceCard>
    suspend fun getUserBookings(userId: Int): Map<String, Any>

    // Taxi Brousse
    suspend fun getTaxiBrousseRoutes(): List<TaxiBrousseRoute>
    suspend fun searchTaxiBrousseRoutes(departure: String, destination: String): List<TaxiBrousseRoute>
    suspend fun bookTaxiBrousse(booking: TaxiBrousseBooking): Boolean

    // Houses
    suspend fun getHouses(): List<House>
    suspend fun searchHouses(city: String, guests: Int): List<House>
    suspend fun getHouseById(id: Int): House?
    suspend fun bookHouse(booking: HouseBooking): Boolean

    // Car Rentals
    suspend fun getCarRentals(): List<CarRental>
    suspend fun searchCarRentals(location: String, type: VehicleType?): List<CarRental>
    suspend fun getCarRentalById(id: Int): CarRental?
    suspend fun bookCarRental(booking: CarRentalBooking): Boolean
}