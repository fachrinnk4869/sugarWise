package com.example.sugarwiseapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyFoodResponse(

	@field:SerializedName("data")
	val data: ArrayList<DataItem>,

	@field:SerializedName("message")
	val message: String
): Parcelable

@Parcelize
data class DataFoodDaily(

	@field:SerializedName("date")
	val date: DateFood,

	@field:SerializedName("food_name")
	val foodName: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("total_fat")
	val totalFat: Float,

	@field:SerializedName("total_protein")
	val totalProtein: Float,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("foodId")
	val foodId: String,

	@field:SerializedName("total_calorie")
	val totalCalorie: Int,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("total_sugar")
	val totalSugar: Float,

	@field:SerializedName("id_food")
	val idFood: String
): Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("id_dailyfood")
	val idDailyfood: String,

	@field:SerializedName("data")
	val data: DataFoodDaily
): Parcelable

@Parcelize
data class DateFood(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
): Parcelable
