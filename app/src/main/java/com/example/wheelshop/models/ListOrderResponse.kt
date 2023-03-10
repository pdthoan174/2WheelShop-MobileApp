package com.example.wheelshop.models


class ListOrderResponse : ArrayList<OrderResponse>()

data class OrderResponse(
    val address: String,
    val amount: Double,
    val orderDate: String,
    val ordersId: Int,
    val phone: String,
    var status: Int,
    val user: User
)
class ListHistoryOrderResponse : ArrayList<OrderItem>()

data class OrderItem(
    val order: OrderResponse,
    val orderDetailId: Int,
    val price: Double,
    val product: Product,
    val quantity: Int
)



