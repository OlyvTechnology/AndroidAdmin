package com.olyv.olyvadmin.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref (context: Context) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Olyv", Context.MODE_PRIVATE)


    var name:String?
    get() = sharedPreferences.getString("first_name",null)
    set(value) {
        sharedPreferences.edit().putString("first_name",value).apply()
    }

    var profile_picture_url:String?
    get() = sharedPreferences.getString("profile_picture_url",null)
    set(value) {
        sharedPreferences.edit().putString("profile_picture_url",value).apply()
    }

    var phone:String?
    get() = sharedPreferences.getString("phone",null)
    set(value) {
        sharedPreferences.edit().putString("phone",value).apply()
    }

    var uuid:String?
    get() = sharedPreferences.getString("uuid",null)
    set(value) {
        sharedPreferences.edit().putString("uuid",value).apply()
    }

    var isLogin:Boolean
    get() = sharedPreferences.getBoolean("login",false)
    set(value) {
        sharedPreferences.edit().putBoolean("login",value).apply()
    }


}
