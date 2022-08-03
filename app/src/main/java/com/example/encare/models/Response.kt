package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Response {

    @SerializedName("status")
    @Expose
    var status:Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null


}