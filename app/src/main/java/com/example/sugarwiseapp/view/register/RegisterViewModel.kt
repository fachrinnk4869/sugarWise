package com.example.sugarwiseapp.view.register

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.sugarwiseapp.data.Repository
import com.example.sugarwiseapp.data.response.RegisterData
import com.example.sugarwiseapp.view.login.LoginPreferences

class RegisterViewModel(
    application: Application,
    loginPreferences: LoginPreferences
) :
    ViewModel() {

    private val repository = Repository(application, loginPreferences)

    fun userRegister(data : RegisterData) =
        repository.register(data.name!!, data.email!!, data.password!!, data.gender!!, data.age!!, data.height!!, data.weight!!, data.bloodSugar!!)

}