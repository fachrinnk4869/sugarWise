package com.example.sugarwiseapp.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sugarwiseapp.view.home.HomeViewModel
import com.example.sugarwiseapp.view.login.LoginPreferences
import com.example.sugarwiseapp.view.login.LoginViewModel
import com.example.sugarwiseapp.view.register.RegisterViewModel

class ViewModelFactory (
    private val application: Application,
    private val loginPreferences: LoginPreferences
) : ViewModelProvider.NewInstanceFactory()
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
//            modelClass.isAssignableFrom(AccountViewModel::class.java) -> AccountViewModel(
//                application,
//                loginPreferences
//            ) as T
//            modelClass.isAssignableFrom(SplashscreenViewModel::class.java) -> SplashscreenViewModel(
//                application,
//                loginPreferences
//            ) as T
//            modelClass.isAssignableFrom(InsertStoryViewModel::class.java) -> InsertStoryViewModel(
//                application,
//                loginPreferences
//            ) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                application,
                loginPreferences
            ) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                application,
                loginPreferences
            ) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                application,
                loginPreferences
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            application: Application,
            loginPreferences: LoginPreferences
        ): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(application, loginPreferences)
            }
    }
}