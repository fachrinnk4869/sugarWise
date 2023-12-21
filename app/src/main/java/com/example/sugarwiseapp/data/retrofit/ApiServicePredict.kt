package com.example.sugarwiseapp.data.retrofit

import com.example.sugarwiseapp.data.response.FoodResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServicePredict {
    @Multipart
    @POST("predict")
    suspend fun uploadFood(
        @Part file: MultipartBody.Part,
    ): FoodResponse
}