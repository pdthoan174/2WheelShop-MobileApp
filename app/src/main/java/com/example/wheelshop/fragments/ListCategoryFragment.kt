package com.example.wheelshop.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wheelshop.R
import com.example.wheelshop.adapters.CategoryAdapter
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.Category
import com.example.wheelshop.models.ListCategory
import kotlinx.android.synthetic.main.fragment_list_category.*
import kotlinx.android.synthetic.main.fragment_shop.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListCategoryFragment : Fragment() {
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
        listCategory()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_category, container, false)
    }

    private fun listCategory(){
        RetrofitClient.instance.getListCategory()
            .enqueue(object: Callback<ListCategory> {
                override fun onResponse(
                    call: Call<ListCategory>,
                    response: Response<ListCategory>
                ) {
                    if (response.isSuccessful){
                        val list: ArrayList<Category>? = response.body()

                        //Log.i("hihi",list.toString())
                        if (list != null){
                            //val adapter = ProductAdapter(list,"horizontal",20)
                            val adapter = CategoryAdapter(list)
                            listCategoryRV.adapter = adapter
                            listCategoryRV.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
//                            listCategoryRV.layoutManager = GridLayoutManager(mContext, 3)
                            listCategoryRV.isNestedScrollingEnabled = true
                        }
                        Toast.makeText(mContext, "Call List Product Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call Product Fail",Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ListCategory>, t: Throwable) {
                    Toast.makeText(mContext, "Call List Product Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

}