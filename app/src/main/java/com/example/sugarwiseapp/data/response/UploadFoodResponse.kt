package com.example.sugarwiseapp.data.response

import com.google.gson.annotations.SerializedName

data class UploadFoodResponse(

	@field:SerializedName("data")
	val data: DataFood,

	@field:SerializedName("message")
	val message: String
)

data class Date(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)

data class DataFood(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("date")
	val date: Date,

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("total_fat")
	val totalFat: Any,

	@field:SerializedName("total_protein")
	val totalProtein: Any,

	@field:SerializedName("foodId")
	val foodId: String,

	@field:SerializedName("total_calorie")
	val totalCalorie: Int,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("total_carbo")
	val totalCarbo: Int
)
