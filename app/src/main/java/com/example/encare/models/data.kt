package com.example.encare.models
import com.google.gson.annotations.SerializedName


data class data(
    @SerializedName("data")
    val data: List<InfoDoctor>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("status")
    val status: Int?
)

data class InfoDoctor(
    @SerializedName("accountResponse")
    val accountResponse: AccountResponse?,
    @SerializedName("categoryResponse")
    val categoryResponse: CategoryResponse?,
    @SerializedName("countRating")
    val countRating: Int?,
    @SerializedName("distance")
    val distance: Any?,
    @SerializedName("doctorId")
    val doctorId: Int?,
    @SerializedName("hospitalResponse")
    val hospitalResponse: HospitalResponse?,
    @SerializedName("rating")
    val rating: Int?
)

data class AccountResponse(
    @SerializedName("accountId")
    val accountId: Int?,
    @SerializedName("avatar")
    val avatar: Any?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("createDate")
    val createDate: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: Any?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("updateDate")
    val updateDate: Any?
)

data class CategoryResponse(
    @SerializedName("categoryId")
    val categoryId: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("name")
    val name: String?
)

data class HospitalResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("countRating")
    val countRating: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("hospitalId")
    val hospitalId: Int?,
    @SerializedName("latMap")
    val latMap: Int?,
    @SerializedName("longMap")
    val longMap: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rating")
    val rating: Int?
)