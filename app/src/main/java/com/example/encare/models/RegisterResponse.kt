package com.example.encare.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RegisterResponse(){
    @SerializedName("status")
    @Expose
    val status:String? = null

    @SerializedName("description")
    @Expose
    val description:String? = null

    @SerializedName("data")
    @Expose
    val data:UserRegister? = null

    class UserRegister{
        @SerializedName("name")
        @Expose
        val name:String?= null

        @SerializedName("phone")
        @Expose
        val phone:String? = null

        @SerializedName("password")
        @Expose
        val password:String? = null
    }

}
