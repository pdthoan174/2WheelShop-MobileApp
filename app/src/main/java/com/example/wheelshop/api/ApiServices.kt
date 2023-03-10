package com.example.wheelshop.api

import com.example.wheelshop.models.*
import retrofit2.Call
import retrofit2.http.*
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

    // add product to cart
    @POST("/api/cartDetail")
    fun addProductToCart(
        @Body addCart: HashMap<String, Any>
    ):Call<CartItem>

    // update item in cart
    @PUT("/api/cartDetail/")
    fun updateItemInCart(
        @Body cartDetail: HashMap<String,Any>
    ):Call<CartItem>

    //delete product in cart
    @DELETE("/api/cartDetail/{id}")
    fun deleteProductInCart(
        @Path("id") id: Int
    ):Call<Any>

    @POST("/api/orders/{mail}")
    fun createOrder(
        @Path("mail") mail: String,
        @Body order: HashMap<String,Any>
    ):Call<OrderResponse>

    @GET("/api/orders/user/{mail}")
    fun getListOrder(
        @Path("mail") mail: String,
    ):Call<ListOrderResponse>

    @GET("/api/orderDetail/order/{orderId}")
    fun getListDetailOrder(
        @Path("orderId") orderId: Int,
    ):Call<ListHistoryOrderResponse>

    @GET("/api/orders/cancel/{orderId}")
    fun cancelOrder(
        @Path("orderId") orderId: Int
    ):Call<Any>

    @GET("/api/categories")
    fun getListCategory(

    ):Call<ListCategory>

    @GET("/api/products/category/{id}")
    fun getListProductById(
        @Path("id") id: Int
    ):Call<DataProduct>

    @POST("/api/send-mail/otp")
    fun getOTP(
        @Body email: String
    ):Call<Any>

}