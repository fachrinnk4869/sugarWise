package com.example.sugarwiseapp.view.statistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.databinding.ActivityStatisticBinding
import com.example.sugarwiseapp.view.addFood.ScanActivity
import com.example.sugarwiseapp.view.home.HomeActivity
import com.example.sugarwiseapp.view.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class StatisticActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        // Add click listeners for bottom navigation items
        binding.btnHome.setOnClickListener {
            // Start HomeActivity (or any other activity you want)
            val intent = Intent(this@StatisticActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnCamera.setOnClickListener {
            // Start CameraActivity (or any other activity you want)
            val intent = Intent(this@StatisticActivity, ScanActivity::class.java)
            startActivity(intent)
        }

        binding.btnStatistic.setOnClickListener {
            // Start StatisticActivity (or any other activity you want)
//            val intent = Intent(this@StatisticActivity, StatisticActivity::class.java)
//            startActivity(intent)
        }
    }
}