package com.example.wheelshop.DataLocal

import android.app.Application
import com.google.gson.Gson

//Dùng để lấy context của úng dụng
// Khai báo trong androidmanifest.xml để app ủy quyền xử lí vấn đề
class App:Application() {

    // Để có thể gọi trực tiêos biến shared từ các class khác: App.shared.getSomething()
    companion object {
        lateinit var shared: App
    }
    val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        shared = this
    }


}