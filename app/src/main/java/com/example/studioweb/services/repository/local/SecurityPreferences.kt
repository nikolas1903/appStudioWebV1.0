package com.example.studioweb.services.repository.local

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences (context: Context){

    private val mPreferences: SharedPreferences =
        context.getSharedPreferences("users", Context.MODE_PRIVATE)


    fun store(key: String, value: String) {
        mPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String) : String {
        return mPreferences.getString(key, "") ?: ""
    }

    fun remove(key: String) {
        mPreferences.edit().remove(key).apply()
    }

}