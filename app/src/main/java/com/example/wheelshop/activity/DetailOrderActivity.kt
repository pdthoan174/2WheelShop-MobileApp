package com.example.wheelshop.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheelshop.R
import com.example.wheelshop.adapters.DetailOrderAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.ListHistoryOrderResponse
import com.example.wheelshop.models.ListOrderResponse
import com.example.wheelshop.models.OrderItem
import kotlinx.android.synthetic.main.activity_detail_order.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.fragment_product_detail.btn_back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailOrderActivity : AppCompatActivity() {

    private var mContext: Context = this@DetailOrderActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)
        val ordersIdFrIntent = intent.extras?.getInt("ordersId")

        if (ordersIdFrIntent != null) {
            getListOrderDetail(ordersIdFrIntent)
        }
        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


    private fun getListOrderDetail(ordersId: Int){
        RetrofitClient.instance.getListDetailOrder(ordersId)
            .enqueue(object : Callback<ListHistoryOrderResponse> {
                override fun onResponse(
                    call: Call<ListHistoryOrderResponse>,
                    response: Response<ListHistoryOrderResponse>
                ) {
                    if (response.isSuccessful){
                        val listOrder: ArrayList<OrderItem>? = response.body()

                            if (listOrder != null){
                                val adapter = DetailOrderAdapter(listOrder)
                                listItemOrder.adapter = adapter
                                listItemOrder.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
//                            setTotalCart(adapter)
                            }


                        Toast.makeText(mContext, "List Order", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ListHistoryOrderResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }
}