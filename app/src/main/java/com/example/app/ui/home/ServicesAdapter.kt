package com.example.app.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.common.ServiceCard
import com.example.app.data.model.common.ServiceType
import androidx.core.graphics.toColorInt

class ServicesAdapter(
    private val onServiceClick: (ServiceType) -> Unit
) : RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>() {

    private var services = listOf<ServiceCard>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newServices: List<ServiceCard>) {
        services = newServices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service_card, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.tv_service_title)
        private val descriptionText: TextView = itemView.findViewById(R.id.tv_service_description)
        private val iconText: TextView = itemView.findViewById(R.id.tv_service_icon)
        private val cardView: CardView = itemView.findViewById(R.id.card_service)

        fun bind(service: ServiceCard) {
            titleText.text = service.title
            descriptionText.text = service.description
            iconText.text = service.icon

            // Définir la couleur de fond
            cardView.setCardBackgroundColor(service.color.toColorInt())

            itemView.setOnClickListener {
                onServiceClick(service.serviceType)
            }
        }
    }
}
