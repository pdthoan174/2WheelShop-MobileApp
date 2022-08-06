package com.example.encare.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.encare.R
import com.example.encare.models.Data

import kotlinx.android.synthetic.main.list_doctor.view.*


class DoctorAdapter(private val listDoctor: ArrayList<Data>):RecyclerView.Adapter<DoctorAdapter.DoctorAdapterHolder>() {
    private lateinit var mContext: Context


    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapterHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_doctor, parent, false)
        mContext = parent.context
        return DoctorAdapterHolder(view)
    }

    // binding data tu doi tuong len viewHolder
    override fun onBindViewHolder(holder: DoctorAdapterHolder, position: Int) {
        val currentDoctor = listDoctor[position]
        // set du lieu cho cac thanh phan
        //tao 8 doi tuong theo trong list_doctor.xml

        holder.nameDoctor.text = currentDoctor.accountResponse?.name
        holder.addressDoctor.text = currentDoctor.hospitalResponse?.hospitalName


        val avatar = currentDoctor.accountResponse?.avatar
        if (avatar == null){
            holder.imgAvatar.setImageResource(R.drawable.avatar)
        }else{
            Glide.with(mContext).load(avatar).into(holder.imgAvatar)
        }

        when (currentDoctor.rating){
            1 -> {
                holder.imageStart1.setImageResource(R.drawable.ic_star_check)
            }
            2 -> {
                holder.imageStart1.setImageResource(R.drawable.ic_star_check)
                holder.imageStart2.setImageResource(R.drawable.ic_star_check)
            }
            3 -> {
                holder.imageStart1.setImageResource(R.drawable.ic_star_check)
                holder.imageStart2.setImageResource(R.drawable.ic_star_check)
                holder.imageStart3.setImageResource(R.drawable.ic_star_check)
            }
            4 -> {
                holder.imageStart1.setImageResource(R.drawable.ic_star_check)
                holder.imageStart2.setImageResource(R.drawable.ic_star_check)
                holder.imageStart3.setImageResource(R.drawable.ic_star_check)
                holder.imageStart4.setImageResource(R.drawable.ic_star_check)
            }
            5 -> {
                holder.imageStart1.setImageResource(R.drawable.ic_star_check)
                holder.imageStart2.setImageResource(R.drawable.ic_star_check)
                holder.imageStart3.setImageResource(R.drawable.ic_star_check)
                holder.imageStart4.setImageResource(R.drawable.ic_star_check)
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