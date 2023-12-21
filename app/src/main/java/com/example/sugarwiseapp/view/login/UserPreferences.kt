package com.example.sugarwiseapp.view.login

import android.content.Context
import android.util.Log
import com.example.sugarwiseapp.data.response.UserData

class UserPreferences (context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: UserData){
        val editor = preferences.edit()
        Log.d("SetUser", "Name: ${value.name}")
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putString(PASSWORD, value.password)
        editor.putString(GENDER, value.gender)
        editor.putInt(WEIGHT, value.weight)
        editor.putInt(HEIGHT, value.height)
        editor.putInt(AGE, value.age)
        editor.putString(BLOODSUGAR, value.bloodSugar)
        editor.apply()
    }

    fun getUser(): UserData {
        val email = preferences.getString(EMAIL, null)
        val password = preferences.getString(PASSWORD, null)
        val name = preferences.getString(NAME, null)
        val gender = preferences.getString(GENDER, null)
        val weight = preferences.getInt(WEIGHT, 0)
        val height = preferences.getInt(HEIGHT, 0)
        val age = preferences.getInt(AGE, 0)
        val bloodsugar = preferences.getString(BLOODSUGAR, null)
        return UserData(password, gender, name, weight, height, bloodsugar, email, age)
    }

    fun removeUser(){
        val editor = preferences.edit().clear()
        editor.apply()
    }

    companion object {
        const val PREFS_NAME = "user_pref"
        const val PASSWORD = "password"
        const val GENDER = "gender"
        const val NAME = "name"
        const val WEIGHT = "weight"
        const val HEIGHT = "height"
        const val BLOODSUGAR = "bloodSugar"
        const val EMAIL = "email"
        const val AGE = "age"
    }
}
