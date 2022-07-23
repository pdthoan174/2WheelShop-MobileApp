package com.example.encare.adapters

import android.util.Base64
import com.example.encare.api.ApiServices
import com.example.encare.api.RetrofitClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val AUTH = "Basic"+ Base64.encodeToString("pdt123".toByteArray(), Base64.NO_WRAP)

    // create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // create OkHttpClient
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger).build()

    val instance: api by lazy{
        // create Retrofit Builder
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        retrofit.create(api::class.java)
    }



}