package com.example.encare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.encare.R
import com.example.encare.models.Data


import kotlinx.android.synthetic.main.list_doctor.view.*
import kotlinx.coroutines.NonDisposableHandle.parent

class DoctorAdapter(private val listDoctor: ArrayList<Data>):RecyclerView.Adapter<DoctorAdapter.DoctorAdapterHolder>() {
    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapterHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_doctor, parent, false)
        return DoctorAdapterHolder(view)
    }

    // binding data tu doi tuong len viewHolder
    override fun onBindViewHolder(holder: DoctorAdapterHolder, position: Int) {
        val doctor = listDoctor.get(position)
        // set du lieu cho cac thanh phan
        //tao 8 doi tuong theo trong list_doctor.xml
        holder.nameDoctor.text = doctor.accountResponse?.name
        holder.addressDoctor.text = doctor.hospitalResponse?.hospitalName

//        Glide.with().load(doctor.accountResponse?.avatar).into(holder.imgAvatar)

        when (doctor.rating){
            1 -> {
                holder.imageStart1.setImageResource(R.drawable.ic_star_check)
            }
            2 -> {
                holder.imageStart2.setImageResource(R.drawable.ic_star_check)
                holder.imageStart2.setImageResource(R.drawable.ic_star_check)
            }
            3 -> {
                holder.imageStart3.setImageResource(R.drawable.ic_star_check)
                holder.imageStart3.setImageResource(R.drawable.ic_star_check)
                holder.imageStart3.setImageResource(R.drawable.ic_star_check)
            }
            4 -> {
                holder.imageStart4.setImageResource(R.drawable.ic_star_check)
                holder.imageStart4.setImageResource(R.drawable.ic_star_check)
                holder.imageStart4.setImageResource(R.drawable.ic_star_check)
                holder.imageStart4.setImageResource(R.drawable.ic_star_check)
            }
            5 -> {
                holder.imageStart5.setImageResource(R.drawable.ic_star_check)
                holder.imageStart5.setImageResource(R.drawable.ic_star_check)
                holder.imageStart5.setImageResource(R.drawable.ic_star_check)
                holder.imageStart5.setImageResource(R.drawable.ic_star_check)
                holder.imageStart5.setImageResource(R.drawable.ic_star_check)
            }else -> {
                holder.imageStart5.setImageResource(R.drawable.ic_star_check)
                holder.imageStart5.setImageResource(R.drawable.ic_star_check)
            }
        }
    }

    // return the item count of recyclerview
    override fun getItemCount(): Int {
        return listDoctor.size
    }

    class DoctorAdapterHolder(view: View) : RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        var imgAvatar: ImageView = view.image_doctor_fav
        var nameDoctor: TextView = view.nameDoctor_fav
        var addressDoctor: TextView = view.address_fav

        var imageStart1:ImageView = view.imgStar1
        var imageStart2:ImageView = view.imgStar2
        var imageStart3:ImageView = view.imgStar3
        var imageStart4:ImageView = view.imgStar4
        var imageStart5:ImageView = view.imgStar5

    }


}