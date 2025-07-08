package com.example.app.ui.common

object Utils {
    fun formatPrice(price: Double): String {
        return "${price.toInt()} Ar"
    }

    fun formatDate(dateString: String): String {
        // Formatage de date (à compléter selon tes besoins)
        return dateString
    }

    fun formatDuration(duration: String): String {
        return duration
    }

    fun formatRating(rating: Float): String {
        return "★ $rating"
    }
}
