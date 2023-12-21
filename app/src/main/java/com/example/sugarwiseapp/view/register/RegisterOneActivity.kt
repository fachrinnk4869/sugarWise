package com.example.sugarwiseapp.view.register

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.response.RegisterData
import com.example.sugarwiseapp.databinding.ActivityRegisterOneBinding
import com.example.sugarwiseapp.view.login.LoginActivity

class RegisterOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterOneBinding
    private lateinit var registerData: RegisterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerData = RegisterData()
        setupAction()
    }

    private fun setupAction() {
        binding.btnContinue.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etGroupFive.text.toString()
            val passwordConf = binding.etGroupFiveOne.text.toString()
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConf))
            {
                if (password == passwordConf){
                    moveToNext(email, password)
                }else{
                    showAlert(
                        getString(R.string.register_gagal),
                        getString(R.string.register_gagal_2)
                    ){}
                }
            } else {
                showAlert(
                    getString(R.string.register_gagal),
                    getString(R.string.register_gagal_1)
                ) { }
            }
        }

        binding.txtSignIn.setOnClickListener {
            val intent = Intent(this@RegisterOneActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Menambahkan onClickListener ke ImageView (panah kembali)
        binding.imageArrowTwo.setOnClickListener {
            onBackPressed()
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

    private fun moveToNext(email: String, password: String){
        registerData.email = email
        registerData.password = password

        val intent = Intent(this@RegisterOneActivity, RegisterTwoActivity::class.java)
        intent.putExtra(RegisterTwoActivity.REGISTER_DATA, registerData)
        startActivity(intent)
    }
}