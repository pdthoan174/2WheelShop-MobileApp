package com.example.encare.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity

import android.widget.Toast
import com.example.encare.DataLocal.SharedPreferencesOptimal
import com.example.encare.R
import com.example.encare.api.RetrofitClient
import com.example.encare.models.DataX
import com.example.encare.models.LoginResponse
import com.example.encare.models.UserRequestLogin
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.editTextPhone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {
    // bien global dung de luu token khi dang nhap
    var token:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getInfoUser()
        Log.i("hihi", isNetworkAvailable(this).toString())

        if (!isNetworkAvailable(this)){
            val toast = Toast.makeText(this, "Please connect internet and try again",Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 20, 30)
            toast.show()
        }


        btn_login?.setOnClickListener {
            val phone = editTextPhone.text.toString().trim()
            val password:String = editTextPassword?.text.toString().trim()
//            if (validate(phone, password)){
//                sendRequestLogin(phone, password)
//
//            }
            // luu tai khoan khi bam luu dang nhap vao bo nho dien thoai
            val saveLogin:Boolean = rememberLogin.isChecked
            sendRequestLogin(phone,password,saveLogin)

        }
        sign_up?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
//            finishAffinity()
        }
    }

    fun sendRequestLogin(phone: String, password: String, saveLogin: Boolean) {
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
                        // du lieu user truyen sang Home

                        val accountId = postResult.data?.accountId
                        val role = postResult.data?.role

                        token = postResult.data?.token.toString()

                        val info:LoginResponse? = null

                        var data: DataX? = null

                        if (accountId != null && role != null && password != null && token != null){
                            data = DataX(accountId, password, role,  token)
                        }
//                        Log.i("hihi", data.toString())

                        token = "Bearer "+postResult.data?.token.toString()
                        saveToken()
                        if (saveLogin){
                            if (password != null) {
                                storageInfoUser(phone,password)
                            }
                        }

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        intent.putExtra("info",info)

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

    fun storageInfoUser(username:String, password:String) {
//        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)
        // Kich hoat trang thai EDIT moi EDIT duoc
//        val editor = preferences.edit()
        SharedPreferencesOptimal.put("PHONE",username)
        SharedPreferencesOptimal.put("PASSWORD", password)

    }

    fun getInfoUser(){
//        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)
        val phone = SharedPreferencesOptimal.get("PHONE",String::class.java)
        val password = SharedPreferencesOptimal.get("PASSWORD", String::class.java)
        editTextPhone.setText(phone)
        editTextPassword?.setText(password)
    }

    fun saveToken(){
//        val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE)
//        val editor = preferences.edit()
        SharedPreferencesOptimal.put("TOKEN", token)

    }
    // kiểm tra ket noi internet
    fun isNetworkAvailable(context: Context):Boolean{
        val connectivityManager:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.getActiveNetworkInfo()
        return activeNetworkInfo!= null && activeNetworkInfo.isConnected
    }


}
