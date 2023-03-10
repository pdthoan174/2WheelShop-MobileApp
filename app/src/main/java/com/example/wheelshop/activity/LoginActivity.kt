package com.example.wheelshop.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.Gravity

import android.widget.Toast
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.api.RetrofitClient

import com.example.wheelshop.models.LoginResponse
import com.example.wheelshop.models.UserRequestLogin
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.editTextPhone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getInfoUser()
        //Log.i("hihi", isNetworkAvailable(this).toString())

        btn_login?.setOnClickListener {
            val phone = editTextPhone.text.toString().trim()
            val password:String = editTextPassword?.text.toString().trim()

            // luu tai khoan khi bam luu dang nhap vao bo nho dien thoai
            val saveLogin:Boolean = rememberLogin.isChecked
            sendRequestLogin(phone,password,true)
        }
        sign_up?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
//            finishAffinity()
        }
    }

    private fun sendRequestLogin(email: String, password: String, saveLogin: Boolean) {
        val request = UserRequestLogin()
        request.email = email
        request.password = password

        RetrofitClient.instance.login(request)
            .enqueue(object: Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    // description: trong RegisterResponse.kt
                    val postResult = response.body()
                    if (postResult != null){
//                        Log.i("hihi", data.toString())
                        val iduser = postResult.id
                        val nameUser: String? = postResult.name
                        var avatar: String? = postResult.image

                        var mail: String? = postResult.email
                        var phone: String? = postResult.phone
                        var gender: Boolean? = postResult.gender
                        var address: String? = postResult.address

//                        saveToken(token, iduser.toString(), email)
                        if (saveLogin){
                            if (password != null) {
                                SharedPreferencesOptimal.put("AVT", avatar)
                                SharedPreferencesOptimal.put("PHONE", phone)
                                SharedPreferencesOptimal.put("GENDER", gender)
                                SharedPreferencesOptimal.put("ADDRESS", address)


                                storageInfoUser(email,password, iduser.toString(), nameUser)
                            }
                        }
                        Toast.makeText(applicationContext, "Login Success",Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(applicationContext, "Login Fail",Toast.LENGTH_SHORT).show()
                        errorMessageLogin?.text = "Username or password incorrect"
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Call API Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun validate(phoneNumber:String, password:String):Boolean{
        val phoneRegex = Pattern.compile("^(0)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}\$")
        val passwordRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")

        if(!phoneRegex.matcher(phoneNumber).matches()){
            errorMessageLogin?.setText("Invalid phone number")
            return false
        }
//        else if (!passwordRegex.matcher(password).matches()){
//            errorMessageLogin.setText("Password Minimum 8 characters, at least one letter and one number")
//            return false
//        }
        else{
            errorMessageLogin?.setText("")
            return true
        }
    }

    fun storageInfoUser(username:String, password:String, userId: String, name: String?) {
//        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)
        // Kich hoat trang thai EDIT moi EDIT duoc
//        val editor = preferences.edit()
        SharedPreferencesOptimal.put("EMAIL",username)
        SharedPreferencesOptimal.put("PASSWORD", password)
        SharedPreferencesOptimal.put("USERID", userId)
        SharedPreferencesOptimal.put("NAME", name)

        Log.i("userID", "username $username password $password userId $userId")

    }

    private fun getInfoUser(){
//        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)
        val phone = SharedPreferencesOptimal.get("EMAIL",String::class.java)
        val password = SharedPreferencesOptimal.get("PASSWORD", String::class.java)
        editTextPhone.setText(phone)
        editTextPassword?.setText(password)
    }

    fun saveToken(token: String,idUser: String,email: String){
//        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
        SharedPreferencesOptimal.put("TOKEN", token)
        SharedPreferencesOptimal.put("ID", idUser)
//        SharedPreferencesOptimal.put("EMAIL", email)
    }

}
