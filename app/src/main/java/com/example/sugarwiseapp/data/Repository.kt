package com.example.sugarwiseapp.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.sugarwiseapp.data.response.AllFoodResponse
import com.example.sugarwiseapp.data.response.DailyFoodResponse
import com.example.sugarwiseapp.data.response.FoodResponse
import com.example.sugarwiseapp.data.response.LoginResponse
import com.example.sugarwiseapp.data.response.RegisterResponse
import com.example.sugarwiseapp.data.response.UploadFoodResponse
import com.example.sugarwiseapp.data.response.UserResponse
import com.example.sugarwiseapp.data.retrofit.ApiConfig
import com.example.sugarwiseapp.data.retrofit.ApiConfigPredict
import com.example.sugarwiseapp.data.retrofit.ApiService
import com.example.sugarwiseapp.data.retrofit.ApiServicePredict
import com.example.sugarwiseapp.view.login.LoginPreferences
import okhttp3.MultipartBody

class Repository (
    application: Application,
    private val loginPreferences : LoginPreferences
) {
    private val apiService: ApiService = ApiConfig.getApiService()
    private val apiServicePredict: ApiServicePredict = ApiConfigPredict.getApiService()

    fun register(
        name: String,
        email: String,
        password: String,
        gender: String,
        age: Int,
        height: Int,
        weight: Int,
        bloodSugar: Float
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(
                name,
                email,
                password,
                gender,
                age,
                height,
                weight,
                bloodSugar
            )
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(
                email,
                password
            )
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUser(): LiveData<Result<UserResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.getUser(
                userId = "${loginPreferences.getUser().userId}",
                token = "Bearer ${loginPreferences.getUser().token}"
            )
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun uploadImage(
        imageFile : MultipartBody.Part
    ): LiveData<Result<FoodResponse>> = liveData{
        emit(Result.Loading)
//        try {
            val response = apiServicePredict.uploadFood(
                file = imageFile
            )
            emit(Result.Success(response))
//        } catch (e: Exception) {
//            emit(Result.Error(e.message.toString()))
//        }
    }

    fun uploadFood(
        foodId: String,
        amount: Int
    ): LiveData<Result<UploadFoodResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadToDaily(
                userId = "${loginPreferences.getUser().userId}",
                foodId = foodId,
                amount = amount,
                token = "Bearer ${loginPreferences.getUser().token}"
            )
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDailyFood(): LiveData<Result<DailyFoodResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDailyFood(
                userId = "${loginPreferences.getUser().userId}",
                token = "Bearer ${loginPreferences.getUser().token}"
            )

            if (response.data.isNotEmpty()) {
                emit(Result.Success(response))
            } else {
                emit(Result.Error(response.message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllFood(): LiveData<Result<AllFoodResponse>> = liveData {
        emit(Result.Loading)
//        try {
        val response = apiService.getAllFood(
            token = "Bearer ${loginPreferences.getUser().token}"
        )
        emit(Result.Success(response))
//        } catch (e: Exception) {
//            emit(Result.Error(e.message.toString()))
//        }
    }
}