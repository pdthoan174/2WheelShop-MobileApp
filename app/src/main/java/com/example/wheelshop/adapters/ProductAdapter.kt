package com.example.wheelshop.adapters

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheelshop.R
import com.example.wheelshop.activity.ProductDetailActivity

import com.example.wheelshop.models.DataProductResponse
import com.example.wheelshop.myInterface.ProductClickHandler
import kotlinx.android.synthetic.main.list_product_1.view.*
import kotlinx.android.synthetic.main.list_product_1.view.categoryProduct
import kotlinx.android.synthetic.main.list_product_1.view.image_product
import kotlinx.android.synthetic.main.list_product_1.view.itemProduct
import kotlinx.android.synthetic.main.list_product_1.view.nameProduct
import kotlinx.android.synthetic.main.list_product_1.view.price
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class ProductAdapter(
    private val listProduct: ArrayList<DataProductResponse>,
    private val layout:String,
    private val numProduct:Int,
):RecyclerView.Adapter<ProductAdapter.ProductAdapterHolder>(), ProductClickHandler {
    private lateinit var mContext: Context
    // gioi han ki tu hien thi nameHospital va addressDoctor
    private var limitCharacter = 27

    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapterHolder {
        mContext = parent.context
        if (layout == "vertical"){
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_product_1, parent, false)
            return ProductAdapterHolder(view)
        }else{
            limitCharacter = 38
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_product_2, parent, false)
            return ProductAdapterHolder(view)
        }
    }

    // binding data tu doi tuong len viewHolder
    override fun onBindViewHolder(holder: ProductAdapterHolder, position: Int) {
        val currentProduct = listProduct[position]
        // set du lieu cho cac thanh phan
        //tao 8 doi tuong theo trong list_doctor.xml
        // gioi han ki tu hien thi addressDoctor
        val idProduct = currentProduct.productId
        val idCategory = currentProduct.category.categoryId
        var nameProduct = currentProduct.name
        var price = currentProduct.price
        var priceFormat = NumberFormat.getCurrencyInstance(Locale("vie","vn")).format(price)
        val image = currentProduct.image
        var category =  currentProduct.category.categoryName

        if (nameProduct != null) {
            if (nameProduct.length >= limitCharacter){
                nameProduct = nameProduct.substring(0, limitCharacter)+"..."
            }
        }

        holder.price.text = priceFormat
        holder.nameProduct.text = nameProduct
        holder.category.text = category
        //end
        holder.itemProduct.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v?.context as AppCompatActivity

                val intent = Intent(activity, ProductDetailActivity::class.java)
                intent.putExtra("idProduct",idProduct)
                intent.putExtra("idCategory",idCategory)

                //start activity with animation
                val options: ActivityOptions = ActivityOptions.makeCustomAnimation(mContext,R.anim.slide_in_right, R.anim.slide_out_left)
                mContext.startActivity(intent, options.toBundle())

//                val detailProductFragment = DetailProductFragment()
//                // tao bundle de gui du lieu sang fragment khac
//                val bundle = Bundle()
//                bundle.putInt("idProduct",idProduct)
//                detailProductFragment.arguments = bundle
//
//                activity.supportFragmentManager.beginTransaction()
//                    .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
//                    .add(R.id.ConstraintLayoutShop, detailProductFragment)
//                    .addToBackStack(null)
//                    .commit()
            }

        })

        holder.add.setOnClickListener {
            Toast.makeText(mContext, idProduct.toString(),Toast.LENGTH_SHORT).show()
        }

        if (image == null){
            holder.imgProduct.setImageResource(R.drawable.avatar)
        }else{
            Glide.with(mContext).load(image).into(holder.imgProduct)
        }

    }

    // return the item count of recyclerview
    override fun getItemCount(): Int {
//        return listProduct.size
        return numProduct
    }

    inner class ProductAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        val itemProduct:ConstraintLayout = view.itemProduct
        val add: Button = view.add
        var imgProduct: ImageView = view.image_product
        var nameProduct: TextView = view.nameProduct
        var price: TextView = view.price
        var category: TextView = view.categoryProduct

    }

    override fun clickedProductItem(idProduct: Int) {
        val bundle = Bundle()
        bundle.putInt("idProduct", idProduct)
    }


}