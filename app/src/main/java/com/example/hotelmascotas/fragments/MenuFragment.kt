package com.example.hotelmascotas.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.hotelmascotas.LoginActivity
import com.example.hotelmascotas.R
import com.example.hotelmascotas.util.FirestoreUtil
import com.example.hotelmascotas.util.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import java.io.ByteArrayOutputStream


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val RC_SELECT_IMG = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChange = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val btnLogout = view.findViewById<View>(R.id.cerrarSesionFR) as LinearLayout
        val mtextViewUserName = view.findViewById<View>(R.id.textViewUserName) as TextView
        btnLogout.setOnClickListener {
            cerrarSesion()
            finishActivity()
        }
        return view
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SELECT_IMG && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImagePath = data.data
            val selectedImageBmp =
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImagePath)
            val outPutStream = ByteArrayOutputStream()
            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outPutStream)
            selectedImageBytes = outPutStream.toByteArray()

            Glide.with(this)
                .load(selectedImageBytes).into(imageViewMenu)

            pictureJustChange = true
        }
    }*/


    /*override fun onStart() {
        super.onStart()
        FirestoreUtil.getCurrentUser { user ->
            if (this@MenuFragment.isVisible) {
                textViewUserName.setText(user.name)
                if (!pictureJustChange && user.profilePicturePath != null)
                    Glide.with(this).load(StorageUtil.pathToReference(user.profilePicturePath))
                        .into(imageViewMenu)
            }
        }
    }*/

    private fun cerrarSesion() {
        auth.signOut()
        activity?.let {
            val intent = Intent(it, LoginActivity::class.java)
            it.startActivity(intent)
        }
    }

    private fun finishActivity() {
        if (activity != null) {
            activity!!.finish()
        }
    }


    private fun getInfoUser() {


    }


}