package com.example.wheelshop.models

class CartRespone : ArrayList<CartItem>()

data class CartItem(
    val cart: Cart,
    val cartDetailId: Int,
    var price: Int,
    val product: Product,
    var quantity: Int
)

data class Cart(
    val address: Any,
    val amount: Int,
    var cartId: Int,
    val phone: String,
    val user: User
)

data class Product(
    val category: Category,
    val description: String,
    val discount: Int,
    val enteredDate: String,
    val image: String,
    val name: String,
    var price: Int,
    var productId: Int,
    val quantity: Int,
    val sold: Int,
    val status: Boolean
)

data class User(
    val address: Any,
    val email: String,
    val gender: Any,
    val image: Any,
    val name: String,
    val password: String,
    val phone: String,
    val registerDate: Any,
    val roles: List<Role>,
    val status: Boolean,
    val token: String,
    val userId: Int
)






