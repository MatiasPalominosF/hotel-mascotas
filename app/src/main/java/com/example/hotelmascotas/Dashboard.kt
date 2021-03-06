package com.example.hotelmascotas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotelmascotas.fragments.HomeFragment
import com.example.hotelmascotas.fragments.MenuFragment
import com.example.hotelmascotas.fragments.MisMascotasFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dashboard.*


class Dashboard : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var menuFragment: MenuFragment
    private lateinit var misMascotasFragment: MisMascotasFragment
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        homeFragment = HomeFragment()
        menuFragment = MenuFragment()
        misMascotasFragment = MisMascotasFragment()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        bundle = Bundle()

        val navController: NavController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView.setupWithNavController(navController)

    }


    @SuppressLint("RestrictedApi")
    private fun pruebas() {
        val docRef = db.collection("users").document(auth.uid.toString())

        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val document: DocumentSnapshot? = it.result
                if (document!!.exists()) {
                    println("NOMBRE: " + document.get("name").toString())
                    println("Apellido: " + document.get("lastname").toString())
                    println("Email: " + document.get("email").toString())
                    println("Contraseña: " + document.get("password").toString())
                    bundle.putString("name:", document.get("name").toString())
                    bundle.putString("lastname:", document.get("lastname").toString())
                    bundle.putString("email:", document.get("email").toString())
                    bundle.putString("password:", document.get("password").toString())

                    println("ESTE ES EL BUNDLE: " + bundle)

                }

            }
        }
        println("UID DEL USUARIO INGRESADO!!!!!!!!!!!!!!: " + auth.uid)

    }

    private fun makeCurrentFragmentWithInfo(fragment: Fragment) {

        // val fragmentManager: FragmentManager = supportFragmentManager
        //val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val docRef = db.collection("users").document(auth.uid.toString())
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val document: DocumentSnapshot? = it.result
                if (document!!.exists()) {
                    bundle.putString("name", document.get("name").toString())
                    bundle.putString("lastname", document.get("lastname").toString())
                    bundle.putString("email", document.get("email").toString())
                    bundle.putString("password", document.get("password").toString())

                    fragment.arguments = bundle


                }

            }
        }
    }
}

