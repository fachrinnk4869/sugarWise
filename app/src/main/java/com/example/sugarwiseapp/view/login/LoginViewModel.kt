package com.example.sugarwiseapp.view.login

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import com.example.sugarwiseapp.data.Repository

class LoginViewModel(
    application: Application,
    loginPreferences: LoginPreferences
) : ViewModel(){

    private val repository = Repository(application, loginPreferences)

    fun userLogin(email: String, password: String) = repository.login(email, password)

    fun getUser() = repository.getUser()
}