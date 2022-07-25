package com.example.encare.activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.encare.R

import com.example.encare.api.RetrofitClient

import com.example.encare.models.ProfileResponse
import com.example.encare.models.User
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class MainActivity : AppCompatActivity() {

    var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getToken()
        getProfile()

    }

//    fun getInfoIntent(){
//        val intent = intent
//        if (intent != null) {
//            val infoUser: User = intent.getSerializableExtra("info") as User
//            test.text = "Account ID: "+infoUser.accountId.toString()
//            test.append("\nRole: "+infoUser.role)
//            test.append("\nPass: "+infoUser.password)
//            test.append("\nToken: "+infoUser.token)
//            // Chuoi token de add vao Header
//            token = "Bearer "+infoUser.token.toString()
//        }
//    }

    fun getToken(){
        val preferences: SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)
        token = preferences.getString("TOKEN", "").toString()
        Log.i("hihi",token)
        test.text = token

    }

    fun getProfile(){
        RetrofitClient.instance.getProfile(token)
            .enqueue(object: Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    // description: trong RegisterResponse.kt
                    val infoProfile = response.body()

                    val accountResponse = infoProfile?.data?.accountResponse
                    if ( accountResponse!= null){
                        var accountId = accountResponse.accountId
                    }

                    if (response.isSuccessful){
                        Toast.makeText(applicationContext, "Get profile success", Toast.LENGTH_SHORT).show()
                        textName.text = infoProfile?.data?.accountResponse?.name
                        var avatar = infoProfile?.data?.accountResponse?.avatar

                        if (avatar == null){
                            avt1.setImageResource(R.drawable.avatar)
                        }else{
                            // load using Glide
                            Glide.with(applicationContext)
                                .load(avatar)
                                .error(R.drawable.avatar)
//                            .override(20,20)
//                            .placeholder(R.drawable.load)
                                .into(avt1)
                        }
                    }else{
                        Toast.makeText(applicationContext, "Get profile Fail", Toast.LENGTH_SHORT).show()
//                        Toast.makeText(applicationContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Call api error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}