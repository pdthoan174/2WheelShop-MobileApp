package com.example.encare.api

import com.example.encare.models.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiServices {

    @POST("api/user/login")
    fun login(
        @Body userRequest: UserRequestLogin
    ):Call<LoginResponse>

    @POST("api/user/registerUser")
    fun register(
        @Body userRequest: UserRequestRegister
    ):Call<RegisterResponse>

    @GET("api/patient/myProfile")
    // them token vao header
    fun getProfile(@Header("Authorization") authToken:String

    ):Call<ProfileResponse>


}