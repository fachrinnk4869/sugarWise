package com.example.sugarwiseapp.view.login

import android.content.Context
import android.util.Log
import com.example.sugarwiseapp.data.response.LoginData

class LoginPreferences (context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLogin(value: LoginData){
        val editor = preferences.edit()
        Log.d("SetLogin", "Name: ${value.userId}")
        editor.putString(USER_ID, value.userId)
        editor.putString(EMAIL, value.email)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }

    fun getUser(): LoginData {
        val email = preferences.getString(EMAIL, null)
        val userId = preferences.getString(USER_ID, null)
        val token = preferences.getString(TOKEN, null)
        return LoginData(userId, email, token)
    }

    fun removeUser(){
        val editor = preferences.edit().clear()
        editor.apply()
    }

    companion object {
        const val PREFS_NAME = "login_pref"
        const val EMAIL = "email"
        const val USER_ID = "userId"
        const val TOKEN = "token"
    }
}