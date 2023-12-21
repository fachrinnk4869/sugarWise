package com.example.sugarwiseapp.view.addFood

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.Result
import com.example.sugarwiseapp.data.response.Predictions
import com.example.sugarwiseapp.databinding.ActivityScanResultBinding
import com.example.sugarwiseapp.view.ViewModelFactory
import com.example.sugarwiseapp.view.home.HomeActivity
import com.example.sugarwiseapp.view.home.HomeViewModel
import com.example.sugarwiseapp.view.login.LoginPreferences

class ScanResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanResultBinding
    private lateinit var resultViewModel: HomeViewModel
    private lateinit var foodId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultViewModel = obtainViewModel(this as AppCompatActivity)

        setupView()
        setupAction()
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        val loginPreferences = LoginPreferences(activity.application)
        val factory = ViewModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[HomeViewModel::class.java]
    }

    private fun setupView() {
        val food = intent.getParcelableExtra<Predictions>(FOOD_DATA)
        val imagePath = intent.getStringExtra(PATH)

        if (food != null) {
            foodId = food.id
            binding.txtDonut.text = food.foodName
            binding.button3.text = food.sugars.toString()
            binding.button4.text = food.totalFat.toString()
            binding.button2.text = food.protein.toString()
        }

        // Set image path to ImageView
        imagePath?.let {
            val imageUri = Uri.parse(it)
            binding.imageTripleChocolat.setImageURI(imageUri)
        }
    }

    private fun setupAction() {
        // Menambahkan onClickListener ke ImageView (panah kembali)
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnAddToDiaryLogOne.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage(){
        val quantityText = binding.etQuantity.text.toString()
        val quantity: Int = quantityText.toInt()
        if (!TextUtils.isEmpty(quantityText)){
            resultViewModel.uploadFood(
                foodId,
                quantity).
            observe(this@ScanResultActivity){ result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showAlert(
                                getString(R.string.post_fail_1),
                                getString(R.string.post_fail_2)
                            )
                            { }
                        }
                        is Result.Success -> {
                            showLoading(false)
                            moveToHome()
                        }
                    }
                }
            }
        }else{
            showAlert(
                getString(R.string.quantityEmpty),
                getString(R.string.quantityEmpty_2)
            )
            { }
        }
    }

    private fun moveToHome(){
        val intent = Intent(this@ScanResultActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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

    companion object{
        const val FOOD_DATA = "data"
        const val PATH = "path"
        const val FOOD_ID =  "id"
    }
}