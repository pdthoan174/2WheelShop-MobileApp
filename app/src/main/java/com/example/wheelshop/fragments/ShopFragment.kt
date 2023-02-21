package com.example.wheelshop.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wheelshop.R
import com.example.wheelshop.activity.MainActivity
import com.example.wheelshop.adapters.ProductAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.*
import com.example.wheelshop.myInterface.ProductClickHandler
import kotlinx.android.synthetic.main.fragment_shop.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ShopFragment : Fragment(),ProductClickHandler {
    private lateinit var productClick:ProductClickHandler

    private var mainActivity = MainActivity();
    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = activity as MainActivity;
        val mView = inflater.inflate(R.layout.fragment_shop, container, false)
        listProduct()
        return mView
    }

//    fun addCart(cartDetailId: Int, quantity: Int, price: Int, product: Product, cart: Int) {
//        val request = AddCartRequest()
//        request.cartDetailId = cartDetailId
//        request.quantity = quantity
//        request.price = price
//        request.product?.productId = product.productId
////        request.cart = cart
//
//        RetrofitClient.instance.addCart(request)
//            .enqueue(object: Callback<CartRespone>{
//                override fun onResponse(
//                    call: Call<CartRespone>,
//                    response: Response<CartRespone>
//                ) {
//                    // description: trong RegisterResponse.kt
//
//                    val postResult = response.body()
//                    Log.i("test",postResult.toString())
//
//                }
//                override fun onFailure(call: Call<CartRespone>, t: Throwable) {
//                    Toast.makeText(mContext, t.message,Toast.LENGTH_SHORT).show()
//                }
//            })
//    }

    private fun listProduct(){
        RetrofitClient.instance.getAllProduct()
            .enqueue(object: Callback<DataProduct>{
                override fun onResponse(
                    call: Call<DataProduct>,
                    response: Response<DataProduct>
                ) {
                    if (response.isSuccessful){
                        val list: ArrayList<DataProductResponse>? = response.body()

                        //Log.i("hihi",list.toString())
                        if (list != null){
                            //val adapter = ProductAdapter(list,"horizontal",20)
                            val adapter = ProductAdapter(list,"vertical",list.size)
                            listAllProduct.adapter = adapter
                            //listAllProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                            listAllProduct.layoutManager = GridLayoutManager(mContext, 3)
//                            listAllProduct.isNestedScrollingEnabled = true
                        }
                        Toast.makeText(mContext, "Call List Product Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call Product Fail",Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
                    Toast.makeText(mContext, "Call List Product Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun clickedProductItem(idProduct: Int) {
        TODO("Not yet implemented")
    }
}