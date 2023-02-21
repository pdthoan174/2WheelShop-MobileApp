package com.example.wheelshop.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddCartRequest{
    @SerializedName("cart")
    @Expose
    var cart: Cart ? = null
    @SerializedName("cartDetailId")
    @Expose
    var cartDetailId: Int? = null

    @SerializedName("price")
    @Expose
    var price: Int? = null

    @SerializedName("product")
    @Expose
    var product: Product? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
}


