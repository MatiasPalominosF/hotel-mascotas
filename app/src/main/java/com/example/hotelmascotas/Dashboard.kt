package com.example.hotelmascotas

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.hotelmascotas.fragments.HomeFragment
import com.example.hotelmascotas.fragments.MenuFragment
import com.example.hotelmascotas.fragments.MisMacotasFragment
import com.example.hotelmascotas.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment
    lateinit var menuFragment: MenuFragment
    lateinit var misMascotasFragment: MisMacotasFragment
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        homeFragment = HomeFragment()
        menuFragment = MenuFragment()
        misMascotasFragment = MisMacotasFragment()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        makeCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigationHome -> makeCurrentFragment(homeFragment)
                R.id.navigationMascotas -> makeCurrentFragment(misMascotasFragment)
                R.id.navigationMenu -> makeCurrentFragment(menuFragment)
                R.id.navigationPedidos -> pruebas()
            }
            true
        }
    }

    private fun pruebas() {
        val docRef = db.collection("users").document(auth?.uid.toString())

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    println("CONTENIDO DOCUMENTO!!!!!!!!!!!!: " + document.data)
                } else {
                    println("EL DOCUMENTO NO EXISTE!!!!!!!!!!!!!1")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error", "get failed with ", exception)
            }


        println("UID DEL USUARIO INGRESADO!!!!!!!!!!!!!!: " + auth.uid)

    }


    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}
