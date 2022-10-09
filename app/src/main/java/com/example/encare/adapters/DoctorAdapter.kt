package com.example.encare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.encare.R

import com.example.encare.models.DataDoctorResponse

import kotlinx.android.synthetic.main.list_doctor_1.view.*

class DoctorAdapter(private val listDoctor: ArrayList<DataDoctorResponse>, private val layout:String):RecyclerView.Adapter<DoctorAdapter.DoctorAdapterHolder>() {
    private lateinit var mContext: Context
    // gioi han ki tu hien thi nameHospital va addressDoctor
    private var limitCharacter = 20

    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorAdapterHolder {
        mContext = parent.context
        if (layout == "vertical"){
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_doctor_1, parent, false)
            return DoctorAdapterHolder(view)
        }else{
            limitCharacter = 38
            val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_doctor_2, parent, false)
            return DoctorAdapterHolder(view)
        }

    }

    // binding data tu doi tuong len viewHolder
    override fun onBindViewHolder(holder: DoctorAdapterHolder, position: Int) {
        val currentDoctor = listDoctor[position]
        // set du lieu cho cac thanh phan
        //tao 8 doi tuong theo trong list_doctor.xml
        holder.nameDoctor.text = currentDoctor.accountResponse?.name

        // gioi han ki tu hien thi addressDoctor
        var addressDoctor = currentDoctor.hospitalResponse?.hospitalAddress
        var nameHospital = currentDoctor.hospitalResponse?.hospitalName

        if (addressDoctor != null) {
            if (addressDoctor.length >= limitCharacter){
                addressDoctor = addressDoctor.substring(0, limitCharacter)+"..."
            }
        }
        if (nameHospital != null) {
            if (nameHospital.length >= limitCharacter){
                nameHospital = nameHospital.substring(0, limitCharacter)+"..."
            }
        }

        holder.addressDoctor.text = addressDoctor
        holder.nameHospital.text = nameHospital
        //end

        holder.itemDoctor.setOnClickListener{
            val nameDoctor = currentDoctor.accountResponse?.name
            Toast.makeText(mContext, "Click Doctor $nameDoctor",Toast.LENGTH_SHORT).show()

        }

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
        val itemDoctor:ConstraintLayout = view.itemDoctor

        var imgAvatar: ImageView = view.image_doctor
        var nameDoctor: TextView = view.nameDoctor
        var addressDoctor: TextView = view.addressHospital

        var nameHospital: TextView = view.nameHospital
        var imageStart1:ImageView = view.imgStar1
        var imageStart2:ImageView = view.imgStar2
        var imageStart3:ImageView = view.imgStar3
        var imageStart4:ImageView = view.imgStar4
        var imageStart5:ImageView = view.imgStar5

    }


}