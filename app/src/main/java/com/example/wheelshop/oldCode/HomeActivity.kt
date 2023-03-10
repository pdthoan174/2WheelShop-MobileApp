package com.example.wheelshop.oldCode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R

import com.example.wheelshop.api.RetrofitClient

import com.example.wheelshop.models.ProfileResponse

import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    var token: String = ""
    private  var idUser: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        getToken()
        getProfile(idUser.toInt())

    }

    private fun getToken(){
        token = SharedPreferencesOptimal.get("TOKEN", String::class.java)
        idUser = SharedPreferencesOptimal.get("ID", String::class.java)
        Log.i("hihi",idUser)

    }

    private fun getProfile(id: Int){
        RetrofitClient.instance.getProfile(id)
            .enqueue(object: Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    // description: trong RegisterResponse.kt
                    val infoProfile = response.body()

                    val accountResponse = infoProfile
                    if ( accountResponse!= null){
                        var accountId = accountResponse.userId
                    }

                    if (response.isSuccessful){
                        Toast.makeText(applicationContext, "Get profile success", Toast.LENGTH_SHORT).show()
                        textName.text = infoProfile?.name
                        var avatar = infoProfile?.image

                        if (avatar == null){
                            avt1.setImageResource(R.drawable.avatars)
                        }else{
                            // load using Glide
                            Glide.with(applicationContext)
                                .load(avatar)
                                .error(R.drawable.avatars)
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