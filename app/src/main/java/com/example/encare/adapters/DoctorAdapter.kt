package com.example.encare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.encare.R
import com.example.encare.models.Doctor
import kotlinx.android.synthetic.main.list_doctor.view.*

class DoctorAdapter:RecyclerView.Adapter< RecyclerView.ViewHolder>() {

    private var listDoctor:List<Doctor> = ArrayList()

    public fun setListDoctor(listDoctor:List<Doctor>){

        this.listDoctor = listDoctor
    }

    // mỗi lầ dổ dữ liệu lên thì nó sẽ sử dụng layout nào để binding data
    // binding vào viewHolder: list_doctor.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_doctor, parent, false)
        return DoctorAdapterHolder(view)
    }

    // binding data tu doi tuong len viewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val doctor = listDoctor.get(position)
        // set du lieu cho cac thanh phan
        if(holder is DoctorAdapterHolder){
            holder.bindingData(doctor)
        }
    }

    // return the item count of recyclerview
    override fun getItemCount(): Int {
        return listDoctor.size
    }

    public class DoctorAdapterHolder(view: View): RecyclerView.ViewHolder(view) {
        // tao 8 doi tuong theo trong list_doctor.xml
        private val imgAvatar: ImageView = view.image_doctor_fav
        private val nameDoctor: TextView = view.nameDoctor_fav
        private val addressDoctor: TextView = view.address_fav

        private val imageStart1:ImageView = view.imgStar1
        private val imageStart2:ImageView = view.imgStar2
        private val imageStart3:ImageView = view.imgStar3
        private val imageStart4:ImageView = view.imgStar4
        private val imageStart5:ImageView = view.imgStar5

        fun bindingData(doctor:Doctor){
//            imgAvatar.setImageResource(1)
            val accountResponse = doctor.accountResponse
            if (accountResponse != null){
                nameDoctor.text = accountResponse.name
                addressDoctor.text = doctor.hospitalResponse?.hospitalName

                when (doctor.rating){
                    1 -> {
                        imageStart1.setImageResource(R.drawable.ic_star_check)
                    }
                    2 -> {
                        imageStart2.setImageResource(R.drawable.ic_star_check)
                        imageStart2.setImageResource(R.drawable.ic_star_check)
                    }
                    3 -> {
                        imageStart3.setImageResource(R.drawable.ic_star_check)
                        imageStart3.setImageResource(R.drawable.ic_star_check)
                        imageStart3.setImageResource(R.drawable.ic_star_check)
                    }
                    4 -> {
                        imageStart4.setImageResource(R.drawable.ic_star_check)
                        imageStart4.setImageResource(R.drawable.ic_star_check)
                        imageStart4.setImageResource(R.drawable.ic_star_check)
                        imageStart4.setImageResource(R.drawable.ic_star_check)
                    }
                    5 -> {
                        imageStart5.setImageResource(R.drawable.ic_star_check)
                        imageStart5.setImageResource(R.drawable.ic_star_check)
                        imageStart5.setImageResource(R.drawable.ic_star_check)
                        imageStart5.setImageResource(R.drawable.ic_star_check)
                        imageStart5.setImageResource(R.drawable.ic_star_check)
                    }
                }
            }

        }

    }
}