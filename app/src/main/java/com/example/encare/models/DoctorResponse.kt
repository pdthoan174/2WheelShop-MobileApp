package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DoctorResponse: Response() {
    @SerializedName("data")
    @Expose
    var data: Doctor? = null

}