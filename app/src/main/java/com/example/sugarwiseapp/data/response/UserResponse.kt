package com.example.sugarwiseapp.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("id")
	val id: String
)

data class UserUpdatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: UserCreatedAt,

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

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("updatedAt")
	val updatedAt: UserUpdatedAt
)

data class UserCreatedAt(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)
