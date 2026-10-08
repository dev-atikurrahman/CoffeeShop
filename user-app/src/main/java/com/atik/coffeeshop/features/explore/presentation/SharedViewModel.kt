package com.atik.coffeeshop.features.explore.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.atik.coffeeshop.features.explore.data.models.ItemsModel

class SharedViewModel : ViewModel() {
    var selectedItem by mutableStateOf<ItemsModel?>(null)
        private set

    var cartItems by mutableStateOf<List<ItemsModel>>(emptyList())
        private set

    var favoriteItems by mutableStateOf<List<ItemsModel>>(emptyList())
        private set

    fun selectItem(item: ItemsModel) {
        selectedItem = item
    }

    fun addToCart(item: ItemsModel) {
        cartItems = cartItems + item
    }

    fun toggleFavorite(item: ItemsModel) {
        favoriteItems = if (favoriteItems.contains(item)) {
            favoriteItems - item
        } else {
            favoriteItems + item
        }
    }

}