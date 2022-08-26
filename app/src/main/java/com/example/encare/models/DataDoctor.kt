package com.example.encare.models
import com.google.gson.annotations.SerializedName

class DataDoctor: Response() {
    @SerializedName("data")
    var data: ArrayList<DataDoctorResponse>? = null
}

class DataDoctorResponse {
    @SerializedName("accountResponse")
    var accountResponse: AccountResponse? = null

    @SerializedName("categoryResponse")
    var categoryResponse: CategoryResponse? = null

    @SerializedName("countRating")
    var countRating: Int? = null

    @SerializedName("distance")
    var distance: Double? = null

    @SerializedName("doctorId")
    var doctorId: Int? = null

    @SerializedName("hospitalResponse")
    var hospitalResponse: HospitalResponse? = null

    @SerializedName("rating")
    var rating: Int? = null
    constructor(
        accountResponse:AccountResponse,
        categoryResponse:CategoryResponse,
        countRating:Int,
        distance:Double,
        doctorId:Int,
        hospitalResponse:HospitalResponse,
        rating:Int
    ){
        this.accountResponse = accountResponse
        this.categoryResponse = categoryResponse
        this.countRating = countRating
        this.distance = distance
        this.doctorId = doctorId
        this.hospitalResponse = hospitalResponse
        this.rating = rating
    }
}

class AccountResponse {
    @SerializedName("accountId")
    var accountId: Int? = null

    @SerializedName("avatar")
    var avatar: String? = null

    @SerializedName("birthday")
    var birthday: String? = null

    @SerializedName("createDate")
    var createDate: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("phone")
    var phone: String? = null

    @SerializedName("role")
    var role: String? = null

    @SerializedName("updateDate")
    var updateDate: String? = null

    constructor (
        accountId: Int,
        avatar: String,
        birthday:String,
        createDate:String,
        description:String,
        name:String,
        password:String,
        phone:String,
        role:String,
        updateDate:String,
        ){
        this.accountId = accountId
        this.avatar = avatar
        this.birthday = birthday
        this.createDate = createDate
        this.description = description
        this.name = name
        this.password = password
        this.phone = phone
        this.role = role
        this.updateDate = updateDate
    }

}

class CategoryResponse {
    @SerializedName("categoryId")
    var categoryId: Int? = null

    @SerializedName("description")
    var categoryDescription: String? = null

    @SerializedName("name")
    var categoryName: String? = null

    constructor(categoryId: Int, categoryDescription: String, categoryName: String){
        this.categoryId = categoryId
        this.categoryDescription = categoryDescription
        this.categoryName = categoryName
    }
}

class HospitalResponse {
    @SerializedName("address")
    var hospitalAddress: String? = null

    @SerializedName("countRating")
    var countRating: Int? = null

    @SerializedName("description")
    var hospitalDescription: String? = null

    @SerializedName("hospitalId")
    var hospitalId: Int? = null

    @SerializedName("latMap")
    var latMap: Int? = null

    @SerializedName("longMap")
    var longMap: Int? = null

    @SerializedName("name")
    var hospitalName: String? = null

    @SerializedName("rating")
    var hospitalRating: Int? = null

    constructor(
        hospitalAddress: String,
        countRating:Int,
        hospitalDescription:String,
        hospitalId:Int,
        latMap:Int,
        longMap:Int,
        hospitalName:String,
        hospitalRating:Int
        ){
        this.hospitalAddress = hospitalAddress
        this.countRating = countRating
        this.hospitalDescription =hospitalDescription
        this.hospitalId =hospitalId
        this.latMap = latMap
        this.longMap = longMap
        this.hospitalName =hospitalName
        this.hospitalRating = hospitalRating
    }
}