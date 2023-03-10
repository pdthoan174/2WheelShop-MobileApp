package com.example.wheelshop.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.adapters.ProductAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.*
import com.example.wheelshop.myInterface.ProductClickHandler
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeFragment : Fragment(), ProductClickHandler {

    private  var token: String = ""
    private  var idUser: String = ""
    private var TAG = "TAG_FRAGMENT"

    private lateinit var mContext:Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getToken()
        getProfile(idUser.toInt())
        listProductRate()
        listProductBestSeller()
        listNewProduct()
        Toast.makeText(mContext, idUser, Toast.LENGTH_SHORT).show()
        Log.i("hihi",idUser)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.itemCategory4.setOnClickListener {
            //showCategoryFragment()
        }
        return view
    }

//    private fun showCategoryFragment() {
//        val fragment = CategoryFragment()
//        val transaction = fragmentManager?.beginTransaction()
//        transaction?.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
//        transaction?.replace(R.id.FrameLayoutHome, fragment, TAG)
//        // them fragment vao stack
//        transaction?.addToBackStack(null)
//        transaction?.commit()
//    }

    private fun getToken(){
        token = SharedPreferencesOptimal.get("TOKEN", String::class.java)
        idUser = SharedPreferencesOptimal.get("USERID", String::class.java)
    }

    private fun getProfile(id: Int){
        RetrofitClient.instance.getProfile(id)
            .enqueue(object: Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    // description: trong RegisterResponse.kt
                    val infoProfile = response.body()
                    //Log.i("hihi",infoProfile.toString())
                    if (response.isSuccessful){
                        if (textName!=null){
                            textName.text = infoProfile?.name
                        }
                        val avatar = infoProfile?.image

                        if (avatar == null){
                            avt1.setImageResource(R.drawable.avatars)
                        }else{
                            // load using Glide
                            Glide.with(mContext)
                                .load(avatar)
                                .error(R.drawable.avatars)
                                .into(avt1)
                        }
                    }else{
                        Toast.makeText(mContext, "Get profile Fail", Toast.LENGTH_SHORT).show()
//                        Toast.makeText(applicationContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Toast.makeText(mContext, "Call api error", Toast.LENGTH_SHORT).show()
                }
            })
    }
    // get list sản phẩm thịnhj hành
    private fun listProductRate(){
        RetrofitClient.instance.getlistProductRate()
            .enqueue(object: Callback<DataProduct>{
                override fun onResponse(
                    call: Call<DataProduct>,
                    response: Response<DataProduct>
                ) {
                    if (response.isSuccessful){
                        val list: ArrayList<DataProductResponse>? = response.body()
//                        Log.i("hihi",list.toString())
                        if (list != null){
                            val adapter = ProductAdapter(list,"vertical",9)
                            if (list_product_rate!= null){
                                list_product_rate.adapter = adapter
                                list_product_rate.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                            }
                        }
                        Toast.makeText(mContext, "Call List Product Rate Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call Product Rate Fail",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
                    Toast.makeText(mContext, "Call API List Product Rate Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

    // get list sản phẩm bán chạy
    private fun listProductBestSeller(){
        RetrofitClient.instance.getlistProductBestSeller()
            .enqueue(object: Callback<DataProduct>{
                override fun onResponse(
                    call: Call<DataProduct>,
                    response: Response<DataProduct>
                ) {
                    if (response.isSuccessful){
                        val list: ArrayList<DataProductResponse>? = response.body()
                        //Log.i("hihi",list.toString())
                        if (list != null){
                            val adapter = ProductAdapter(list,"vertical",10)
                            list_product_best_seller.adapter = adapter
                            list_product_best_seller.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                        }
                        Toast.makeText(mContext, "Call List Best Seller Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call List Best Seller Fail",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
                    Toast.makeText(mContext, "Call List Best Seller Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }
    // get list sản phẩm mới
    private fun listNewProduct(){
        RetrofitClient.instance.getlistNewProduct()
            .enqueue(object: Callback<DataProduct>{
                override fun onResponse(
                    call: Call<DataProduct>,
                    response: Response<DataProduct>
                ) {
                    if (response.isSuccessful){
                        val list: ArrayList<DataProductResponse>? = response.body()
                        //Log.i("hihi",list.toString())
                        if (list != null){
                            val adapter = ProductAdapter(list,"vertical",10)
                            list_new_product.adapter = adapter
                            list_new_product.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                        }
                        Toast.makeText(mContext, "Call List New Product Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call List New Product Fail",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
                    Toast.makeText(mContext, "Call List New Product Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun clickedProductItem(idProduct: Int) {
        TODO("Not yet implemented")
    }

}