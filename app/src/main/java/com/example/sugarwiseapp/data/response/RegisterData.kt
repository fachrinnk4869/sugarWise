package com.example.sugarwiseapp.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterData(
    var password: String? = null,
    var gender: String? = null,
    var name: String? = null,
    var weight: Int? = null,
    var bloodSugar: Float? = null,
    var email: String? = null,
    var age: Int? = null,
    var height: Int? = null,
) : Parcelable