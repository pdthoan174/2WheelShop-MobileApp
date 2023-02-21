package com.example.wheelshop.DataLocal

import android.content.Context

// class SharedPreferencesOptimal thực hiện toàn bọ việc tạo SharedPreferences, put và get dũ liệu
// Tạo class SharedPreferencesOptimal ở dạng singleton như sau
// Dùng để khởi tạo 1 lần cho cả chượng trình sử dụng

object SharedPreferencesOptimal {
    private const val INFO_USER:String = "INFO_USER"

    //App.shared laf context cua ung dung
    // val preferences:SharedPreferences = this.getSharedPreferences("Info User", Context.MODE_PRIVATE) : Code cũ
    private val sharedPreferences = App.shared.getSharedPreferences(INFO_USER, Context.MODE_PRIVATE)

    // Generic Functions
    // Vi sử dụng Generic Function nên khi data truyền vào kiểu nào thì sẻ lấy ra theo kiểu đó
    // Dua du lieu vao
    fun <T> put(key: String, data:T){
        val editor = sharedPreferences.edit()
        when(data){
            is String -> {
                editor.putString(key, data as String)
            }
            is Float -> {
                editor.putFloat(key, data as Float)
            }
            is Boolean -> {
                editor.putBoolean(key, data as Boolean)
            }
            is Int -> {
                editor.putInt(key, data as Int)
            }
            is Long -> {
                editor.putLong(key, data as Long)
            }
            // Chuyen object thanh string
            // Luu object duoi dang string
            else -> {
                editor.putString(key, App.shared.gson.toJson(data))
            }
        }
        editor.apply()
    }

    // lay du lieu ra
    fun <T> get(key: String, type:Class<T>):T{
        return when (type) {
            String::class.java -> {
                sharedPreferences.getString(key, "") as T
            }
            Float::class.java -> {
                sharedPreferences.getFloat(key, Float.MAX_VALUE) as T
            }
            Boolean::class.java -> {
                sharedPreferences.getBoolean(key, false) as T
            }
            Int::class.java -> {
                sharedPreferences.getInt(key, Int.MAX_VALUE) as T
            }
            Long::class.java -> {
                sharedPreferences.getLong(key, Long.MAX_VALUE) as T
            }
            else -> {
                // lay ra duoi dang string chuyen thanh object
                val jsonString = sharedPreferences.getString(key, "")
                App.shared.gson.fromJson(jsonString, type)
            }

        }
    }

}