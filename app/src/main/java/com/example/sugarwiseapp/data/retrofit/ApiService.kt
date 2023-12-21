package com.example.sugarwiseapp.data.retrofit

import com.example.sugarwiseapp.data.response.AllFoodResponse
import com.example.sugarwiseapp.data.response.DailyFoodResponse
import com.example.sugarwiseapp.data.response.FoodResponse
import com.example.sugarwiseapp.data.response.LoginResponse
import com.example.sugarwiseapp.data.response.RegisterResponse
import com.example.sugarwiseapp.data.response.UploadFoodResponse
import com.example.sugarwiseapp.data.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String,
        @Field("age") age: Int,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("blood_sugar") bloodSugar: Float
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String,
        @Header("Authorization") token: String
    ): UserResponse

    @FormUrlEncoded
    @POST("daily-food")
    suspend fun uploadToDaily(
        @Field("userId") userId: String,
        @Field("foodId") foodId: String,
        @Field("amount") amount: Int,
        @Header("Authorization") token: String
    ): UploadFoodResponse

    @GET("daily-food/{userId}")
    suspend fun getDailyFood(
        @Path("userId") userId: String,
        @Header("Authorization") token: String
    ): DailyFoodResponse

    @GET("food")
    suspend fun getAllFood(
        @Header("Authorization") token: String
    ): AllFoodResponse
}