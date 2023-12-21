package com.example.sugarwiseapp.view.addFood

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.Result
import com.example.sugarwiseapp.databinding.ActivityListFoodBinding
import com.example.sugarwiseapp.view.ViewModelFactory
import com.example.sugarwiseapp.view.adapter.ListFoodAdapter
import com.example.sugarwiseapp.view.adapter.ListFoodAllAdapter
import com.example.sugarwiseapp.view.home.HomeViewModel
import com.example.sugarwiseapp.view.login.LoginPreferences

class ListFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFoodBinding
    private lateinit var adapter: ListFoodAllAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = obtainViewModel(this as AppCompatActivity)
        adapter = ListFoodAllAdapter()

        binding.apply{
            recyclerViewFood.layoutManager = LinearLayoutManager(this@ListFoodActivity)
            recyclerViewFood.setHasFixedSize(true)
            recyclerViewFood.adapter = adapter
        }

        getFood()

        // Menambahkan onClickListener ke ImageView (panah kembali)
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnScan.setOnClickListener {
            val intent = Intent(this@ListFoodActivity, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        val loginPreferences = LoginPreferences(activity.application)
        val factory = ViewModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[HomeViewModel::class.java]
    }

    private fun getFood() {
        homeViewModel.getAllFood().observe(this@ListFoodActivity) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showAlert(
                            getString(R.string.login_gagal),
                            getString(R.string.login_gagal_1)
                        )
                        { }
                    }
                    is Result.Success -> {
                        showLoading(false)
                        adapter.setList(result.data.data)
                    }
                }
            }
        }
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