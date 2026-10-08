package com.atik.coffeeshop.features.explore.data.datasources

sealed interface BannerImage {
    data class Resource(
        val id: Int
    ) : BannerImage

    data class Url(
        val url: String
    ) : BannerImage
}