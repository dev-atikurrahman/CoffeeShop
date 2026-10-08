package com.atik.coffeeshop.features.explore.data.models

import java.io.Serializable


data class ItemsModel(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var picUrl: List<String> = emptyList(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var numberInCart: Int = 0,
    var extra: String = ""
) : Serializable