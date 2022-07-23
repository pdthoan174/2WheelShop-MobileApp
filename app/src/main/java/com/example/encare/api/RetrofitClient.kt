package com.example.encare.api

import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



// Sington Pattern
object RetrofitClient {
    private val AUTH = "Basic"+ Base64.encodeToString("pdt123".toByteArray(), Base64.NO_WRAP)
    private const val BASE_URL = "https://enclave-encare.herokuapp.com/"

    // moi
    // create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // create OkHttpClient
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger).build()

    //Cu
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val original = chain.request()
//            val requestBuilder = original.newBuilder()
//                .addHeader("Authorization", AUTH)
//                .method(original.method, original.body)
//
//            val request = requestBuilder.build()
//            chain.proceed(request)
//        }.build()

    val instance: ApiServices by lazy{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        retrofit.create(ApiServices::class.java)
    }

}