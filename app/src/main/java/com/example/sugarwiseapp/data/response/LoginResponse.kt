package com.example.sugarwiseapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginDataResponse,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class LoginDataResponse(

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String
): Parcelable
