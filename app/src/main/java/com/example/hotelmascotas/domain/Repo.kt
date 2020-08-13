package com.example.hotelmascotas.domain

import androidx.lifecycle.LiveData
import com.example.hotelmascotas.model.Mascota
import com.example.hotelmascotas.model.Hotel

interface Repo {
    fun getDataMascota(): LiveData<MutableList<Mascota>>
    fun getDataHoteles(): LiveData<MutableList<Hotel>>
}