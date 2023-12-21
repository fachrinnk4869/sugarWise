package com.example.sugarwiseapp.view.addFood

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sugarwiseapp.R
import com.example.sugarwiseapp.data.Result
import com.example.sugarwiseapp.data.response.Predictions
import com.example.sugarwiseapp.databinding.ActivityScanBinding
import com.example.sugarwiseapp.view.ViewModelFactory
import com.example.sugarwiseapp.view.home.HomeActivity
import com.example.sugarwiseapp.view.home.HomeViewModel
import com.example.sugarwiseapp.view.login.LoginPreferences
import com.example.sugarwiseapp.view.register.RegisterTwoActivity
import com.example.sugarwiseapp.view.statistic.StatisticActivity
import com.example.sugarwiseapp.view.utils.createCustomTempFile
import com.example.sugarwiseapp.view.utils.reduceFileImage
import com.example.sugarwiseapp.view.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private lateinit var scanViewModel: HomeViewModel
    private lateinit var foodPredict: Predictions
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        scanViewModel = obtainViewModel(this as AppCompatActivity)
        foodPredict = Predictions("food", 0.1f, 0.1f, "url", 0.1f, "id", 0)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        setupAction()
    }

    private fun obtainViewModel(activity: AppCompatActivity): HomeViewModel {
        val loginPreferences = LoginPreferences(activity.application)
        val factory = ViewModelFactory.getInstance(activity.application, loginPreferences)
        return ViewModelProvider(activity, factory)[HomeViewModel::class.java]
    }

    private fun setupAction(){
        binding.btnCameraUpload.setOnClickListener{
            startTakePhoto()
        }
        binding.btnGalleryUpload.setOnClickListener{
            startGallery()
        }
        binding.btnAdd.setOnClickListener {
            if (getFile != null) {
                lifecycleScope.launch {
                    postStory()
                }
            } else {
                showAlert(
                    getString(R.string.post_fail_1),
                    getString(R.string.post_fail_2)
                )
                { }
            }
        }

        // ===== bottom navigation ====
        binding.btnHome.setOnClickListener {
            // Start HomeActivity (or any other activity you want)
            val intent = Intent(this@ScanActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnCamera.setOnClickListener {
            // Start CameraActivity (or any other activity you want)
            // val intent = Intent(this@ScanActivity, ScanActivity::class.java)
            // startActivity(intent)
        }

        binding.btnStatistic.setOnClickListener {
            // Start StatisticActivity (or any other activity you want)
            val intent = Intent(this@ScanActivity, StatisticActivity::class.java)
            startActivity(intent)
        }

        // Menambahkan onClickListener ke ImageView (panah kembali)
        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ScanActivity,
                "com.example.sugarwiseapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)

            binding.ivStory.setImageBitmap(result)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ScanActivity)
            getFile = myFile
            binding.ivStory.setImageURI(selectedImg)
        }
    }

    private fun postStory() {
        val image = convertImage()
        scanViewModel.uploadImage(
            image
        ).observe(this@ScanActivity) { result ->
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
                        postSuccess(result.data.predictions)
                    }
                }
            }
        }
    }

    private fun postSuccess(foodPrediction: Predictions) {
        foodPredict = foodPrediction
        binding.ivStory.setImageResource(R.drawable.placeholder)
        val intent = Intent(this@ScanActivity, ScanResultActivity::class.java)
        intent.putExtra(ScanResultActivity.FOOD_DATA, foodPredict)
        intent.putExtra(ScanResultActivity.PATH, getFile?.absolutePath)
        intent.putExtra(ScanResultActivity.FOOD_ID, foodPrediction.id)
        startActivity(intent)
        finish()
    }

    private fun convertImage(): MultipartBody.Part {
        val file = reduceFileImage(getFile as File)
        val requestImageFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        return MultipartBody.Part.createFormData(
            "file",
            file.name,
            requestImageFile
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun showAlert(
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

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}