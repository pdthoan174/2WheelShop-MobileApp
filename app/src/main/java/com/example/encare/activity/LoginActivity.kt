package com.example.encare.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.encare.R
import com.example.encare.api.RetrofitClient
import com.example.encare.models.LoginResponse
import com.example.encare.models.User
import com.example.encare.models.UserRequestLogin
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

        btn_login?.setOnClickListener {
            val phone = editTextPhone.text.toString().trim()
            val password:String = editTextPassword?.text.toString().trim()
//            if (validate(phone, password)){
//                sendRequestLogin(phone, password)
//
//            }

            // luu tai khoan khi bam luu dang nhap vao bo nho
            val check:Boolean = rememberLogin.isChecked
            if (check){
                storageInfoUser(phone, password)
            }
            sendRequestLogin("0987654321","0987654321")

        }
        sign_up?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
//            finishAffinity()
        }
    }

    // bien global dung de luu token khi dang nhap
    var token:String = ""

    fun sendRequestLogin(phone: String, password: String) {
        val request = UserRequestLogin()
        request.phone = phone
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
                        // du lieu truyen sang Home
                        val accountId = postResult.data?.accountId
                        val role = postResult.data?.role
                        val password = postResult.data?.password
                        token = postResult.data?.token.toString()
                        var info: User? = null

                        if (accountId != null && role != null && password != null && token != null){
                            info = User(accountId,role,password,token)
                        }

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)

                        intent.putExtra("info",info)
                        Toast.makeText(applicationContext, "Login Success",Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }else{
                        Toast.makeText(applicationContext, "Login Fail",Toast.LENGTH_SHORT).show()
                        errorMessageLogin?.text = "Username or password incorrect"
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

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

    fun storageInfoUser(username:String, password:String ) {
        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)

        // Kich hoat trang thai EDIT moi EDIT duoc
        val editor = preferences.edit()
        editor.putString("PHONE",username)
        editor.putString("PASSWORD",password)
        editor.apply()
    }

    fun getInfoUser(){
        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)

        val phone = preferences.getString("PHONE","")
        val password = preferences.getString("PASSWORD","")
        editTextPhone.setText(phone)
        editTextPassword.setText(password)
    }
}