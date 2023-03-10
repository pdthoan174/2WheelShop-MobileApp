package com.example.wheelshop.adapters

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.wheelshop.DataLocal.SharedPreferencesOptimal
import com.example.wheelshop.R
import com.example.wheelshop.activity.DetailOrderActivity
import com.example.wheelshop.activity.ProductDetailActivity
import com.example.wheelshop.api.RetrofitClient
import com.example.wheelshop.fragments.ProfileFragment

import com.example.wheelshop.models.OrderResponse
import kotlinx.android.synthetic.main.item_order.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class OrderAdapter(
    private val listOrder: ArrayList<OrderResponse>,
    private val numProduct:Int,
):RecyclerView.Adapter<OrderAdapter.OrderAdapterHolder>() {
    private lateinit var mContext: Context
    private var email = SharedPreferencesOptimal.get("EMAIL", String::class.java)
    var index = 1

    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapterHolder {
        mContext = parent.context

        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderAdapterHolder(view)
    }

    // binding data tu doi tuong len viewHolder
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OrderAdapterHolder, position: Int) {
        val currentOrder = listOrder[position]
        // set du lieu cho cac thanh phan
        //tao 8 doi tuong theo trong list_doctor.xml
        val ordersId = currentOrder.ordersId

        val datetime = currentOrder.orderDate
        val dateFormat = getDateFromString(datetime)

        val total = currentOrder.amount.toInt()
        val totalFormat = formatCurrency(total)

        val address = currentOrder.address
        val status = currentOrder.status

        val detailStatus = when (status) {
            0-> "Chờ xác nhận"
            1-> "Đã xác nhận"
            2-> "Đã thanh toán"
            else -> "Đã Hủy"
        }

        holder.dateOrder.text = dateFormat
        holder.total.text = totalFormat
        holder.address.text = address
        holder.status.text = detailStatus
        holder.index.text = index.toString()
        index ++

        //end
        holder.itemOrder.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v?.context as AppCompatActivity

                val intent = Intent(activity, DetailOrderActivity::class.java)
                intent.putExtra("ordersId",ordersId)
                //start activity with animation
                val options: ActivityOptions = ActivityOptions.makeCustomAnimation(mContext,R.anim.slide_in_right, R.anim.slide_out_left)
                mContext.startActivity(intent, options.toBundle())

            }
        })

        holder.del.setOnClickListener {
            if(status == 0){
                if (cancelOrder(ordersId)){
//                listOrder.removeAt(position)
//                notifyDataSetChanged()

                    listOrder[position].status = 3
                    notifyDataSetChanged()

                    Toast.makeText(mContext,"Đã Hủy", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(mContext,"Không thể hủy. Thử lại", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(mContext,"Không thể hủy.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // return the item count of recyclerview
    override fun getItemCount(): Int {
//        return listProduct.size
        return numProduct
    }

    inner class OrderAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        val itemOrder:LinearLayout = view.itemOrder
        var index: TextView = view.index
        val del: ImageView = view.btn_delete
        var dateOrder: TextView = view.date
        var total: TextView = view.total
        var address: TextView = view.address
        val status: TextView = view.status

    }

    private fun formatCurrency(price: Int): String{
        return NumberFormat.getCurrencyInstance(Locale("vie","vn")).format(price)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateFromString(datetime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val localDate = LocalDate.parse(datetime, formatter)
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return localDate.format(dateFormatter)
    }

    private fun cancelOrder(orderId: Int): Boolean {
        var statusRequest = true
        RetrofitClient.instance.cancelOrder(orderId)
            .enqueue(object : Callback<Any>{
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    statusRequest = response.isSuccessful
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {

                }

            })
        return statusRequest
    }



}



