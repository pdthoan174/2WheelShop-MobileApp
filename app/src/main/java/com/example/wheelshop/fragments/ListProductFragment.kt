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
import com.example.wheelshop.adapters.ProductAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.DataProduct
import com.example.wheelshop.models.DataProductResponse
import kotlinx.android.synthetic.main.fragment_list_product.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ListProductFragment : Fragment() {
    private lateinit var list: ArrayList<DataProductResponse>

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle: Bundle? = arguments
        val categoryId =  bundle?.getInt("idCategory")

        if (categoryId != null){
            listProduct(categoryId)
        }else{
            listProduct()
            Toast.makeText(mContext, "Call Product", Toast.LENGTH_SHORT).show()
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_product, container, false)
    }

    private fun filterList(newText: String?) {
        if (newText != null){
            val filterList = ArrayList<DataProductResponse>()
            for (i in list){
                if (i.name.toLowerCase(Locale.ROOT).contains(newText)){
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()){
                Toast.makeText(mContext, "Không có sản phẩm", Toast.LENGTH_SHORT).show()
            }else{
                val adapter: ProductAdapter = ProductAdapter(list,"vertical",list.size)
                adapter.setFilterList(filterList)
            }
        }
    }

    private fun listProduct(){
        RetrofitClient.instance.getAllProduct()
            .enqueue(object: Callback<DataProduct> {
                override fun onResponse(
                    call: Call<DataProduct>,
                    response: Response<DataProduct>
                ) {
                    if (response.isSuccessful){
                        list = response.body()!!

                        if (list != null){

                            val adapter = ProductAdapter(list,"vertical",list.size)

                            listAllProduct.adapter = adapter
                            //listAllProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                            listAllProduct.layoutManager = GridLayoutManager(mContext, 3)


                        }
                        Toast.makeText(mContext, "Call List Product Success", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call Product Fail", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
                    Toast.makeText(mContext, "Call List Product Fail", Toast.LENGTH_SHORT).show()
                }
            })
    }



    public fun listProduct(categoryId: Int){
        RetrofitClient.instance.getListProductById(categoryId)
            .enqueue(object: Callback<DataProduct> {
                override fun onResponse(
                    call: Call<DataProduct>,
                    response: Response<DataProduct>
                ) {
                    if (response.isSuccessful){
                        list = response.body()!!

                        //Log.i("hihi",list.toString())
                        //val adapter = ProductAdapter(list,"horizontal",20)
                        val adapter = ProductAdapter(list,"vertical",list.size)
                        listAllProduct.adapter = adapter
                        //listAllProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                        listAllProduct.layoutManager = GridLayoutManager(mContext, 3)
                        listAllProduct.removeAllViewsInLayout()
//                            listAllProduct.isNestedScrollingEnabled = true
                        Toast.makeText(mContext, "Call List Product Success", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call Product Fail", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
                    Toast.makeText(mContext, "Call List Product Fail", Toast.LENGTH_SHORT).show()
                }
            })
    }

}