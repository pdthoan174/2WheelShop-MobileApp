package com.example.encare.adapters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class posts{
    @SerializedName("userId")
    @Expose
    val userId:Int = 0
    @SerializedName("id")
    @Expose
    val id:Int = 0
    @SerializedName("title")
    @Expose
    val title:String =""
    @SerializedName("body")
    @Expose
    val body:String = ""



}

