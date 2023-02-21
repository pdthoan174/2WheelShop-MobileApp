package com.example.wheelshop.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Profile{
    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("accountResponse")
    @Expose
    var accountResponse: ResponseAccount? = null


}