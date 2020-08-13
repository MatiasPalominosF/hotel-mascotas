@file:Suppress("DEPRECATION")

package com.example.hotelmascotas.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hotelmascotas.R
import com.example.hotelmascotas.model.Positions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_tomar_alojamiento.*


class TomarAlojamientoFragment : Fragment() {

    private lateinit var positions: Positions
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val datos = hashMapOf<String?, Any?>()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tomar_alojamiento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //-34.9779074 y -71.2380107

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        mapa.setOnClickListener {
            findNavController().navigate(R.id.mapsActivity)
        }

        btnAceptar.setOnClickListener {
            datos["nombre"] = txtname.text.toString()
            datos["raza"] = txtRaza.text.toString()
            datos["latitud"] = "-34.9779074"
            datos["longitud"] = "-71.2380107"
            datos["activo"] = true
            datos["dueno"] = auth.uid.toString()

            this.progressDialog = ProgressDialog(requireContext())
            this.progressDialog.show()
            this.progressDialog.setContentView(R.layout.progress_dialog)
            this.progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            db.collection("alojamientos").add(datos).addOnSuccessListener {
                Toast.makeText(requireContext(), "Se ha agregado correctamente", Toast.LENGTH_LONG)
                    .show()
                this.progressDialog.dismiss()
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}