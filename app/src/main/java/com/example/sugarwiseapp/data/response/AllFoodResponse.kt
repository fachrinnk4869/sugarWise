package com.example.sugarwiseapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AllFoodResponse(

	@field:SerializedName("data")
	val data: ArrayList<DataItemAllFood>,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class DataAllFood(

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

	@field:SerializedName("calories")
	val calories: Float
): Parcelable

@Parcelize
data class DataItemAllFood(

	@field:SerializedName("data")
	val data: DataAllFood,

	@field:SerializedName("id")
	val id: String
): Parcelable
