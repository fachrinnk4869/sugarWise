package com.example.sugarwiseapp.view.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.databinding.ActivityLoginBinding
import com.example.sugarwiseapp.view.ViewModelFactory
import com.example.sugarwiseapp.data.Result
import com.example.sugarwiseapp.data.response.LoginData
import com.example.sugarwiseapp.data.response.LoginResponse
import com.example.sugarwiseapp.data.response.UserData
import com.example.sugarwiseapp.data.response.UserResponse
import com.example.sugarwiseapp.view.home.HomeActivity
import com.example.sugarwiseapp.view.register.RegisterOneActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = obtainViewModel(this as AppCompatActivity)

        showLoading(false)
        setupView()
        setupAction()
    }

    private fun obtainViewModel(activity: AppCompatActivity): LoginViewModel {
        val loginPreferences = LoginPreferences(activity.application)
        val factory = ViewModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[LoginViewModel::class.java]
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
//        val myPasswordEditText = findViewById<MyPasswordEditText>(R.id.passwordEditText)
//        val errorTextView = findViewById<TextView>(R.id.errorPass)
//        myPasswordEditText.setErrorTextView(errorTextView)
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etGroupFive.text.toString()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                login(email, password)
            } else {
                showAlert(
                    getString(R.string.login_gagal),
                    getString(R.string.login_gagal_1)
                )
                { finish() }
            }
        }

        binding.btnSignUp.setOnClickListener{
            val moveToRegister = Intent(this, RegisterOneActivity::class.java)
            startActivity(moveToRegister)
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.userLogin(email, password).observe(this@LoginActivity) { result ->
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
                        loginSuccess(result.data)
                    }
                }
            }
        }
    }

    private fun loginSuccess(loginResponse: LoginResponse) {
        saveLoginData(loginResponse)
        getUserData(loginResponse)

        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveLoginData(loginResponse: LoginResponse) {
        val loginPreference = LoginPreferences(this)
        val loginResult = loginResponse.data
        val loginModel = LoginData(
            email = loginResult.email, userId = loginResult.userId, token = loginResult.token
        )
        loginPreference.setLogin(loginModel)
    }

    private fun getUserData(loginResponse: LoginResponse) {
        val loginResult = loginResponse.data
        loginViewModel.getUser().observe(this@LoginActivity) { result ->
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
                        saveUserData(result.data)
                    }
                }
            }
        }
    }

    private fun saveUserData(userResponse: UserResponse) {
        val userPreferences = UserPreferences(this)
        val getResult = userResponse.data
        val userModel = UserData(
            name = getResult.name,
            email = getResult.email,
            password = getResult.password,
            gender = getResult.gender,
            weight = getResult.weight,
            height = getResult.height,
            age = getResult.age,
            bloodSugar = getResult.bloodSugar.toString()
        )
        userPreferences.setUser(userModel)
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