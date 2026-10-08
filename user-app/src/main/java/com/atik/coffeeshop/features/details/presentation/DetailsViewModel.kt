package com.atik.coffeeshop.features.details.presentation

import androidx.lifecycle.ViewModel
import com.atik.coffeeshop.features.details.presentation.widget.CoffeeSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailsViewModel : ViewModel() {
    private val _selectedSize = MutableStateFlow(CoffeeSize.Small)
    val selectedSize: StateFlow<CoffeeSize> = _selectedSize.asStateFlow()

    private val _selectedQty = MutableStateFlow(0)
    val selectedQty: StateFlow<Int> = _selectedQty.asStateFlow()

    fun onSizeSelected(size: CoffeeSize) {
        _selectedSize.value = size
    }


}