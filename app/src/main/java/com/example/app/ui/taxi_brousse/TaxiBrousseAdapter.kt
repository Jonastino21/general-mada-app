package com.example.app.ui.taxi_brousse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.taxi_brousse.TaxiBrousseRoute
import com.example.app.ui.common.Utils

class TaxiBrousseAdapter(
    private val onRouteClick: (TaxiBrousseRoute) -> Unit
) : RecyclerView.Adapter<TaxiBrousseAdapter.RouteViewHolder>() {

    private var routes = listOf<TaxiBrousseRoute>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newRoutes: List<TaxiBrousseRoute>) {
        routes = newRoutes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_taxi_brousse_route, parent, false)
        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(routes[position])
    }

    override fun getItemCount(): Int = routes.size

    inner class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val departureText: TextView = itemView.findViewById(R.id.tv_departure)
        private val destinationText: TextView = itemView.findViewById(R.id.tv_destination)
        private val timeText: TextView = itemView.findViewById(R.id.tv_time)
        private val priceText: TextView = itemView.findViewById(R.id.tv_price)
        private val companyText: TextView = itemView.findViewById(R.id.tv_company)
        private val availableSeatsText: TextView = itemView.findViewById(R.id.tv_available_seats)
        private val ratingText: TextView = itemView.findViewById(R.id.tv_rating)

        @SuppressLint("SetTextI18n")
        fun bind(route: TaxiBrousseRoute) {
            departureText.text = route.departure
            destinationText.text = route.destination
            timeText.text = "${route.departureTime} - ${route.arrivalTime}"
            priceText.text = Utils.formatPrice(route.price)
            companyText.text = route.companyName
            availableSeatsText.text = "${route.availableSeats} places"
            ratingText.text = Utils.formatRating(route.rating)

            itemView.setOnClickListener {
                onRouteClick(route)
            }
        }
    }
}
