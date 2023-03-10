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
import com.example.wheelshop.models.OrderItem
import kotlinx.android.synthetic.main.item_detail_order.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailOrderAdapter(
    private val listOrderDetail: ArrayList<OrderItem>
):RecyclerView.Adapter<DetailOrderAdapter.DetailOrderAdapter>() {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailOrderAdapter {
        mContext = parent.context

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_order, parent, false)
        return DetailOrderAdapter(view)
    }

    override fun onBindViewHolder(holder: DetailOrderAdapter, position: Int) {
        val itemOrder = listOrderDetail[position]
        holder.name.text = itemOrder.product.name
        holder.price.text = formatCurrency(itemOrder.price.toInt())

        if (itemOrder.product.image == null){
            holder.image.setImageResource(R.drawable.avatars)
        }else{
            Glide.with(mContext).load(itemOrder.product.image).into(holder.image)
        }
        holder.quantity.text = itemOrder.quantity.toString()
    }

    override fun getItemCount(): Int {
        return listOrderDetail.size
    }

    inner class DetailOrderAdapter(view: View) : RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        val name: TextView = view.nameProduct
        var image: ImageView = view.image_product
        var price: TextView = view.price_text
        var quantity: TextView = view.quantity

    }

    private fun formatCurrency(price: Int): String{
        return NumberFormat.getCurrencyInstance(Locale("vie","vn")).format(price)
    }
}