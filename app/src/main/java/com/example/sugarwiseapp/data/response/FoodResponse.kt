package com.example.sugarwiseapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FoodResponse(

	@field:SerializedName("predictions")
	val predictions: Predictions
)

@Parcelize
data class Predictions(

	@field:SerializedName("food_name")
	val foodName: String,

	@field:SerializedName("total_fat")
	val totalFat: Float,

	@field:SerializedName("sugars")
	val sugars: Float,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("protein")
	val protein: Float,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("calories")
	val calories: Int
): Parcelable
