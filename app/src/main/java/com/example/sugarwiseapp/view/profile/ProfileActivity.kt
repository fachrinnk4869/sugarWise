package com.example.sugarwiseapp.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sugarwiseapp.view.splashScreen.SplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.Result
import com.example.sugarwiseapp.data.response.LoginData
import com.example.sugarwiseapp.databinding.ActivityHomeBinding
import com.example.sugarwiseapp.databinding.ActivityProfileBinding
import com.example.sugarwiseapp.view.ViewModelFactory
import com.example.sugarwiseapp.view.home.HomeViewModel
import com.example.sugarwiseapp.view.login.LoginPreferences
import com.example.sugarwiseapp.view.login.UserPreferences

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userPreferences: UserPreferences
    private lateinit var loginPreferences: LoginPreferences
    private lateinit var userViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = obtainViewModel(this as AppCompatActivity)
        userPreferences = UserPreferences(this)
        loginPreferences = LoginPreferences(this)

        setupView()

        binding.btnSignOut.setOnClickListener{
            userPreferences.removeUser()
            loginPreferences.removeUser()
            val intent = Intent(this@ProfileActivity, SplashScreen::class.java)
            startActivity(intent)
            finishAffinity()
        }

        // Menambahkan onClickListener ke ImageView (panah kembali)
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupView() {
        binding.userName.text =  userPreferences.getUser().name
        binding.tvFullnameText.text = userPreferences.getUser().email
        binding.tvAgeText.text = userPreferences.getUser().age.toString()
        binding.tvSexText.text = userPreferences.getUser().gender
        binding.tvWeightText.text = userPreferences.getUser().weight.toString()
        binding.tvHeightText.text = userPreferences.getUser().height.toString()
        binding.tvBloodText.text = userPreferences.getUser().bloodSugar
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        val loginPreferences = LoginPreferences(activity.application)
        val factory = ViewModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[HomeViewModel::class.java]
    }
}