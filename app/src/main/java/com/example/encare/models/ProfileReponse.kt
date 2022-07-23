package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileResponse {
    @SerializedName("data")
    @Expose
    var data: Profile? = null

    @SerializedName("status")
    @Expose
    val status:Int? = null

    @SerializedName("description")
    @Expose
    val description: String? = null

}