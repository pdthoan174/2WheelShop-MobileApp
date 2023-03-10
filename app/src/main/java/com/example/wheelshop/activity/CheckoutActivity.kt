package com.example.wheelshop.activity

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.OrderResponse
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.checkout_dialog.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class CheckoutActivity : AppCompatActivity() {
    private var mContext: Context = this@CheckoutActivity
    private val userId = SharedPreferencesOptimal.get("USERID",String::class.java)
    private val mail = SharedPreferencesOptimal.get("EMAIL",String::class.java)
    private val cartId = SharedPreferencesOptimal.get("CART",String::class.java)
    var name = SharedPreferencesOptimal.get("NAME",String::class.java)

    var phone = ""
    private var tinh = ""
    var huyen = ""
    var xa = ""
    var sonha = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        inputName.setText(name)

        btn_back.setOnClickListener {
            finish()
        }

        btn_confirm.setOnClickListener {
            name = inputName.text.toString()
            phone = inputSDT.text.toString()

            tinh = inputTinh.text.toString()
            huyen = inputHuyen.text.toString()
            xa = inputPhuong.text.toString()
            sonha = inputSoNha.text.toString()

            val address = "$sonha, Phường: $xa, Quận: $huyen, Tỉnh: $tinh"

            if (name == "" || phone == "" || tinh == "" || huyen == "" || xa == "" || sonha == ""){
                Toast.makeText(mContext,"Thông tin còn thiếu", Toast.LENGTH_SHORT).show()

            }else{
                checkout(cartId.toInt(),address, phone,userId.toInt(),mail)
            }
        }

        btnReset.setOnClickListener {
            inputSDT.setText("")
            inputTinh.setText("")
            inputHuyen.setText("")
            inputPhuong.setText("")
            inputSoNha.setText("")
        }


    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun checkout(cartId: Int, address: String, phone:String, userId: Int, mail:String) {
        val map = HashMap<String, Any>()

        map["cartId"] = cartId
        map["address"] = address
        map["phone"] = phone

        val userObj = HashMap<String,Any>()
        userObj["userId"] = userId

        map["cart"] = userObj

        RetrofitClient.instance.createOrder(mail,map)
            .enqueue(object : Callback<OrderResponse> {
                override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                    if (response.isSuccessful){
                        showConfirmDialog()

                    }else{
                        Toast.makeText(mContext, "Cart Failed!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    Toast.makeText(mContext, "Call Api Add Cart Failed!", Toast.LENGTH_SHORT).show()

                }

            })
    }


    private fun showConfirmDialog() {
        val view = View.inflate(this@CheckoutActivity, R.layout.checkout_dialog, null)
        val builder = AlertDialog.Builder(this@CheckoutActivity)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        view.btn_confirm.setOnClickListener {
            dialog.dismiss()
            this.finish()
        }

    }
}