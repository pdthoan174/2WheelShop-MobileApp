package com.example.wheelshop.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wheelshop.R
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.fragments.ListProductFragment
import com.example.wheelshop.fragments.ShopFragment
import com.example.wheelshop.models.Category
import com.example.wheelshop.models.DataProduct
import com.example.wheelshop.models.DataProductResponse
import com.example.wheelshop.models.ListCategory
import kotlinx.android.synthetic.main.fragment_shop.*
import kotlinx.android.synthetic.main.fragment_shop.view.*
import kotlinx.android.synthetic.main.item_category.view.*

import kotlinx.android.synthetic.main.list_product_1.view.*
import kotlinx.android.synthetic.main.list_product_1.view.categoryProduct
import kotlinx.android.synthetic.main.list_product_1.view.itemProduct
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class CategoryAdapter(
    private val listCategory: ArrayList<Category>,
):RecyclerView.Adapter<CategoryAdapter.CategoryAdapterHolder>(){
    private lateinit var mContext: Context
    private lateinit var view1: View

    private var limitCharacter = 27

    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterHolder {
        mContext = parent.context
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        view1 = LayoutInflater.from(parent.context).inflate(R.layout.fragment_shop, parent, false)
        return CategoryAdapterHolder(view)

    }

    // binding data tu doi tuong len viewHolder
    override fun onBindViewHolder(holder: CategoryAdapterHolder, position: Int) {
        val currentCategory = listCategory[position]

        holder.category.text = currentCategory.categoryName

        holder.category.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v?.context as AppCompatActivity
//                if (idCategory != null){
                    val listProductFragment = ListProductFragment()

                    // add data vao bundle de gui sang fragment khac
                    val bundle = Bundle()
                    bundle.putInt("idCategory", currentCategory.categoryId)
                    listProductFragment.arguments = bundle

                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.list_product_frame, listProductFragment)

                        .commit()
                    notifyDataSetChanged()
                }

//                Toast.makeText(mContext, currentCategory.categoryId.toString(), Toast.LENGTH_SHORT).show()

//            }

        })


    }

    // return the item count of recyclerview
    override fun getItemCount(): Int {
        return listCategory.size
    }

    inner class CategoryAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        val itemProduct:RelativeLayout = view.itemCategory
        var category: TextView = view.name_category

    }


//    private fun listProductById(categoryId: Int){
//        RetrofitClient.instance.getListProductById(categoryId)
//            .enqueue(object: Callback<DataProduct> {
//                override fun onResponse(
//                    call: Call<DataProduct>,
//                    response: Response<DataProduct>
//                ) {
//                    if (response.isSuccessful){
//                        val list: ArrayList<DataProductResponse>? = response.body()
//
//
//
//                        //Log.i("hihi",list.toString())
//                        if (list != null){
//                            //val adapter = ProductAdapter(list,"horizontal",20)
//                            val adapter = ProductAdapter(list,"vertical",list.size)
//                            view1.listAllProduct.adapter = adapter
//                            //listAllProduct.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
//                            view1.listAllProduct.layoutManager = GridLayoutManager(mContext, 3)
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

}