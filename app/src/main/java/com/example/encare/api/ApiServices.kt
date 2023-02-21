package com.example.encare.api

import com.example.encare.models.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap


interface ApiServices {
    // phuong thuc truy van cua Retrofit
    @POST("/api/auth/signin")
    fun login(
        @Body userRequest: UserRequestLogin
    ):Call<LoginResponse>

    @POST("/api/auth/signup")
    fun register(
        @Body userRequest: UserRequestRegister
    ):Call<RegisterResponse>

    @GET("api/auth/{id}")
    // them token vao header
    fun getProfile(
        @Path("id") id: Int
    ):Call<ProfileResponse>

//    @GET("api/user/listDoctor")
//    fun getListDoctor(@Header("Authorization") authToken:String, @Query("categoryId") categoryId:Int
//
//    ):Call<DataDoctor>


    //get sản phẩm thịnh hành
    @GET("/api/products/rated")
    fun getlistProductRate():Call<DataProduct>

    // get sản phẩm bán chạy
    @GET("/api/products/bestseller")
    fun getlistProductBestSeller():Call<DataProduct>
    // get sản phẩm mới
    @GET("/api/products/latest")
    fun getlistNewProduct():Call<DataProduct>

    // get all product
    @GET("/api/products")
    fun getAllProduct():Call<DataProduct>

    @GET("/api/products/{id}")
    fun getProductById(
        @Path("id") id: Int
    ):Call<DataProductResponse>

    // get list product suggest
    @GET("/api/products/suggest/{categoryId}/{productId}")
    fun getListProductSuggest(
        @Path("categoryId") categoryId:Int,
        @Path("productId") productId:Int
    ):Call<DataProduct>

    @GET("/api/cart/user/{email}")
    fun getCartId(
        @Path("email") email: String
    ):Call<CartEmailRespose>


    @GET("/api/cartDetail/cart/{id}")
    fun getListProductCart(
        @Path("id") id: Int
    ):Call<CartRespone>

    @POST("/api/cartDetail")
    fun addaCart(
        @Body addCart: AddCartRequest
    ):Call<CartRespone>

    // update item in cart

    @PUT("/api/cartDetail/")
    fun updateItemInCart(
        @Body cartDetail: HashMap<String,Any>
    ):Call<CartItem>

}