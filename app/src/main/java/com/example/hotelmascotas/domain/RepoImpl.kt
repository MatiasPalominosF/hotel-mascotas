package com.example.hotelmascotas.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotelmascotas.Mascota
import com.example.hotelmascotas.model.Hotel
import com.google.firebase.firestore.FirebaseFirestore

class RepoImpl : Repo {
    override fun getDataMascota(): LiveData<MutableList<Mascota>> {
        val mutableData = MutableLiveData<MutableList<Mascota>>()
        FirebaseFirestore.getInstance().collection("pets").get().addOnSuccessListener { result ->
            val listData = mutableListOf<Mascota>()
            Log.d("RESULTA", "$result")
            for (document in result) {
                val image = document.getString("foto")
                val name = document.getString("nombre")
                val raza = document.getString("raza")
                val mascota = Mascota(name!!, image!!, raza!!)
                Log.d("Mascota", "$mascota")
                listData.add(mascota)
            }
            mutableData.value = listData
        }
        return mutableData
    }

    override fun getDataHoteles(): LiveData<MutableList<Hotel>> {
        val mutableData = MutableLiveData<MutableList<Hotel>>()
        FirebaseFirestore.getInstance().collection("hoteles").get()
            .addOnSuccessListener { result ->
                val listData = mutableListOf<Hotel>()
                for (document in result) {

                    Log.d("RESULTA", "${document.data}")
                    val uid = document.id
                    val imagen = document.getString("imagen")
                    val nombre = document.getString("nombre")
                    val descripcion = document.getString("descripcion")
                    val abierto = document.getBoolean("abierto")

                    println("DATOS: $uid $imagen $nombre $descripcion $abierto")

                    val hotel =
                        Hotel(uid!!, imagen!!, nombre!!, descripcion!!, abierto!!)
                    listData.add(hotel)
                }
                mutableData.value = listData
            }.addOnFailureListener {
                Log.d("Exception", "${it.toString()}")
            }
        return mutableData
    }
}