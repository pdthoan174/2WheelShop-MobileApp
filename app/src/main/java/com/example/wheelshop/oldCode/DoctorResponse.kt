package com.example.wheelshop.oldCode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DoctorResponse {
    @SerializedName("data")
    @Expose
    var data: Doctor? = null

    @SerializedName("status")
    @Expose
    var status:Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

}
