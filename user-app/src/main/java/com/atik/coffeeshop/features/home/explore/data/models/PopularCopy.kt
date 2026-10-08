package com.atik.coffeeshop.features.home.explore.data.models

data class PopularCopy(
    val id: Int,
    val thumbnail: List<String>,
    val title: String,
    val description: String,
    val price: Double
)