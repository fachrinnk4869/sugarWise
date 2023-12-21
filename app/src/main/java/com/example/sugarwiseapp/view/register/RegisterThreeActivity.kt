package com.example.sugarwiseapp.view.register

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.Result
import com.example.sugarwiseapp.data.response.RegisterData
import com.example.sugarwiseapp.databinding.ActivityRegisterThreeBinding
import com.example.sugarwiseapp.view.ViewModelFactory
import com.example.sugarwiseapp.view.login.LoginActivity
import com.example.sugarwiseapp.view.login.LoginPreferences

class RegisterThreeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterThreeBinding
    private lateinit var registerData: RegisterData
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerData = RegisterData()
        registerViewModel = obtainViewModel(this as AppCompatActivity)

        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<RegisterData>(RegisterTwoActivity.REGISTER_DATA, RegisterData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<RegisterData>(RegisterTwoActivity.REGISTER_DATA)
        }

        if (data != null) {
            registerData.email = data.email
            registerData.password = data.password
            registerData.name = data.name
            registerData.gender = data.gender
            registerData.age = data.age
        }

        setupAction()
    }

    private fun obtainViewModel(activity: AppCompatActivity): RegisterViewModel {
        val loginPreferences = LoginPreferences(activity.application)
        val factory = ViewModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[RegisterViewModel::class.java]
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            val height = binding.etGroupFive.text.toString()
            val weight = binding.etWeight.text.toString()
            val bloodSugar = binding.etGroupFiveOne.text.toString()
            if (!TextUtils.isEmpty(height) && !TextUtils.isEmpty(weight) && !TextUtils.isEmpty(bloodSugar))
            {
                registerData.height = height.toInt()
                registerData.weight = weight.toInt()
                registerData.bloodSugar = bloodSugar.toFloat()
                register()
            } else {
                showAlert(
                    getString(R.string.register_gagal),
                    getString(R.string.register_gagal_1)
                ) { finish() }
            }
        }

        binding.txtSignIn.setOnClickListener {
            val intent = Intent(this@RegisterThreeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Menambahkan onClickListener ke ImageView (panah kembali)
        binding.imageArrowTwo.setOnClickListener {
            onBackPressed()
        }
    }

    private fun register() {
        registerViewModel.userRegister(data = registerData)
            .observe(this@RegisterThreeActivity) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Error -> {
                            showLoading(false)
//                            val errorMessage = intent.extras?.getString("errorMessage")
                            showAlert(
                                getString(R.string.register_gagal),
                                getString(R.string.register_gagal_2)
                            )
                            { }
                        }
                        is Result.Success -> {
                            showLoading(false)
                            registerSuccess()
                        }
                    }
                }
            }
    }

    private fun registerSuccess() {
        showAlert(
            getString(R.string.register_berhasil),
            getString(R.string.register_berhasil_1)
        )
        { navigateToLogin() }
        binding.etGroupFive.text.clear()
        binding.etWeight.text.clear()
        binding.etGroupFiveOne.text.clear()
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

    private fun navigateToLogin() {
        val intent = Intent(this@RegisterThreeActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val REGISTER_DATA = "data"
    }
}