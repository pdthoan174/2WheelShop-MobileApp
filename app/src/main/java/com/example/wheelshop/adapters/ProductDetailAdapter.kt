package com.example.wheelshop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.wheelshop.R

import com.example.wheelshop.models.DataProductResponse
import kotlinx.android.synthetic.main.fragment_product_detail.view.*

class ProductDetailAdapter(
    private val detailProduct: DataProductResponse,
):RecyclerView.Adapter<ProductDetailAdapter.ProductDetailAdapterHolder>() {
    private lateinit var mContext: Context
    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailAdapterHolder {
        mContext = parent.context
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_product_detail, parent, false)
        return ProductDetailAdapterHolder(view)
    }

    // binding data tu doi tuong len viewHolder
    override fun onBindViewHolder(holder: ProductDetailAdapterHolder, position: Int) {

        // gioi han ki tu hien thi addressDoctor
        val idProduct = detailProduct.productId
        val image = detailProduct.image
        val category =  detailProduct.category.categoryName
        val nameProduct = detailProduct.name
        val price = detailProduct.price
        val description = detailProduct.description
        val sold = detailProduct.sold
        val quantity = detailProduct.quantity


        holder.nameProduct.text = nameProduct
        holder.category.text = category
        holder.price.text = price.toString()
        holder.descriptionProduct.text = description
        holder.sold.text = sold.toString()
        holder.quantity.text = quantity.toString()
        //end

        if (image == null){
            holder.imgProduct.setImageResource(R.drawable.avatar)
        }else{
            Glide.with(mContext).load(image).into(holder.imgProduct)
        }

    }

    // return the item count of recyclerview
    override fun getItemCount(): Int {
//        return listProduct.size
        return 1
    }

    inner class ProductDetailAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        var imgProduct: ImageView = view.image_product
        var nameProduct: TextView = view.name_product
        var price: TextView = view.price_product
        var category: TextView = view.category_product
        val descriptionProduct: TextView = view.description_product
        val sold:TextView = view.sold_product
        val quantity:TextView = view.quantity_product
    }

}