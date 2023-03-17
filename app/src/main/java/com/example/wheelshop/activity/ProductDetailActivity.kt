package com.example.wheelshop.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.adapters.ProductAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.CartItem
import com.example.wheelshop.models.DataProduct
import com.example.wheelshop.models.DataProductResponse
import com.example.wheelshop.myInterface.AddToCart

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ProductDetailActivity : AppCompatActivity() {
    private var mContext: Context = this@ProductDetailActivity
    private lateinit var categoryTV: TextView
    private lateinit var nameProductTV: TextView
    private lateinit var priceProductTV: TextView
    private lateinit var descriptionProductTV: TextView
    private lateinit var soldTV: TextView
    private lateinit var quantityTV: TextView

    private lateinit var imageProduct: String
    private var productId: Int = 0
    private var cartId: String = SharedPreferencesOptimal.get("CART", String::class.java)
    private lateinit var categoryProduct: String
    private lateinit var nameProduct: String
    private var priceProduct: Int = 0
    private lateinit var priceFormat: String
    private lateinit var descriptionProduct: String
    private var sold:Int = 0
    private var quantity:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_product_detail)

        categoryTV = category_product
        nameProductTV = name_product
        priceProductTV = price_product
        descriptionProductTV = description_product
        soldTV = sold_product
        quantityTV = quantity_product

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

        btn_add_cart.setOnClickListener {
//            Toast.makeText(mContext, "Add Cart", Toast.LENGTH_SHORT).show()
            addToCart(1,priceProduct,productId,cartId.toInt())

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
                            imageProduct = detailProduct.image
                            productId = detailProduct.productId

                            categoryProduct = detailProduct.category.categoryName
                            nameProduct = detailProduct.name
                            priceProduct = detailProduct.price
                            priceFormat = NumberFormat.getCurrencyInstance(Locale("vie","vn")).format(priceProduct)
                            descriptionProduct = detailProduct.description
                            sold = detailProduct.sold
                            quantity = detailProduct.quantity

                            Glide.with(this@ProductDetailActivity).load(imageProduct).into(image_product)
                            categoryTV.text = categoryProduct
                            nameProductTV.text = nameProduct
                            priceProductTV.text = priceFormat.toString()
                            descriptionProductTV.text = descriptionProduct
                            soldTV.text = "Sold: $sold"
                            quantityTV.text = "Quantity Product: $quantity"

                            // hien thi views
                            image_product.visibility = View.VISIBLE
                            categoryTV.visibility = View.VISIBLE
                            nameProductTV.visibility = View.VISIBLE
                            priceProductTV.visibility = View.VISIBLE
                            descriptionProductTV.visibility = View.VISIBLE
                            soldTV.visibility = View.VISIBLE
                            quantityTV.visibility = View.VISIBLE

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

//                            Toast.makeText(mContext, "Call list product suggest success",Toast.LENGTH_SHORT).show()
                            Log.i("toast","Call list product suggest success")
                        }else{
//                            Toast.makeText(mContext, "Call list product suggest fail",Toast.LENGTH_SHORT).show()
                            Log.i("toast","Call list product suggest fail")
                        }
                    }
                }
                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
//                    Toast.makeText(mContext, "",Toast.LENGTH_SHORT).show()
                    Log.i("toast","Call api product suggest fail")

                }
            })
    }

    //{"quantity":1,"price":198000,"product":{"productId":26},"cart":{"cartId":17}}
    private fun addToCart(quantity: Int, price: Int, productId:Int, cartId: Int) {
        val map = HashMap<String, Any>()

        map["quantity"] = quantity
        map["price"] = price

        val productObj = HashMap<String,Any>()
        productObj["productId"] = productId

        val cartObj = HashMap<String,Any>()
        cartObj["cartId"] = cartId

        map["product"] = productObj
        map["cart"] = cartObj

        RetrofitClient.instance.addProductToCart(map)
            .enqueue(object : Callback<CartItem>{
                override fun onResponse(call: Call<CartItem>, response: Response<CartItem>) {
                    if (response.isSuccessful){
                        Toast.makeText(mContext, "Add To Cart Success!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Add Cart Failed!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CartItem>, t: Throwable) {
                    Toast.makeText(mContext, "Call Api Add Cart Failed!", Toast.LENGTH_SHORT).show()

                }

            })
    }


}
