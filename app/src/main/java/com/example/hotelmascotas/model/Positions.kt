package com.example.hotelmascotas.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Positions(
    var latitude: String = "",
    var longitude: String = ""
) : Parcelable