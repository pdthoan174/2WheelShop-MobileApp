package com.example.wheelshop.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.adapters.CartAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.*
import kotlinx.android.synthetic.main.fragment_cart.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class CartFragment : Fragment() {
    val email = SharedPreferencesOptimal.get("EMAIL", String::class.java)
    private var idCart:Int = 0

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_cart, container, false)

        return mView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        getCartID()

        super.onCreate(savedInstanceState)
    }

    private fun getCartID(){
        RetrofitClient.instance.getCartId(email)
            .enqueue(object: Callback<CartEmailRespose> {
                override fun onResponse(
                    call: Call<CartEmailRespose>,
                    response: Response<CartEmailRespose>
                ) {
                    if (response.isSuccessful){
                        idCart = response.body()?.cartId!!
                        SharedPreferencesOptimal.put("CART", idCart.toString())
                        listProductInCart(idCart)
                    }else{
                        Toast.makeText(mContext, "Call c Fail", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<CartEmailRespose>, t: Throwable) {
                    Toast.makeText(mContext, "Call CART ID Fail", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun listProductInCart(cart:Int) {
        RetrofitClient.instance.getListProductCart(cart)
            .enqueue(object: Callback<CartRespone>{
                override fun onResponse(
                    call: Call<CartRespone>,
                    response: Response<CartRespone>
                ) {
                    if (response.isSuccessful){
                        val list: ArrayList<CartItem>? = response.body()

                        //Log.i("hihi",list.toString())
                        if (list != null){
                            val adapter = CartAdapter(list,totalText)
                            listCartProduct.adapter = adapter
                            listCartProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

//                            setTotalCart(adapter)
                        }
                        Toast.makeText(mContext, "Call List Doctor Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call Doctor Fail",Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<CartRespone>, t: Throwable) {
                    Toast.makeText(mContext, "Call List Doctor Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

    // call function on adapter
//    fun setTotalCart(adapterFun: CartAdapter){
//        val total = adapterFun.formatCurrency(adapterFun.updateTotalCart())
//        totalText.text = total
//    }
}