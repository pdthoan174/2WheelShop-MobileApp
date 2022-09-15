package com.example.encare.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.encare.R
import com.example.encare.adapters.DoctorAdapter
import com.example.encare.api.RetrofitClient
import com.example.encare.models.DataCategoryResponse
import com.example.encare.models.DataDoctor
import com.example.encare.models.DataDoctorResponse
import kotlinx.android.synthetic.main.fragment_doctor.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorFragment() : Fragment() {
    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // nhan data tu bundle
        val bundle: Bundle? = arguments
        val idCategoryFromBundle =  bundle?.getInt("idCategory")
        if (idCategoryFromBundle != null){
            getListDoctorById(idCategoryFromBundle)
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor, container, false)
    }


    private fun getListDoctorById(idCategory: Int){
        Log.i("hihi", idCategory.toString())

        RetrofitClient.instance.getListDoctor(18)
            .enqueue(object: Callback<DataDoctor>{
                override fun onResponse(call: Call<DataDoctor>, response: Response<DataDoctor>) {
                    if (response.isSuccessful){
                        val listDoctor: ArrayList<DataDoctorResponse>? = response.body()?.data
                        if (listDoctor != null){
                            val adapter = DoctorAdapter(listDoctor,"horizontal")
                            listDoctorRV.adapter = adapter
                            listDoctorRV.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                            Log.i("hihi", "list doctor null")
                        }else{
                            Log.i("hihi", "list doctor not null")

                        }

//                        Toast.makeText(mContext, "Call List Doctor Successful",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call List Doctor Successful",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DataDoctor>, t: Throwable) {
                    Toast.makeText(mContext, "Call api Fail",Toast.LENGTH_SHORT).show()

                }

            })

    }

}


