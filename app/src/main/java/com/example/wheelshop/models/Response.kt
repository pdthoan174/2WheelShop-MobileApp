package com.example.wheelshop.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Response {
    @SerializedName("status")
    @Expose
    open var statuss:Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null
}