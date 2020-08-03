package com.example.hotelmascotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.hotelmascotas.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.NullPointerException

class RegisterActivity : AppCompatActivity() {

    //Atributos para obtener datos del xml.
    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private val users = hashMapOf<String?, Any?>()

    private lateinit var db: FirebaseFirestore

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)

        db = FirebaseFirestore.getInstance()
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        auth = FirebaseAuth.getInstance()

    }

    fun register(view: View) {
        createNewAccount2()
    }

    private fun createNewAccount2() {

        val name: String = txtName.text.toString()
        val lastName: String = txtLastName.text.toString()
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()
        users.put("name", name)
        users.put("lastname", lastName)
        users.put("email", email)
        users.put("password", password)


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(
                password
            )
        ) {
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isComplete) {
                        //Envía mensaje de verificación al correo
                        val user: FirebaseUser? = auth.currentUser
                        println("USUARIO!!!!!!!!!!!!!!!: " + user?.uid.toString())
                        verifyEmail(user)

                        var uidUser = user?.uid.toString()
                        val usersDB = db.collection("users")
                        usersDB.document(uidUser).set(users).addOnSuccessListener {
                            Toast.makeText(this, "Usuario agregado correctamente", Toast.LENGTH_LONG).show()
                            action()
                        }.addOnFailureListener {
                            println("IIIIIIIIIIIIIIIIIIIIT2: " + it)
                        }

                        // Add a new document with a generated ID
                        /*db.collection("users")
                            .add(users)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    "Exito",
                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                )
                                action()
                            }
                            .addOnFailureListener { e ->
                                Log.w("Error", "Error adding document", e)
                            }*/
                    }
                }
        }

    }

    private fun action() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    //Función que envía un email de verificación al usuario.
    private fun verifyEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            if (task.isComplete) {
                Toast.makeText(this, "E-mail enviado correctamente", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Error al enviar el e-mail", Toast.LENGTH_LONG).show()
            }
        }

    }
}
