package com.example.sugarwiseapp.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: RegisterDataResponse,

	@field:SerializedName("message")
	val message: String
)

data class CreatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)

data class UpdatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)

data class RegisterDataResponse(

	@field:SerializedName("data")
	val data: RegisterDataResponse,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("createdAt")
	val createdAt: CreatedAt,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("weight")
	val weight: Int,

	@field:SerializedName("blood_sugar")
	val bloodSugar: Any,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("token")
	val token: Any,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: UpdatedAt
)
