package com.example.hotelmascotas.domain

import androidx.lifecycle.LiveData
import com.example.hotelmascotas.Mascota

interface Repo {
    fun getDataMascota(): LiveData<MutableList<Mascota>>
}