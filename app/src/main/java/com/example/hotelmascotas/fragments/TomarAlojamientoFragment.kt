package com.example.hotelmascotas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotelmascotas.R
import kotlinx.android.synthetic.main.fragment_tomar_alojamiento.*

class TomarAlojamientoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tomar_alojamiento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        botonPrueba.setOnClickListener {
            findNavController().navigate(R.id.mapsActivity)
        }
    }
}