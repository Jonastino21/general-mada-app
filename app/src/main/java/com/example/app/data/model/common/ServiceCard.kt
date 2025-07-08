package com.example.app.data.model.common

import android.icu.text.CaseMap

data class ServiceCard(
    val serviceType: ServiceType,
    val title: String,
    val description: String,
    val icon: String,
    val color: String
)