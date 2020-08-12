@file:Suppress("DEPRECATION")

package com.example.hotelmascotas.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelmascotas.LoginActivity
import com.example.hotelmascotas.R
import com.example.hotelmascotas.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.progressDialog = ProgressDialog(requireContext())
        this.progressDialog.show()
        this.progressDialog.setContentView(R.layout.progress_dialog)
        this.progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.auth = FirebaseAuth.getInstance()
        this.db = FirebaseFirestore.getInstance()

        cargarDatos()
        cerrarSesionFR.setOnClickListener {
            cerrarSesion()
            finishActivity()
        }

        edit_profile.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
    }

    private fun cargarDatos() {
        val docRef = db.collection("users").document(auth.uid.toString())
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val document: DocumentSnapshot? = it.result
                if (document!!.exists()) {
                    val name = document.getString("name")
                    val lastname = document.getString("lastname")

                    textViewUserName.text = "$name $lastname"
                    this.progressDialog.dismiss()
                }
            }
        }

    }

    private fun cerrarSesion() {
        auth.signOut()
        activity?.let {
            val intent = Intent(it, LoginActivity::class.java)
            it.startActivity(intent)
        }
    }

    private fun finishActivity() {
        activity?.finish()
    }
}