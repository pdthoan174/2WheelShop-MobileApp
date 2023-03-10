package com.example.wheelshop.models
import com.google.gson.annotations.SerializedName

class DataProduct : ArrayList<DataProductResponse>()


data class DataProductResponse (
    @SerializedName("category")
    val category: Category,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("enteredDate")
    val enteredDate: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("sold")
    val sold: Int,
    @SerializedName("status")
    val status: Boolean
)

class ListCategory : ArrayList<Category>()

data class Category(
    val categoryId: Int,
    val categoryName: String
)

