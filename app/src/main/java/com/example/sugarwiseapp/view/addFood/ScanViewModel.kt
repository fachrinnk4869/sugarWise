package com.example.sugarwiseapp.view.addFood

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.sugarwiseapp.data.Repository
import com.example.sugarwiseapp.view.login.LoginPreferences
import okhttp3.MultipartBody

class ScanViewModel(
    application: Application,
    loginPreferences: LoginPreferences
) : ViewModel() {

    private val repository = Repository(application, loginPreferences)

    fun uploadImage(imageFile: MultipartBody.Part) = repository.uploadImage(imageFile)

}