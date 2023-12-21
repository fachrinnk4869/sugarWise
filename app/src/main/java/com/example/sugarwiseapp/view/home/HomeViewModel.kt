package com.example.sugarwiseapp.view.home

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.sugarwiseapp.data.Repository
import com.example.sugarwiseapp.view.login.LoginPreferences
import okhttp3.MultipartBody

class HomeViewModel(
    application: Application,
    loginPreferences: LoginPreferences
) : ViewModel(){

    private val repository = Repository(application, loginPreferences)

    fun getUser() = repository.getUser()
    fun uploadImage(imageFile: MultipartBody.Part) = repository.uploadImage(imageFile)
    fun uploadFood(foodId: String, quantity: Int) = repository.uploadFood(foodId, quantity)
    fun getDailyFood() = repository.getDailyFood()
    fun getAllFood() = repository.getAllFood()
}