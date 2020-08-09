package com.example.hotelmascotas.recyclerView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hotelmascotas.Mascota
import com.example.hotelmascotas.domain.RepoImpl

class MainViewModel: ViewModel() {
    private val repo = RepoImpl()

    fun fetchDataMascotas(): LiveData<MutableList<Mascota>> {
        val mutableData = MutableLiveData<MutableList<Mascota>>()
        repo.getDataMascota().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}