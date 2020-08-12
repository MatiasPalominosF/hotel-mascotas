package com.example.hotelmascotas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var password: String? = null
) : Parcelable