package com.example.encare.adapters

import retrofit2.Call
import retrofit2.http.GET

interface api {

    @GET("posts/1")
    fun getPost():Call<posts>

}