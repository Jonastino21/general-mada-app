package com.example.app.data.mock

import com.example.app.data.model.car_rental.CarRental
import com.example.app.data.model.car_rental.CarRentalBooking
import com.example.app.data.model.common.BookingStatus
import com.example.app.data.model.common.HouseType
import com.example.app.data.model.common.ServiceCard
import com.example.app.data.model.common.ServiceType
import com.example.app.data.model.common.User
import com.example.app.data.model.common.VehicleType
import com.example.app.data.model.house.House
import com.example.app.data.model.house.HouseBooking
import com.example.app.data.model.taxi_brousse.TaxiBrousseBooking
import com.example.app.data.model.taxi_brousse.TaxiBrousseRoute
import kotlinx.coroutines.delay

class MockDataRepository {

    companion object {
        // Mock User
        val mockUser = User(
            id = 1,
            name = "Rakoto Andry",
            email = "rakoto.andry@gmail.com",
            phone = "+261 32 12 345 67",
            location = "Antananarivo, Madagascar",
            profileImageUrl = "https://example.com/profile.jpg"
        )

        // Mock Services
        val mockServices = listOf(
            ServiceCard(
                serviceType = ServiceType.TAXI_BROUSSE,
                title = "Taxi Brousse",
                description = "Réservez vos places dans les taxi-brousse",
                icon = "🚌",
                color = "#FF6B6B"
            ),
            ServiceCard(
                serviceType = ServiceType.MAISON,
                title = "Hébergement",
                description = "Trouvez votre maison idéale",
                icon = "🏠",
                color = "#4ECDC4"
            ),
            ServiceCard(
                serviceType = ServiceType.LOCATION_VOITURE,
                title = "Location Voiture",
                description = "Louez une voiture pour vos déplacements",
                icon = "🚗",
                color = "#45B7D1"
            )
        )

        // Mock Taxi Brousse Routes
        val mockTaxiBrousseRoutes = listOf(
            TaxiBrousseRoute(
                id = 1,
                departure = "Antananarivo",
                destination = "Tamatave",
                departureTime = "06:00",
                arrivalTime = "12:00",
                duration = "6h",
                price = 15000.0,
                vehicleType = "Minibus 18 places",
                availableSeats = 12,
                companyName = "Madarail Express",
                rating = 4.2f,
                imageUrl = "https://example.com/taxi_brousse1.jpg"
            ),
            TaxiBrousseRoute(
                id = 2,
                departure = "Antananarivo",
                destination = "Antsirabe",
                departureTime = "07:30",
                arrivalTime = "10:30",
                duration = "3h",
                price = 8000.0,
                vehicleType = "Minibus 25 places",
                availableSeats = 8,
                companyName = "Cotisse Transport",
                rating = 4.5f,
                imageUrl = "https://example.com/taxi_brousse2.jpg"
            ),
            TaxiBrousseRoute(
                id = 3,
                departure = "Antananarivo",
                destination = "Mahajanga",
                departureTime = "05:00",
                arrivalTime = "14:00",
                duration = "9h",
                price = 25000.0,
                vehicleType = "Bus 40 places",
                availableSeats = 15,
                companyName = "Malagasy Lines",
                rating = 4.0f,
                imageUrl = "https://example.com/taxi_brousse3.jpg"
            )
        )

        // Mock Houses
        val mockHouses = listOf(
            House(
                id = 1,
                title = "Villa moderne avec piscine - Ivandry",
                type = HouseType.VILLA,
                address = "Lot 123 Ivandry",
                city = "Antananarivo",
                pricePerNight = 80000.0,
                bedrooms = 4,
                bathrooms = 3,
                guests = 8,
                description = "Magnifique villa moderne avec piscine, jardin tropical et vue panoramique sur la ville.",
                amenities = listOf(
                    "Piscine",
                    "WiFi",
                    "Climatisation",
                    "Parking",
                    "Jardin",
                    "Sécurité"
                ),
                images = listOf("villa1.jpg", "villa2.jpg", "villa3.jpg"),
                rating = 4.8f,
                reviews = 24,
                ownerId = 1,
                ownerName = "Madame Ratsimba",
                ownerPhone = "+261 33 11 222 33"
            ),
            House(
                id = 2,
                title = "Appartement cosy - Analakely",
                type = HouseType.APPARTEMENT,
                address = "Rue Rainandriamampandry",
                city = "Antananarivo",
                pricePerNight = 35000.0,
                bedrooms = 2,
                bathrooms = 1,
                guests = 4,
                description = "Appartement moderne en centre-ville, proche des commerces et restaurants.",
                amenities = listOf("WiFi", "Cuisine équipée", "Balcon", "Ascenseur"),
                images = listOf("appart1.jpg", "appart2.jpg"),
                rating = 4.3f,
                reviews = 18,
                ownerId = 2,
                ownerName = "Monsieur Randria",
                ownerPhone = "+261 32 44 555 66"
            ),
            House(
                id = 3,
                title = "Maison traditionnelle - Ambohimanga",
                type = HouseType.MAISON_TRADITIONNELLE,
                address = "Ambohimanga Rova",
                city = "Ambohimanga",
                pricePerNight = 25000.0,
                bedrooms = 3,
                bathrooms = 2,
                guests = 6,
                description = "Maison traditionnelle malgache authentique près du site royal d'Ambohimanga.",
                amenities = listOf("Jardin", "Cuisine traditionnelle", "Parking", "Vue montagne"),
                images = listOf("trad1.jpg", "trad2.jpg", "trad3.jpg"),
                rating = 4.6f,
                reviews = 31,
                ownerId = 3,
                ownerName = "Madame Razafy",
                ownerPhone = "+261 34 77 888 99"
            )
        )

        // Mock Car Rentals
        val mockCarRentals = listOf(
            CarRental(
                id = 1,
                brand = "Toyota",
                model = "Hilux",
                year = 2020,
                type = VehicleType.PICKUP,
                pricePerDay = 45000.0,
                fuelType = "Diesel",
                transmission = "Manuelle",
                seats = 5,
                features = listOf("4x4", "Climatisation", "GPS", "Bluetooth"),
                images = listOf("hilux1.jpg", "hilux2.jpg"),
                rating = 4.7f,
                location = "Antananarivo",
                rentalCompany = "Mada Rent",
                companyPhone = "+261 20 22 123 45"
            ),
            CarRental(
                id = 2,
                brand = "Renault",
                model = "Logan",
                year = 2019,
                type = VehicleType.SEDAN,
                pricePerDay = 25000.0,
                fuelType = "Essence",
                transmission = "Manuelle",
                seats = 5,
                features = listOf("Climatisation", "Radio", "Economique"),
                images = listOf("logan1.jpg", "logan2.jpg"),
                rating = 4.2f,
                location = "Antananarivo",
                rentalCompany = "Tana Cars",
                companyPhone = "+261 32 11 222 33"
            ),
            CarRental(
                id = 3,
                brand = "Mitsubishi",
                model = "Pajero",
                year = 2021,
                type = VehicleType.SUV,
                pricePerDay = 55000.0,
                fuelType = "Diesel",
                transmission = "Automatique",
                seats = 7,
                features = listOf("4x4", "Climatisation", "GPS", "Bluetooth", "Cuir"),
                images = listOf("pajero1.jpg", "pajero2.jpg"),
                rating = 4.9f,
                location = "Antananarivo",
                rentalCompany = "Premium Cars Madagascar",
                companyPhone = "+261 33 44 555 66"
            )
        )

        // Mock Bookings
        val mockTaxiBrousseBookings = listOf(
            TaxiBrousseBooking(
                id = 1,
                routeId = 1,
                userId = 1,
                passengerName = "Rakoto Andry",
                passengerPhone = "+261 32 12 345 67",
                seatNumber = 15,
                bookingDate = "2025-07-05",
                travelDate = "2025-07-10",
                totalPrice = 15000.0,
                status = BookingStatus.CONFIRMED
            )
        )

        val mockHouseBookings = listOf(
            HouseBooking(
                id = 1,
                houseId = 1,
                userId = 1,
                checkInDate = "2025-07-15",
                checkOutDate = "2025-07-18",
                guests = 4,
                totalNights = 3,
                totalPrice = 240000.0,
                guestName = "Rakoto Andry",
                guestPhone = "+261 32 12 345 67",
                specialRequests = "Arrivée tardive prévue",
                status = BookingStatus.CONFIRMED
            )
        )

        val mockCarRentalBookings = listOf(
            CarRentalBooking(
                id = 1,
                carId = 1,
                userId = 1,
                pickupDate = "2025-07-20",
                returnDate = "2025-07-25",
                pickupLocation = "Aéroport Ivato",
                returnLocation = "Aéroport Ivato",
                totalDays = 5,
                totalPrice = 225000.0,
                driverName = "Rakoto Andry",
                driverLicense = "1234567890",
                driverPhone = "+261 32 12 345 67",
                withDriver = false,
                status = BookingStatus.PENDING
            )
        )
    }

    // TAXI BROUSSE METHODS
    suspend fun getTaxiBrousseRoutes(): List<TaxiBrousseRoute> {
        delay(500)
        return mockTaxiBrousseRoutes
    }

    suspend fun searchTaxiBrousseRoutes(departure: String, destination: String): List<TaxiBrousseRoute> {
        delay(600)
        return mockTaxiBrousseRoutes.filter {
            it.departure.contains(departure, ignoreCase = true) &&
                    it.destination.contains(destination, ignoreCase = true)
        }
    }

    suspend fun bookTaxiBrousse(booking: TaxiBrousseBooking): Boolean {
        delay(800)
        return true
    }

    // HOUSE METHODS
    suspend fun getHouses(): List<House> {
        delay(500)
        return mockHouses
    }

    suspend fun searchHouses(city: String, guests: Int): List<House> {
        delay(600)
        return mockHouses.filter {
            it.city.contains(city, ignoreCase = true) && it.guests >= guests
        }
    }

    suspend fun getHouseById(id: Int): House? {
        delay(300)
        return mockHouses.find { it.id == id }
    }

    suspend fun bookHouse(booking: HouseBooking): Boolean {
        delay(800)
        return true
    }

    // CAR RENTAL METHODS
    suspend fun getCarRentals(): List<CarRental> {
        delay(500)
        return mockCarRentals
    }

    suspend fun searchCarRentals(location: String, type: VehicleType?): List<CarRental> {
        delay(600)
        return mockCarRentals.filter {
            it.location.contains(location, ignoreCase = true) &&
                    (type == null || it.type == type)
        }
    }

    suspend fun getCarRentalById(id: Int): CarRental? {
        delay(300)
        return mockCarRentals.find { it.id == id }
    }

    suspend fun bookCarRental(booking: CarRentalBooking): Boolean {
        delay(800)
        return true
    }

    // COMMON METHODS
    suspend fun getUserProfile(): User {
        delay(200)
        return mockUser
    }

    suspend fun getServices(): List<ServiceCard> {
        delay(300)
        return mockServices
    }

    suspend fun getUserBookings(userId: Int): Map<String, Any> {
        delay(600)
        return mapOf(
            "taxiBrousse" to mockTaxiBrousseBookings.filter { it.userId == userId },
            "houses" to mockHouseBookings.filter { it.userId == userId },
            "carRentals" to mockCarRentalBookings.filter { it.userId == userId }
        )
    }
}
