package com.example.encare.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.encare.R
import com.example.encare.fragments.CategoryFragment
import com.example.encare.fragments.DoctorFragment
import com.example.encare.models.DataCategoryResponse
import com.example.encare.myInterface.ClickItemListener
import kotlinx.android.synthetic.main.list_category.view.*

class CategoryAdapter(
    private val listCategory:List<DataCategoryResponse>,

):RecyclerView.Adapter<CategoryAdapter.CategoryAdapterHolders>(), ClickItemListener {
    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterHolders {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_category, parent, false)
        mContext = parent.context
        return CategoryAdapterHolders(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapterHolders, position: Int) {
        val currentCategory = listCategory[position]
        val idCategory = currentCategory.categoryId

        holder.nameCategory.text = currentCategory.name
        holder.nameCategory.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v?.context as AppCompatActivity
                if (idCategory != null){
                    val doctorFragment = DoctorFragment()

                    // add data vao bundle de gui sang fragment khac
                    val bundle = Bundle()
                    bundle.putInt("idCategory", idCategory)
                    doctorFragment.arguments = bundle

                    activity.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
                        .add(R.id.FrameLayoutHome, doctorFragment)
                        .addToBackStack(null)
                        .commit()
                }

//                Toast.makeText(mContext, currentCategory.categoryId.toString(), Toast.LENGTH_SHORT).show()

            }

        })
    }

    override fun getItemCount(): Int = listCategory.size

    inner class CategoryAdapterHolders(view: View):RecyclerView.ViewHolder(view.rootView){
        val nameCategory:TextView = view.name_category

    }

    override fun onClickItemCategory(idCategory: Int) {
        val bundle = Bundle()
        bundle.putInt("id", idCategory)
    }


}

