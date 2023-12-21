package com.example.sugarwiseapp.view.register

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.response.RegisterData
import com.example.sugarwiseapp.databinding.ActivityRegisterTwoBinding
import com.example.sugarwiseapp.view.login.LoginActivity


class RegisterTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterTwoBinding
    private lateinit var registerData: RegisterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerData = RegisterData()

        val data = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<RegisterData>(REGISTER_DATA, RegisterData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<RegisterData>(REGISTER_DATA)
        }

        if (data != null) {
            registerData.email = data.email
            registerData.password = data.password
        }
//
        val spinnerGender : Spinner = binding.spinnerGender

        // Ambil sumber data dari string-array di file strings.xml
//        val genderArray = resources.getStringArray(R.array.gender_array)
        val categories = listOf("Male", "Female", "No Answer")

        // Buat adaptor untuk Spinner
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

//         Set adaptor ke Spinner
        spinnerGender.adapter = genderAdapter

        setupAction()
    }

    private fun setupAction() {
        binding.btnContinue.setOnClickListener {
            val name = binding.etGroupFive.text.toString()
            val gender = binding.spinnerGender.selectedItem.toString()
            val age = binding.etGroupFiveOne.text.toString()
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(age))
            {
                moveToNext(name, gender, age.toInt())
            } else {
                showAlert(
                    getString(R.string.register_gagal),
                    getString(R.string.register_gagal_1)
                ) { finish() }
            }
        }

        binding.txtSignIn.setOnClickListener {
            val intent = Intent(this@RegisterTwoActivity, LoginActivity::class.java)
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

    private fun moveToNext(name: String, gender: String, age: Int){
        registerData.name = name
        registerData.gender = gender
        registerData.age = age

        val intent = Intent(this@RegisterTwoActivity, RegisterThreeActivity::class.java)
        intent.putExtra(RegisterThreeActivity.REGISTER_DATA, registerData)
        startActivity(intent)
    }

    companion object{
        const val REGISTER_DATA = "data"
    }
}