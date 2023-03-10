package com.example.wheelshop.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import com.example.wheelshop.R
import com.example.wheelshop.activity.MainActivity
import com.example.wheelshop.models.DataProductResponse
import kotlinx.android.synthetic.main.fragment_shop.*


class ShopFragment : Fragment() {
//    private lateinit var list: ArrayList<DataProductResponse>
//    private lateinit var adapter: ProductAdapter

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
        val listCategoryFragment = ListCategoryFragment()
        val listProductFragment = ListProductFragment()

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.list_category_frame, listCategoryFragment).commit()

        val transaction1 = childFragmentManager.beginTransaction()

        transaction1.replace(R.id.list_product_frame, listProductFragment).commit()


        return mView
    }



    override fun onResume() {
        super.onResume()
        Log.i("resume","resume")

    }

//    private fun listProduct(){
//        RetrofitClient.instance.getAllProduct()
//            .enqueue(object: Callback<DataProduct>{
//                override fun onResponse(
//                    call: Call<DataProduct>,
//                    response: Response<DataProduct>
//                ) {
//                    if (response.isSuccessful){
//                        list = response.body()!!
//
//                        //Log.i("hihi",list.toString())
//                        if (list != null){
//                            //val adapter = ProductAdapter(list,"horizontal",20)
//                            adapter = ProductAdapter(list,"vertical",list.size)
//
//                            listAllProduct.adapter = adapter
//                            //listAllProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
//                            listAllProduct.layoutManager = GridLayoutManager(mContext, 3)
//                            adapter.notifyDataSetChanged()
//
//                        }
//                        Toast.makeText(mContext, "Call List Product Success",Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(mContext, "Call Product Fail",Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//
//                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
//                    Toast.makeText(mContext, "Call List Product Fail",Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//
//    public fun listProduct(categoryId: Int){
//        RetrofitClient.instance.getListProductById(categoryId)
//            .enqueue(object: Callback<DataProduct> {
//                override fun onResponse(
//                    call: Call<DataProduct>,
//                    response: Response<DataProduct>
//                ) {
//                    if (response.isSuccessful){
//                        list = response.body()!!
//
//                        //Log.i("hihi",list.toString())
//                        if (list != null){
//                            //val adapter = ProductAdapter(list,"horizontal",20)
//                            adapter = ProductAdapter(list,"vertical",list.size)
//                            listAllProduct.adapter = adapter
//                            //listAllProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
//                            listAllProduct.layoutManager = GridLayoutManager(mContext, 3)
//
//                            adapter.notifyDataSetChanged()
////                            listAllProduct.isNestedScrollingEnabled = true
//                        }
//                        Toast.makeText(mContext, "Call List Product Success", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(mContext, "Call Product Fail", Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//
//                override fun onFailure(call: Call<DataProduct>, t: Throwable) {
//                    Toast.makeText(mContext, "Call List Product Fail", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }




    internal interface RefreshInterface {
        fun refreshAdapterFragmentB()
    }

}