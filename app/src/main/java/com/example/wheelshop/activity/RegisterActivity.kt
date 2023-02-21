package com.example.wheelshop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.wheelshop.R
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        onFocusChange()

        sign_in.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        btn_register.setOnClickListener {
            val name:String = editTextName.text.toString().trim()
            val phoneNumber:String = editTextPhone.text.toString().trim()
            val email:String = editTextEmail.text.toString().trim()
            val password:String = editTextPassword.text.toString().trim()
            val confirmPassword:String = editConfirmPassword.text.toString().trim()

            if (validate(name, phoneNumber, password, confirmPassword)){
                sendRequestRegister(phoneNumber, password, name, email)
            }

        }
    }

    fun sendRequestRegister(phone: String, password: String, name: String, email: String) {
        val request = UserRequestRegister()
        request.phone = phone
        request.password = password
        request.name = name
        request.email = email
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

    fun validate(name:String, phoneNumber:String, password:String, confirmPassword:String):Boolean{
        val phoneRegex = Pattern.compile("^(0)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}\$")
        val passwordRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")

        if (name.isEmpty() || name.length < 5){
            errorMessage.setText("Your full name not null")
            return false

        }else if(!phoneRegex.matcher(phoneNumber).matches()){
            errorMessage.setText("Invalid phone number")
            return false

        }
//        else if (!passwordRegex.matcher(password).matches()){
//            errorMessage.setText("Password Minimum 8 characters, at least one letter and one number")
//            return false
//
//        }
        else if (!password.equals(confirmPassword)){
            errorMessage.setText("Incorrect confirm password")
            return false
        }
        else{
            errorMessage.setText("")
            return true
        }
    }

//    fun onFocusChange(){
//        editTextName.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
//            if (b){
//                // do something when edit text get focus
//            }else{
//                validate()
//            }
//        }
//        editTextPhone.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
//            if (b){
//                // do something when edit text get focus
//            }else{
//                validate()
//            }
//        }
//        editTextPassword.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
//            if (b){
//                // do something when edit text get focus
//            }else{
//                validate()
//            }
//        }
//        editConfirmPassword.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
//            if (b){
//                // do something when edit text get focus
//            }else{
//                validate()
//            }
//        }
//    }
}