package com.example.wheelshop.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.adapters.OrderAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.ListOrderResponse
import com.example.wheelshop.models.OrderResponse
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var mContext: Context
    private var isOrderEmpty = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private val idUser = SharedPreferencesOptimal.get("USERID", String::class.java)

    private var avatar: String = ""
    private var name: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var gender: Boolean = true
    private var address: String = ""

    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        getProfile(idUser.toInt())
        loadProfile(view)

        getListOrder(email)

        return view
    }

    private fun loadProfile(view: View) {
        avatar = SharedPreferencesOptimal.get("AVT", String::class.java)
        name = SharedPreferencesOptimal.get("NAME", String::class.java)
        email = SharedPreferencesOptimal.get("EMAIL", String::class.java)
        phone = SharedPreferencesOptimal.get("PHONE", String::class.java)
        address = SharedPreferencesOptimal.get("ADDRESS", String::class.java)
        gender = SharedPreferencesOptimal.get("GENDER", Boolean::class.java)

        if (avatar == "") {
            view.imageProfile.setImageResource(R.drawable.avatars)
        } else {
            // load using Glide
            Glide.with(mContext)
                .load(avatar)
                .error(R.drawable.avatars)
                .into(view.imageProfile)
        }

        view.inputName.setText(name)
        view.inputEmail.setText(email)
        view.inputPhone.setText(phone)
        val gioitinh = if (gender) {
            "Nam"
        } else {
            "Nữ"
        }
        view.inputGioiTinh.setText(gioitinh)

        view.inputSoNha.setText(address)
    }

    override fun onResume() {
        super.onResume()
        getListOrder(email)
    }

    public fun getListOrder(mail: String) {
        RetrofitClient.instance.getListOrder(mail)
            .enqueue(object : Callback<ListOrderResponse> {
                override fun onResponse(
                    call: Call<ListOrderResponse>,
                    response: Response<ListOrderResponse>
                ) {
                    if (response.isSuccessful) {
                        val listOrder: ArrayList<OrderResponse>? = response.body()
                        if (listOrder?.isEmpty() == true) {
                            isOrderEmpty = true
                            orderEmpty.visibility = View.VISIBLE
                        } else {
                            isOrderEmpty = false
                            if (orderEmpty!=null) {
                                orderEmpty.visibility = View.INVISIBLE
                            }
                            if (listOrder != null) {
                                val adapter = OrderAdapter(listOrder, listOrder.size)
                                listOrderRV.adapter = adapter
                                listOrderRV.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
//                            setTotalCart(adapter)
                            }

                        }
                        Toast.makeText(mContext, "Cập nhật danh sách đơn hàng", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ListOrderResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }



}