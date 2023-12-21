package com.example.sugarwiseapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginData(
    val userId: String? = null,
    val email: String? = null,
    val token: String? = null
): Parcelable