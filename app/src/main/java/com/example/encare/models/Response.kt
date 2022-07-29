package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Response {

    @SerializedName("status")
    @Expose
    private var status:Int? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null


}