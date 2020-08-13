@file:Suppress("DEPRECATION")

package com.example.hotelmascotas.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hotelmascotas.R
import com.example.hotelmascotas.model.Mascota
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_resgistrar_mascota.*


class ResgistrarMascotaFragment : Fragment() {

    private lateinit var db: FirebaseFirestore
    private val mascotas = hashMapOf<String?, Any?>()
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resgistrar_mascota, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        button_camera.setOnClickListener {

        }

        button_galery.setOnClickListener {

        }

        buttonAccept.setOnClickListener {
            registrarMascota()
        }
    }

    fun registrarMascota() {
        val nombre: String = nombre_mascota.text.toString()
        val raza: String = raza_mascota.text.toString()
        val imagen: String =
            "https://image.freepik.com/vector-gratis/dibujos-animados-mascota-perro-lindo_97365-539.jpg"
        mascotas.put("foto", imagen)
        mascotas.put("nombre", nombre)
        mascotas.put("raza", raza)

        this.progressDialog = ProgressDialog(requireContext())
        this.progressDialog.show()
        this.progressDialog.setContentView(R.layout.progress_dialog)
        this.progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        db.collection("pets")
            .add(mascotas)
            .addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Mascota agregada correctamente",
                    Toast.LENGTH_LONG
                ).show()
                this.progressDialog.dismiss()
                findNavController().navigate(R.id.misMascotasFragment)
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "Error ${it.toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }

    }

}