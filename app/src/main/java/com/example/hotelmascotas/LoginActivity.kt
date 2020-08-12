@file:Suppress("DEPRECATION")

package com.example.hotelmascotas

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //Atributos para obtener datos del xml.
    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)

        auth = FirebaseAuth.getInstance()
    }

    /**
     * Función onclick de olvidaste la contraseña.
     */
    fun forgotPassword(view: View) {
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }

    /**
     * Función onclick de register.
     */
    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    /**
     * Función onclick de login.
     */
    fun login(view: View) {
        loginUser()
    }

    /**
     * Método que inicia la sesión de un usuario.
     */
    private fun loginUser() {
        val user: String = txtUser.text.toString()
        val password: String = txtPassword.text.toString()

        this.progressDialog = ProgressDialog(this)
        this.progressDialog.show()
        this.progressDialog.setContentView(R.layout.progress_dialog)
        this.progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        println("Es válido: " + isValidEmail(user))
        if (isValidEmail(user)) {
            if (validarCamposInicioSesion(user, password)) {
                auth.signInWithEmailAndPassword(user, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            action()
                            this.progressDialog.dismiss()
                        } else {
                            this.progressDialog.dismiss()
                            Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            } else {
                this.progressDialog.dismiss()

            }
        } else {
            this.progressDialog.dismiss()
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG)
                .show()

        }
    }

    /**
     * Método que verifica que el usuario ingresó todos los campos de la ventana login.
     */
    private fun validarCamposInicioSesion(
        correo: String,
        contrasena: String
    ): Boolean {
        if (contrasena.isEmpty()) {
            this.txtPassword.setError("Campo requerido")
            this.txtPassword.requestFocus()
            return false
        } else if (correo.isEmpty()) {
            this.txtUser.setError("Campo requerido")
            this.txtUser.requestFocus()
            return false
        }
        return true
    }

    private fun action() {
        startActivity(Intent(this, Dashboard::class.java))
        finish()
    }

    /**
     * Método que valida si el email ingresado por el usuario es válido (se utiliza la clase
     * Pattern de Java).
     */
    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    /**
     * Método utilizado para que verifique si el usuario ya está logeado, si es true, lo manda
     * directo a la pantalla dashboard.
     */
    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            action()

        }
    }

/*override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.getItemId()) {
        android.R.id.home -> {
            println("volver")
            val i = Intent(baseContext, RegisterActivity::class.java)
            startActivity(i)
            //onBackPressed();
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}*/
}
