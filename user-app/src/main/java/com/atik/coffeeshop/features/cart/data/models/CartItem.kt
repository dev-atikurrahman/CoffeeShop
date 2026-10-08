package com.atik.coffeeshop.features.cart.data.models

import com.atik.coffeeshop.features.explore.data.models.ItemsModel

data class CartItem(
    val item: ItemsModel,
    val quantity: Int
)
