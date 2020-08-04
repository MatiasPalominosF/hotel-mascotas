package com.example.hotelmascotas

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
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 1

    private val signInProviders = listOf(
        AuthUI.IdpConfig.EmailBuilder()
            .setAllowNewAccounts(true).setRequireName(true).build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /*registerBtn.setOnClickListener {
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders).build()
            startActivityForResult(intent, RC_SIGN_IN)
        }*/


        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar)
        auth = FirebaseAuth.getInstance()
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val progressDialog = indeterminateProgressDialog("Registra tu cuenta")
                FirestoreUtil.initCurrentUserIfFirstTime {
                    startActivity(intentFor<Dashboard>().newTask().clearTask())
                    progressDialog.dismiss()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) return

                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK -> Toast.makeText(
                        this,
                        "Sin conexión a internet",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    ErrorCodes.UNKNOWN_ERROR -> Toast.makeText(
                        this,
                        "Error desconocido",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

            }
        }
    }*/

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


        println("Es válido: " + isValidEmail(user))
        if (isValidEmail(user)) {
            if (validarCamposInicioSesion(user, password)) {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(user, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            action()
                        } else {
                            progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        } else {
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
