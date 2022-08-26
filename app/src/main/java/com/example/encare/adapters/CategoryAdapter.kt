package com.example.encare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.encare.R
import com.example.encare.models.DataCategory
import com.example.encare.models.DataCategoryResponse
import kotlinx.android.synthetic.main.list_category.view.*

class CategoryAdapter(private val listCategory:ArrayList<DataCategoryResponse>):RecyclerView.Adapter<CategoryAdapter.CategoryAdapterHolders>() {
    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterHolders {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.list_category, parent, false)
        mContext = parent.context
        return CategoryAdapterHolders(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapterHolders, position: Int) {
        val currentCategory = listCategory[position]

        holder.nameCategory.text = currentCategory.name
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class CategoryAdapterHolders(view: View):RecyclerView.ViewHolder(view){
        val nameCategory:TextView = view.name_category
    }
}

