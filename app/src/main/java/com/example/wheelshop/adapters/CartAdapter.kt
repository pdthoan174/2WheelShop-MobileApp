package com.example.wheelshop.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wheelshop.R
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.models.*
import kotlinx.android.synthetic.main.cart_product.view.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.HashMap


class CartAdapter(
    private val listProduct: ArrayList<CartItem>,
    private val totalText: TextView

):RecyclerView.Adapter<CartAdapter.CartAdapterHolder>() {
    private lateinit var mContext: Context
    private var limitCharacter = 20

    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapterHolder {
        mContext = parent.context
        limitCharacter = 80
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.cart_product, parent, false)
        updateTotalCart()
        return CartAdapterHolder(view)
    }

    // binding data tu doi tuong len viewHolder
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CartAdapterHolder, position: Int) {
        val currentProduct = listProduct[position]
        // set du lieu cho cac thanh phan
        //tao 8 doi tuong theo trong list_doctor.xml

        val cartDetailId = currentProduct.cartDetailId
        val productId = currentProduct.product.productId
        val cartId = currentProduct.cart.cartId

        var nameProduct = currentProduct.product.name
        val price = currentProduct.price

        //giá của 1 sản phẩm
        val priceAProduct = currentProduct.product.price
        val priceFormat = formatCurrency(price)
        val image = currentProduct.product.image
        var num =  currentProduct.quantity

        // sản phẩm trong kho
        val quantityProduct = currentProduct.product.quantity

        var quantity = num

        if (nameProduct != null) {
            if (nameProduct.length >= limitCharacter){
                nameProduct = nameProduct.substring(0, limitCharacter)+"..."
            }
        }

        holder.price.text = priceFormat
        holder.nameProduct.text = nameProduct
        holder.numProduct.text = num.toString()
        holder.itemProduct.setOnClickListener{
            val cartDetailId = currentProduct.cartDetailId
            Toast.makeText(mContext, "Click Product $cartDetailId",Toast.LENGTH_SHORT).show()
        }
        if (image == null){
            holder.imgProduct.setImageResource(R.drawable.avatars)
        }else{
            Glide.with(mContext).load(image).into(holder.imgProduct)
        }

        holder.btnPlus.setOnClickListener {
            quantity = num + 1
            if(quantity<=  quantityProduct){
                val total = priceAProduct * quantity
                currentProduct.price = total
                currentProduct.quantity = quantity

                holder.price.text = formatCurrency(total)
                holder.numProduct.text = quantity.toString()
                num = quantity

                updateCart(cartDetailId, quantity, total, productId, cartId)
                updateTotalCart()

            }else{
                Toast.makeText(mContext, "Vượt quá số sản phẩm trong kho", Toast.LENGTH_SHORT).show()
            }

        }

        holder.btnMinus.setOnClickListener {
            when(quantity){
                1 -> Toast.makeText(mContext, "Sản phẩm phải lớn hơn 0", Toast.LENGTH_SHORT).show()
                else -> {
                    quantity = num - 1
                    val total = priceAProduct * quantity
                    // update price in list
                    currentProduct.price = total
                    currentProduct.quantity = quantity
                    holder.price.text = formatCurrency(total)
                    holder.numProduct.text = quantity.toString()
                    num = quantity

                    updateCart(cartDetailId, quantity, total, productId, cartId)
                    updateTotalCart()
                }
            }

        }

        holder.btnDelete.setOnClickListener {
            if (deleteProductInCart(cartDetailId)){
                listProduct.removeAt(position)
                notifyDataSetChanged()
                updateTotalCart()
                Toast.makeText(mContext,"Xóa thành công", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(mContext,"Thử lại", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // return the item count of recyclerview
    override fun getItemCount(): Int {
//        return listProduct.size
        return listProduct.size
    }

    class CartAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        val itemProduct:ConstraintLayout = view.itemProduct
        var imgProduct: ImageView = view.image_product
        var nameProduct: TextView = view.nameProduct
        var price: TextView = view.price_text
        var numProduct: TextView = view.quantity
        val btnPlus: ImageView = view.btn_plus
        val btnMinus: ImageView = view.btn_minus
        val btnDelete: ImageView = view.btn_delete
    }

    private fun formatCurrency(price: Int): String{
        return NumberFormat.getCurrencyInstance(Locale("vie","vn")).format(price)
    }

    private fun updateTotalCart(){
        var total = 0
        for (item in listProduct){
            total += item.product.price * item.quantity
        }

        totalText.text = formatCurrency(total)
    }

    private fun updateCart(cartDetailId: Int, quantity:Int, price:Int, productId:Int, cartId: Int){
        val map = HashMap<String, Any>()
        map["cartDetailId"] = cartDetailId
        map["quantity"] = quantity
        map["price"] = price

        val productObj = HashMap<String, Any>()
        productObj["productId"] = productId

        val cartObj = HashMap<String, Any>()
        cartObj["cartId"] = cartId

        map["product"] = productObj
        map["cart"] = cartObj

        RetrofitClient.instance.updateItemInCart(map)
            .enqueue(object: Callback<CartItem> {
                override fun onResponse(
                    call: Call<CartItem>,
                    response: Response<CartItem>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(mContext, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Không thể cập nhật. Thử lại", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<CartItem>, t: Throwable) {
                    Toast.makeText(mContext, "request fail", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun deleteProductInCart(id: Int): Boolean {
        var statusRequest: Boolean = true

        RetrofitClient.instance.deleteProductInCart(id)
            .enqueue(object : Callback<Any>{
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    statusRequest = response.isSuccessful

                    Log.i("del", statusRequest.toString())
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.i("del", "call api delete error")
                }

            })

        return statusRequest
    }


}