package com.example.hotelmascotas

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mascota(
    val nombre: String = "",
    val foto: String = "",
    val raza: String = ""
) : Parcelable
