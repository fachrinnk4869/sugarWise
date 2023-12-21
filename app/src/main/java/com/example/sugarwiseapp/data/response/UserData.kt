package com.example.sugarwiseapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData (
        val password: String? = null,
        val gender: String? = null,
        val name: String? = null,
        val weight: Int,
        val height: Int,
        val bloodSugar: String? = null,
        val email: String? = null,
        val age: Int
):Parcelable