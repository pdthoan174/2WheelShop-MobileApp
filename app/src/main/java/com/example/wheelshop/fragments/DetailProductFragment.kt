package com.example.wheelshop.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.wheelshop.R
import com.example.wheelshop.adapters.ProductAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.DataProduct
import com.example.wheelshop.models.DataProductResponse
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.fragment_product_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailProductFragment : Fragment() {
    private lateinit var mContext: Context
    private lateinit var categoryTV: TextView
    private lateinit var nameProductTV: TextView
    private lateinit var priceProductTV: TextView
    private lateinit var descriptionProductTV: TextView
    private lateinit var soldTV: TextView
    private lateinit var quantityTV: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_detail, container, false)
        categoryTV = view.category_product
        nameProductTV = view.name_product
        priceProductTV = view.price_product
        descriptionProductTV = view.description_product
        soldTV = view.sold_product
        quantityTV = view.quantity_product
//        category.text = "tesst nha hihi "
        val bundle: Bundle? = arguments
        val idProductFrBundle = bundle?.getInt("idProduct",0)

        if (idProductFrBundle != null){
            getDetailProduct(idProductFrBundle)
        }else{
            Toast.makeText(mContext, "idProductFrBundle null", Toast.LENGTH_SHORT).show()
        }
        getListProductSuggest(7,75)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    private fun getDetailProduct(idProduct: Int) {
        RetrofitClient.instance.getProductById(idProduct)
            .enqueue(object : Callback<DataProductResponse>{
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<DataProductResponse>,
                    response: Response<DataProductResponse>
                ) {
                    if (response.isSuccessful){
                        val detailProduct: DataProductResponse? = response.body()
                        if (detailProduct!=null) {
                            val imageProduct = detailProduct.image
                            val categoryProduct = detailProduct.category.categoryName
                            val nameProduct = detailProduct.name
                            val priceProduct = detailProduct.price
                            val priceFormat = NumberFormat.getCurrencyInstance(Locale("vie","vn")).format(priceProduct)

                            val descriptionProduct = detailProduct.description
                            val sold = detailProduct.sold
                            val quantity = detailProduct.quantity

                            Glide.with(mContext).load(imageProduct).into(image_product)
                            categoryTV.text = categoryProduct
                            nameProductTV.text = nameProduct
                            priceProductTV.text = priceFormat.toString()
                            descriptionProductTV.text = descriptionProduct
                            soldTV.text = "Đã bán: $sold"
                            quantityTV.text = "Số lượng sản phẩm: $quantity"

                        }
                        Log.i("hihi", detailProduct.toString())
                    }
                }

                override fun onFailure(call: Call<DataProductResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun getListProductSuggest(categoryId: Int, productId: Int) {
        RetrofitClient.instance.getListProductSuggest(categoryId, productId)
            .enqueue(object : Callback<DataProduct>{
                override fun onResponse(call: Call<DataProduct>, response: Response<DataProduct>) {
                    if (response.isSuccessful){
                        val listSuggest: ArrayList<DataProductResponse>? = response.body()
                        if (listSuggest!=null){
                            val adapter = ProductAdapter(listSuggest,"vertical", 6)
                            list_product_suggest.adapter = adapter
//                            list_product_suggest.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                            list_product_suggest.layoutManager = GridLayoutManager(mContext, 3)

                            Toast.makeText(mContext, "Call list product suggest success",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(mContext, "Call list product suggest fail",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
                    Toast.makeText(mContext, "Call api product suggest fail",Toast.LENGTH_SHORT).show()

                }

            })
    }
    
}