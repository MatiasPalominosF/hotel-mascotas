package com.example.hotelmascotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class InicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

    }

    private fun direccionMenu ()
    {
        action()
    }

    private fun action() {
        startActivity(Intent(this, MasOpciones2::class.java))
    }
}