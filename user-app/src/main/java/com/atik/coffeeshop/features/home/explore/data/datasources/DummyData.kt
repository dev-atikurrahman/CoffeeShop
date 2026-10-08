package com.atik.coffeeshop.features.home.explore.data.datasources

import com.atik.coffeeshop.features.home.explore.data.models.Category
import com.atik.coffeeshop.features.home.explore.data.models.ItemsModel
import com.atik.coffeeshop.features.home.explore.data.models.BannerModel

object DummyData {

    val dummyCategories = listOf(
        Category(0, "Espresso"),
        Category(1, "Cappuccino"),
        Category(2, "Latte"),
        Category(3, "Americano"),
        Category(4, "Hot Chocolate")
    )

    val dummyBanners = listOf(
        BannerModel(
            url = "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217423/banner_dkb33i.png"
        ),
        BannerModel(
            url = "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217423/banner_dkb33i.png"
        ),
        BannerModel(
            url = "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217423/banner_dkb33i.png"
        ),
        BannerModel(
            url = "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217423/banner_dkb33i.png"
        )
    )

    val dummyPicUr = listOf(
        "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217428/5_ob9ojh.png",
        "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217423/banner_dkb33i.png",
        "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217423/banner_dkb33i.png",
        "https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217423/banner_dkb33i.png",
    )

    val dummyCopyList = listOf(
        ItemsModel(
            id = "1",
            title = "Cappoccino",
            description = "Cappuccino is a traditional Italian coffee drink made with equal parts espresso, steamed milk, and milk foam. Cappuccino is a traditional Italian coffee drink made with equal parts espresso, steamed milk, and milk foamCappuccino is a traditional Italian coffee drink made with equal parts espresso, steamed milk, and milk foam",
            picUrl = listOf("https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217428/5_ob9ojh.png"),
            price = 4.5,
            rating = 4.6,
            extra = "Essperso,Milk"
        ),
        ItemsModel(
            id = "2",
            title = "Espersso",
            description = "Espresso is a concentrated form of coffee served in small, strong shots...",
            picUrl = listOf("https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217434/4_iprgrm.png"),
            price = 3.5,
            rating = 4.0,
            extra = "Espersso"
        ),
        ItemsModel(
            id = "3",
            title = "Affagato",
            description = "Espresso is a concentrated form of coffee served in small, strong shots...",
            picUrl = listOf("https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217417/6_cpunsg.png"),
            price = 8.0,
            rating = 4.1,
            extra = "Espersso,milk,IceCream"
        ),
        ItemsModel(
            id = "4",
            title = "Americano",
            description = "Espresso is a concentrated form of coffee served in small, strong shots...",
            picUrl = listOf("https://res.cloudinary.com/dkikc5ywq/image/upload/v1770217445/8_xg3pwo.png"),
            price = 5.5,
            rating = 4.4,
            extra = "Espersso, milk"
        )
    )

}