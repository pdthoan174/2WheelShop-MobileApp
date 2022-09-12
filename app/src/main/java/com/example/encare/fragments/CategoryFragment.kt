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
import com.example.encare.adapters.CategoryAdapter
import com.example.encare.api.RetrofitClient
import com.example.encare.models.DataCategory
import com.example.encare.models.DataCategoryResponse
import com.example.encare.myInterface.ClickItemListener
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment(){
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        listCategory()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        view.btnBack.setOnClickListener {
            // tro ve fragment truoc
            fragmentManager?.popBackStack()
        }
        return view
    }

    private fun listCategory() {
        RetrofitClient.instance.getListCategory()
            .enqueue(object : Callback<DataCategory>{
                override fun onResponse(
                    call: Call<DataCategory>,
                    response: Response<DataCategory>
                ) {
                    if (response.isSuccessful){

                        val listCategory = response.body()?.data!!
                        if (listCategory != null){
                            val adapter = CategoryAdapter(listCategory)
                            listCategoryRV.adapter = adapter
                            listCategoryRV.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false)
                        }
//                        Log.i("hihi", listCategory?.get(1)?.name.toString())
                    }else{
                        Log.i("hihi","get list doctor fail")
                    }
                }

                override fun onFailure(call: Call<DataCategory>, t: Throwable) {
                    Toast.makeText(mContext, "Call API List Category Fail",Toast.LENGTH_SHORT).show()
                }
            })
    }

//    fun showDoctorFragment(category:DataCategoryResponse){
//        val fragmentDoctor = DoctorFragment()
//        val transaction = fragmentManager?.beginTransaction()
//
//        // Bundle dung de chuyen data giua cac activity
//        lateinit var bundle:Bundle
//
//        // phai implement Serializable trong DataCategoryResponse truoc
//        bundle.putSerializable("object_category", category)
//        fragmentDoctor.arguments = bundle // fragmentDoctor chua du lieu chuyen sang
//
//        transaction?.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.fade_out, R.anim.fade_in)
//        transaction?.add(R.id.FrameLayoutHome, fragmentDoctor)
//        transaction?.addToBackStack(null)
//        transaction?.commit()
//    }



}