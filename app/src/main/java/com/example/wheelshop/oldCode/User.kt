package com.example.wheelshop.oldCode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User: Serializable {
    @SerializedName("accountId")
    @Expose
    var accountId:Int? = null

    @SerializedName("role")
    @Expose
    var role:String? = null

    @SerializedName("password")
    @Expose
    var password:String? = null

    @SerializedName("token")
    @Expose
    var token:String? = null

    constructor(accountId:Int, role:String, password:String, token:String){
        this.accountId = accountId
        this.role = role
        this.password = password
        this.token = token
    }
}