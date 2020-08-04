package com.example.hotelmascotas.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.hotelmascotas.LoginActivity
import com.example.hotelmascotas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "lastname"
private const val ARG_PARAM3 = "email"
private const val ARG_PARAM4 = "password"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123
    private lateinit var bundle: Bundle
    private lateinit var editProfileFragment: EditProfileFragment

    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var lastname: String? = null
    private var email: String? = null
    private var password: String? = null
    private val RC_SELECT_IMG = 2
    private lateinit var selectedImageBytes: ByteArray
    private var pictureJustChange = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM1)
            lastname = it.getString(ARG_PARAM2)
            email = it.getString(ARG_PARAM3)
            password = it.getString(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val name = this.arguments!!.getString("name")
        val lastname = this.arguments!!.getString("lastname")
        val email = this.arguments!!.getString("email")
        val password = this.arguments!!.getString("password")

        //Atributos para pasar datos entre fragmentos
        bundle = Bundle()
        editProfileFragment = EditProfileFragment()
        val manager = fragmentManager
        //Fin atributos

        auth = FirebaseAuth.getInstance()

        val btnLogout = view.findViewById<View>(R.id.cerrarSesionFR) as LinearLayout
        val mtextViewUserName = view.findViewById<View>(R.id.textViewUserName) as TextView
        val btnEditProfile = view.findViewById<View>(R.id.edit_profile) as LinearLayout

        btnEditProfile.setOnClickListener {
            //TODO: Insert function that edit profile.
            if (name != null && lastname != null && email != null && password != null && manager != null) {
                editProfile(name, lastname, email, password, editProfileFragment, manager)
            }
        }


        mtextViewUserName.text = "$name $lastname"
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @param lastname Parameter 2.
         * @param email Parameter 3.
         * @param password Parameter 4.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String, lastname: String, email: String, password: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                    putString(ARG_PARAM2, lastname)
                    putString(ARG_PARAM3, email)
                    putString(ARG_PARAM4, password)
                }
            }
    }


    private fun editProfile(
        name: String,
        lastname: String,
        email: String,
        password: String,
        fragment: Fragment,
        manager: FragmentManager
    ) {
        println("UID en profile: " + auth.uid)

        bundle.putString("name", name)
        bundle.putString("lastname", lastname)
        bundle.putString("email", email)
        bundle.putString("password", password)

        fragment.arguments = bundle

        println("Cosas en el fragment: " + fragment.arguments)

        val frag_transaction = manager.beginTransaction()
        //frag_transaction.replace(R.id.linear_profile_view, fragment)
        frag_transaction.apply {
            replace(R.id.linear_profile_view, fragment)
            commit()
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
        if (activity != null) {
            activity!!.finish()
        }
    }


    private fun getInfoUser() {


    }


}