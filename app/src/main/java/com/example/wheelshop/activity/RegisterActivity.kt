package com.example.wheelshop.activity

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private var otp:Int = 0
    private var email: String = ""
    private var name:String = ""
    private var phoneNumber:String = ""
    private var address:String = ""
    private var password:String = ""
    private var confirmPassword:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sign_in.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        btn_get_otp.setOnClickListener {

            email = editTextEmail.text.toString().trim()
            getOTP(email)

        }

        btn_register.setOnClickListener {
            name = editTextName.text.toString().trim()
            phoneNumber = editTextPhone.text.toString().trim()
            email = editTextEmail.text.toString().trim()

            val otpTextView = textOTP.text.toString()
            if (otpTextView != ""){
                otp = otpTextView.toInt()
            }
            address = editTextAddress.text.toString()
            password = editTextPassword.text.toString().trim()
            confirmPassword = editConfirmPassword.text.toString().trim()
            Log.i("test", validateInputs(name,email,otp, phoneNumber,address, password, confirmPassword).toString())

            if (validateInputs(name,email,otp, phoneNumber,address, password, confirmPassword)){
                sendRequestRegister(phoneNumber, password, name, email,address)
                SharedPreferencesOptimal.put("OTP", 0)
            }

        }
    }

    private fun getOTP(email: String) {
        RetrofitClient.instance.getOTP(email)
            .enqueue(object: Callback<Any>{
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    // description: trong RegisterResponse.kt
                    if (response.isSuccessful){
                        Toast.makeText(applicationContext, "Gửi mã OTP thành công",Toast.LENGTH_SHORT).show()
                        val postResult = response.body()
                        val otpInt = postResult.toString().toDouble().toInt()
                        SharedPreferencesOptimal.put("OTP", otpInt)
                        SharedPreferencesOptimal.put("EMAIL_OTP", email)
                        Log.i("test",otpInt.toString())
                    }

                }
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(applicationContext, "Gửi mã OTP thất bại",Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun sendRequestRegister(phone: String, password: String, name: String, email: String, address: String) {
        val request = UserRequestRegister()
        request.phone = phone
        request.password = password
        request.name = name
        request.email = email
        request.address = address
        request.status = true

        RetrofitClient.instance.register(request)
            .enqueue(object: Callback<RegisterResponse>{
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    // description: trong RegisterResponse.kt
                    Toast.makeText(applicationContext, "Register Successful",Toast.LENGTH_SHORT).show()
                    val postResult = response.body()

                    Log.i("test",postResult.toString())
                    if (postResult != null){
                        errorMessage.text = postResult.message


                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message,Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun validateInputs(name: String, email: String, otp: Int, phoneNumber: String, address: String, password: String, confirmPassword: String): Boolean {
        val emailRegex = Regex("^\\S+@\\S+\\.\\S+$")
        val phoneRegex = Regex("^\\d{10}$")

        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

        if (name.isEmpty()) {
            return false
        }

        if (email.isEmpty()) {
            errorMessage.text = "Email cannot be empty"
            return false
        }

        if (otp == 0) {
            errorMessage.text = "OTP must be not null"
            return false
        }else if(!checkOTP(otp, email)){
            return false
        }

        if (phoneNumber.isEmpty()) {
            errorMessage.text = "Phone number cannot be empty"
            return false
        }

        if (address.isEmpty()) {
            return false
        }

        if (password.isEmpty()) {
            errorMessage.text = "Password cannot be empty"
            return false
        }

        if (confirmPassword.isEmpty()) {
            errorMessage.text = "Confirm password cannot be empty"
            return false
        }

        if (!email.matches(emailRegex)) {
            errorMessage.text = "Invalid email format"
            return false
        }

        if (!phoneNumber.matches(phoneRegex)) {
            errorMessage.text = "Invalid phone number format"
            return false
        }

        if (password != confirmPassword) {
            errorMessage.text = "Passwords do not match"
            return false
        }
        errorMessage.text = ""
        return true
    }

    private fun checkOTP(otp: Int, emailOTP: String):Boolean{
        val otpStorage = SharedPreferencesOptimal.get("OTP",Int::class.java)
        val emailOTPStorage = SharedPreferencesOptimal.get("EMAIL_OTP",String::class.java)
        if (otp != otpStorage || emailOTP != emailOTPStorage){
            Toast.makeText(this,"Mã OTP không đúng", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}