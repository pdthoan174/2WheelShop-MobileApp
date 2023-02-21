package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateItemCartRequest{
    @SerializedName("cartDetailId")
    @Expose
    var cartDetailId: Int? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null

    @SerializedName("price")
    @Expose
    var price: Int? = null

    @SerializedName("productId")
    @Expose
    var product: Product? = null

    @SerializedName("cartId")
    @Expose
    var cart: Cart? = null

}


