package com.example.encare.models
import com.google.gson.annotations.SerializedName


class DataCategory:Response(){
    @SerializedName("data")
    val data: ArrayList<DataCategoryResponse>? = null
}

class DataCategoryResponse(){
    @SerializedName("categoryId")
    val categoryId: Int? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("name")
    val name: String? = null
}
