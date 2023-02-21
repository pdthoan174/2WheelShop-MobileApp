package com.example.wheelshop.models

data class CartEmailRespose(
    val address: String,
    val amount: Int,
    val cartId: Int,
    val phone: String,
    val user: User
)





