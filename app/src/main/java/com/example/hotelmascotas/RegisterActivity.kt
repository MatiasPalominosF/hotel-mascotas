package com.example.hotelmascotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    //Atributos para obtener datos del xml.
    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar

    //Atributos de Firebase.
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        //Se obtiene la referencia de la BD para escribir en ella.
        dbReference = database.reference.child("User")
    }

    fun register(view: View) {
        createNewAccount()
    }

    //Función que crear una nueva cuenta.
    private fun createNewAccount() {
        val name: String = txtName.text.toString()
        val lastName: String = txtLastName.text.toString()
        val email: String = txtEmail.text.toString()
        val password: String = txtPassword.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isComplete) {
                        val user: FirebaseUser? = auth.currentUser
                        verifyEmail(user)

                        val userBD = dbReference.child(user?.uid!!)

                        println("La referencia de la BD es: " + userBD.database.getReference())
                        userBD.child("Name").setValue(name)
                        userBD.child("LastName").setValue(lastName)
                        println("Nombre: " + name)
                        println("Apellido: " + lastName)
                        println("esto imprimió " + userBD.child("Name").setValue(name))
                        println("esto imprimió " + userBD.child("LastName").setValue(lastName))
                        action()
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
