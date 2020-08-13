package com.example.hotelmascotas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hotel(
    var uid: String = "",
    var imagen: String = "",
    var nombre: String = "",
    var descripcion: String = "",
    var abierto: Boolean = true
) : Parcelable