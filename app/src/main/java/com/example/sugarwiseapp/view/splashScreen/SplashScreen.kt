package com.example.sugarwiseapp.view.splashScreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.view.login.LoginActivity
import com.example.sugarwiseapp.view.onboarding.Onboarding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreen : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        lifecycleScope.launch {
            delay(splashTime)
            withContext(Dispatchers.Main) {
//                if (loginModel.name != null && loginModel.userId != null && loginModel.token != null) {
//                    // Kondisi pertama: Pengguna sudah login, pergi ke MainActivity
//                    val intentToList = Intent(this@SplashScreen, ListStoryActivity::class.java)
//                    startActivity(intentToList)
//                } else
                    if (isFirstRun()) {
                    // Kondisi kedua: Aplikasi baru diinstal, pergi ke OnboardingActivity
                    val intentToOnboarding = Intent(this@SplashScreen, Onboarding::class.java)
                    startActivity(intentToOnboarding)
                } else {
                    // Kondisi ketiga: Tidak ada user preference, pergi ke LoginActivity
                    val intentToLogin = Intent(this@SplashScreen, LoginActivity::class.java)
                    startActivity(intentToLogin)
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun isFirstRun(): Boolean {
        // Menggunakan key "firstRun" untuk menandai apakah aplikasi baru diinstal
        val firstRun = sharedPreferences.getBoolean("firstRun", true)
        if (firstRun) {
            // Jika ini pertama kali dijalankan, tandai bahwa sudah bukan pertama kali lagi
            sharedPreferences.edit().putBoolean("firstRun", false).apply()
        }
        return firstRun
    }

    companion object {
        const val splashTime: Long = 1500
    }
}
