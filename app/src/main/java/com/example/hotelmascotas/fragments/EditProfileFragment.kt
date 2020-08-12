@file:Suppress("DEPRECATION")

package com.example.hotelmascotas.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelmascotas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile_edit.*

class EditProfileFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_profile_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inicialización parámetros firebase.
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        traerDatosBD()

        buttonAccept.setOnClickListener {
            editProfile(txtName.text.toString(), txtLastName.text.toString())
        }

    }

    /**
     * Función que edita un perfil.
     */
    private fun editProfile(
        name: String,
        lastName: String
    ) {
        this.progressDialog = ProgressDialog(requireContext())
        this.progressDialog.show()
        this.progressDialog.setContentView(R.layout.progress_dialog)
        this.progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        //Se obtiene la referencia de la base de datos.
        val docRef = db.collection("users").document(auth.uid.toString())
        docRef.update("name", name, "lastname", lastName)
            .addOnSuccessListener {
                this.progressDialog.dismiss()
                findNavController().navigate(R.id.menuFragment)
                Toast.makeText(activity, "Usuario editado correctamente!", Toast.LENGTH_SHORT)
                    .show()
            }.addOnFailureListener {
                Toast.makeText(activity, "Error al editar el usuario", Toast.LENGTH_SHORT).show();
            }
    }

    private fun traerDatosBD() {
        val docRef = db.collection("users").document(auth.uid.toString())
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val document: DocumentSnapshot? = it.result
                if (document!!.exists()) {
                    val name = document.getString("name")
                    val lastname = document.getString("lastname")
                    val email = document.getString("email")
                    val password = document.getString("password")

                    txtName.setText(name)
                    txtLastName.setText(lastname)
                    txtEmail.setText(email)
                    txtPassword.setText(password)
                    this.progressDialog.dismiss()
                }
            }
        }
    }
}