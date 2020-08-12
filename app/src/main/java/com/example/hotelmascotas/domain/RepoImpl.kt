package com.example.hotelmascotas.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotelmascotas.Mascota
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
}