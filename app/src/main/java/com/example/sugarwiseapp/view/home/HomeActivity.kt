package com.example.sugarwiseapp.view.home

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.response.UserData
import com.example.sugarwiseapp.databinding.ActivityHomeBinding
import com.example.sugarwiseapp.view.ViewModelFactory
import com.example.sugarwiseapp.view.addFood.ScanActivity
import com.example.sugarwiseapp.view.login.LoginPreferences
import com.example.sugarwiseapp.view.login.UserPreferences
import com.example.sugarwiseapp.view.profile.ProfileActivity
import com.example.sugarwiseapp.view.statistic.StatisticActivity
import com.example.sugarwiseapp.data.Result
import com.example.sugarwiseapp.data.response.DataItem
import com.example.sugarwiseapp.view.adapter.ListFoodAdapter
import com.example.sugarwiseapp.view.addFood.ListFoodActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var userModel: UserData
    private lateinit var loginPreferences: LoginPreferences
    private var waterValue : Int = 0
    private lateinit var handler: Handler
    private lateinit var adapter: ListFoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreferences = UserPreferences(this)
        loginPreferences = LoginPreferences(this)
        userModel = userPreferences.getUser()
        homeViewModel = obtainViewModel(this as AppCompatActivity)

        handler = Handler(Looper.getMainLooper())

        binding.textSugar.text = "0"
        binding.textFat.text = "0"
        binding.textProtein.text = "0"
        binding.waterValue.text = waterValue.toString()

        adapter = ListFoodAdapter()

        binding.apply{
            rvListFood.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvListFood.setHasFixedSize(true)
            rvListFood.adapter = adapter
        }

        getDailyFood()
        setupAction()
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        val loginPreferences = LoginPreferences(activity.application)
        val factory = ViewModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[HomeViewModel::class.java]
    }

    private fun setupAction(){
        binding.account.setOnClickListener{
            val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.plus.setOnClickListener{
            waterValue += 250
            binding.waterValue.text = waterValue.toString()
        }

        binding.addFood.setOnClickListener {
            val intent = Intent(this@HomeActivity, ListFoodActivity::class.java)
            startActivity(intent)
        }

        binding.btnCamera.setOnClickListener {
            // Start CameraActivity (or any other activity you want)
            val intent = Intent(this@HomeActivity, ScanActivity::class.java)
            startActivity(intent)
        }

        binding.btnStatistic.setOnClickListener {
            // Start StatisticActivity (or any other activity you want)
            val intent = Intent(this@HomeActivity, StatisticActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDailyFood() {
        homeViewModel.getDailyFood().observe(this@HomeActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        binding.empty.text = getString(R.string.food_empty)
                    }
                    is Result.Success -> {
                        showLoading(false)
                            adapter.setList(result.data.data)
                            calculateFood(result.data.data)
                    }
                }
            }
        }
    }

    private fun calculateFood(data: List<DataItem>) {
        val totalSugar = data.sumOf { it.data.totalSugar.toInt() } // menghitung jumlah total_sugar
        val totalProtein = data.sumOf { it.data.totalProtein.toInt() } // menghitung jumlah total_protein
        val totalFat = data.sumOf { it.data.totalFat.toInt() } // menghitung jumlah total_fat

        binding.textSugar.text = totalSugar.toString()
        binding.textFat.text = totalFat.toString()
        binding.textProtein.text = totalProtein.toString()
    }

    private fun showAlert(
        title: String,
        message: String,
        positiveAction: (dialog: DialogInterface) -> Unit
    ) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                positiveAction.invoke(dialog)
            }
            setCancelable(false)
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}
