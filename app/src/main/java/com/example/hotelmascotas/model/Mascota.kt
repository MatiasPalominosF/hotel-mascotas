package com.example.hotelmascotas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mascota(
    val id: String = "",
    val nombre: String = "",
    val foto: String = "",
    val raza: String = ""
) : Parcelable
