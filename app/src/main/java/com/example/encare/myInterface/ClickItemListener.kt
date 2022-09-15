package com.example.encare.myInterface

import com.example.encare.models.DataCategoryResponse

// Khi duoc goi se lay category truyen vao noi implement interface nay
interface ClickItemListener {
    fun onClickItemCategory(idCategory: Int)
}