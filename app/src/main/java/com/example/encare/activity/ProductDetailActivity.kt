package com.example.encare.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.Gravity
import android.widget.TextView

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.encare.DataLocal.SharedPreferencesOptimal
import com.example.encare.R
import com.example.encare.adapters.ProductAdapter
import com.example.encare.api.RetrofitClient
import com.example.encare.fragments.DetailProductFragment
import com.example.encare.models.DataProduct
import com.example.encare.models.DataProductResponse

import com.example.encare.models.LoginResponse
import com.example.encare.models.UserRequestLogin
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.editTextPhone
import kotlinx.android.synthetic.main.fragment_product_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class ProductDetailActivity : AppCompatActivity() {
    private var mContext: Context = this@ProductDetailActivity
    private lateinit var categoryTV: TextView
    private lateinit var nameProductTV: TextView
    private lateinit var priceProductTV: TextView
    private lateinit var descriptionProductTV: TextView
    private lateinit var soldTV: TextView
    private lateinit var quantityTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_product_detail)

        categoryTV = categoryProduct
        nameProductTV = nameProduct
        priceProductTV = price
        descriptionProductTV = description_product
        soldTV = sold
        quantityTV = quantity

        val idProductFrIntent = intent.extras?.getInt("idProduct")
        val idCategoryFrIntent = intent.extras?.getInt("idCategory")

        Log.i("Intent", idProductFrIntent.toString())
        if (idProductFrIntent != null && idCategoryFrIntent != null){
            getDetailProduct(idProductFrIntent)
            getListProductSuggest(idCategoryFrIntent,idProductFrIntent)
        }else{
            Toast.makeText(this, "idProduct from intent null", Toast.LENGTH_SHORT).show()
        }

        btn_back.setOnClickListener {
            finish()
        }

        btn_buy_now.setOnClickListener {
            Toast.makeText(mContext,"Buy Now", Toast.LENGTH_SHORT).show()
        }

    }

    // finish activity with animation
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun getDetailProduct(idProduct: Int) {
        RetrofitClient.instance.getProductById(idProduct)
            .enqueue(object : Callback<DataProductResponse>{
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

                            Glide.with(this@ProductDetailActivity).load(imageProduct).into(image_product)
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
