package com.example.all_in_one_sm

import java.util.Date

data class Order(
    val orderNumber: Int,
    val sellerName: String,
    val items: List<Item>,
    val orderDate: Date
)
data class Item(
    val name: String,
    val price: Double,
    val imageUrl: String
)