package com.example.app.data.repository

import com.example.app.data.mock.MockDataRepository
import com.example.app.data.model.common.User
import com.example.app.data.model.common.ServiceCard
import com.example.app.data.model.taxi_brousse.TaxiBrousseRoute
import com.example.app.data.model.taxi_brousse.TaxiBrousseBooking
import com.example.app.data.model.house.House
import com.example.app.data.model.car_rental.CarRental
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.common.VehicleType

class ServiceRepositoryImpl : ServiceRepository {
    private val mockDataRepository = MockDataRepository()

    override suspend fun getUserProfile(): User = mockDataRepository.getUserProfile()
    override suspend fun getServices(): List<ServiceCard> = mockDataRepository.getServices()
    override suspend fun getUserBookings(userId: Int): Map<String, Any> = mockDataRepository.getUserBookings(userId)

    override suspend fun getTaxiBrousseRoutes(): List<TaxiBrousseRoute> = mockDataRepository.getTaxiBrousseRoutes()
    override suspend fun searchTaxiBrousseRoutes(departure: String, destination: String): List<TaxiBrousseRoute> =
        mockDataRepository.searchTaxiBrousseRoutes(departure, destination)
    override suspend fun bookTaxiBrousse(booking: TaxiBrousseBooking): Boolean = mockDataRepository.bookTaxiBrousse(booking)

    override suspend fun getHouses(): List<House> = mockDataRepository.getHouses()
    override suspend fun searchHouses(city: String, guests: Int): List<House> = mockDataRepository.searchHouses(city, guests)
    override suspend fun getHouseById(id: Int): House? = mockDataRepository.getHouseById(id)
    override suspend fun bookHouse(booking: HouseBooking): Boolean = mockDataRepository.bookHouse(booking)

    override suspend fun getCarRentals(): List<CarRental> = mockDataRepository.getCarRentals()
    override suspend fun searchCarRentals(location: String, type: VehicleType?): List<CarRental> =
        mockDataRepository.searchCarRentals(location, type)
    override suspend fun getCarRentalById(id: Int): CarRental? = mockDataRepository.getCarRentalById(id)
    override suspend fun bookCarRental(booking: CarRentalBooking): Boolean = mockDataRepository.bookCarRental(booking)
}
