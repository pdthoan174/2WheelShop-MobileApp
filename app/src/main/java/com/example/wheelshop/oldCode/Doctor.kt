package com.example.wheelshop.oldCode

import com.example.wheelshop.models.ResponseAccount
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Doctor() {
    @SerializedName("doctorId")
    @Expose
    var doctorId: Int? = null

    @SerializedName("rating")
    @Expose
    var rating: Int? = null

    @SerializedName("distance")
    @Expose
    var distance: String? = null

    @SerializedName("accountResponse")
    @Expose
    var accountResponse: ResponseAccount? = null

    @SerializedName("categoryResponse")
    @Expose
    var categoryResponse: CategoryResponse? = null
    class CategoryResponse {
        @SerializedName("categoryId")
        @Expose
        var categoryId: Int? = null

        @SerializedName("name")
        @Expose
        var categoryName: String? = null

        @SerializedName("description")
        @Expose
        var categoryDescription: String? = null
    }


    @SerializedName("hospitalResponse")
    @Expose
    var hospitalResponse: HospitalResponse? = null

    class HospitalResponse {
        @SerializedName("hospitalId")
        @Expose
        var hospitalId: Int? = null

          @SerializedName("description")
        @Expose
        var hospitalDescription: String? = null

          @SerializedName("rating")
        @Expose
        var hospitalRating: Int? = null

        @SerializedName("address")
        @Expose
        var hospitalAddress: String? = null

        @SerializedName("name")
        @Expose
        var hospitalName: String? = null

    }


}


