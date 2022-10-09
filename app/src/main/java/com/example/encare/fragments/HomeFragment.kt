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
import com.bumptech.glide.Glide
import com.example.encare.DataLocal.SharedPreferencesOptimal
import com.example.encare.R
import com.example.encare.adapters.DoctorAdapter
import com.example.encare.api.RetrofitClient
import com.example.encare.models.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeFragment : Fragment() {

    private  var token: String = ""
    private var TAG = "TAG_FRAGMENT"

    private lateinit var mContext:Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getToken()
        getProfile()
        listDoctor()

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.itemCategory4.setOnClickListener {
            showCategoryFragment()
        }

        return view
    }

    private fun showCategoryFragment() {
        val fragment = CategoryFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
        transaction?.replace(R.id.FrameLayoutHome, fragment, TAG)
        // them fragment vao stack
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun getToken(){
        token = SharedPreferencesOptimal.get("TOKEN", String::class.java)
    }

    private fun getProfile(){
        RetrofitClient.instance.getProfile()
            .enqueue(object: Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    // description: trong RegisterResponse.kt
                    val infoProfile = response.body()

                    val accountResponse = infoProfile?.data?.accountResponse
                    if (response.isSuccessful){
                        textName.text = accountResponse?.name
                        val avatar = accountResponse?.avatar
                        if (avatar == null){
                            avt1.setImageResource(R.drawable.avatar)
                        }else{
                            // load using Glide
                            Glide.with(mContext)
                                .load(avatar)
                                .error(R.drawable.avatar)
                                .into(avt1)
                        }
                    }else{
                        Toast.makeText(mContext, "Get profile Fail", Toast.LENGTH_SHORT).show()
//                        Toast.makeText(applicationContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Toast.makeText(mContext, "Call api error", Toast.LENGTH_SHORT).show()
                }
            })
    }
    private fun listDoctor(){
        RetrofitClient.instance.getListDoctor(18)
            .enqueue(object: Callback<DataDoctor>{
                override fun onResponse(
                    call: Call<DataDoctor>,
                    response: Response<DataDoctor>
                ) {
                    if (response.isSuccessful){
                        val list: ArrayList<DataDoctorResponse>? = response.body()?.data

                        if (list != null){
                            val adapter = DoctorAdapter(list,"vertical")
                            list_doctor.adapter = adapter
                            list_doctor.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                        }
                        Toast.makeText(mContext, "Call List Doctor Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mContext, "Call Doctor Fail",Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<DataDoctor>, t: Throwable) {
                    Toast.makeText(mContext, "Call List Doctor Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

}