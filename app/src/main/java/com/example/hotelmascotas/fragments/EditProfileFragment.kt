package com.example.hotelmascotas.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.hotelmascotas.Dashboard
import com.example.hotelmascotas.LoginActivity
import com.example.hotelmascotas.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var menuFragment: MenuFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)
        //Inicialización parámetros firebase.
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()



        //Argumentos obtenidos del fragmento MenuFragment con los datos del usuario.
        val name = this.arguments!!.getString("name")
        val lastname = this.arguments!!.getString("lastname")
        val email = this.arguments!!.getString("email")
        val password = this.arguments!!.getString("password")

        //Inicialización fragmentos.
        menuFragment = MenuFragment()
        val manager = fragmentManager

        //Inicialización de los parámetros obtenidos del XML de este fragmento.
        val mEditTextUserName = view.findViewById<View>(R.id.txtName) as EditText
        val mEditTextLastName = view.findViewById<View>(R.id.txtLastName) as EditText
        val mEditTextEmail = view.findViewById<View>(R.id.txtEmail) as EditText
        val mEditTextPassword = view.findViewById<View>(R.id.txtPassword) as EditText
        val mButtonAccept = view.findViewById<View>(R.id.buttonAccept) as Button

        añadirDatosAlXml(
            name,
            lastname,
            email,
            password,
            mEditTextUserName,
            mEditTextLastName,
            mEditTextEmail,
            mEditTextPassword
        )

        println("Valores: " + mEditTextUserName.text.toString() + mEditTextLastName.text.toString() + mEditTextEmail.text.toString() + mEditTextPassword.text.toString())

        mButtonAccept.setOnClickListener {
            if (manager != null) {
                editProfile(
                    mEditTextUserName.text.toString(),
                    mEditTextLastName.text.toString(),
                    mEditTextPassword.text.toString(),
                    manager,
                    menuFragment
                )

            }

        }

        // Inflate the layout for this fragment
        return view
    }


    private fun action() {
        activity?.let {
            val intent = Intent(it, Dashboard::class.java)
            it.startActivity(intent)
        }
    }

    private fun finishActivity() {
        if (activity != null) {
            activity!!.finish()
        }
    }

    /**
     * Función que edita un perfil.
     */
    private fun editProfile(
        name: String,
        lastName: String,
        password: String,
        manager: FragmentManager,
        fragment: Fragment
    ) {


        //Se obtiene la referencia de la base de datos.
        val docRef = db.collection("users").document(auth.uid.toString())

        docRef.update("name", name, "lastname", lastName, "password", password)
            .addOnSuccessListener {
                Toast.makeText(activity, "Usuario editado correctamente!", Toast.LENGTH_SHORT)
                    .show();
                //manager.beginTransaction().add(fragment, "MenuFragment").addToBackStack(null)
                //.commit()
                println("manager.backStackEntryCount: " + manager.backStackEntryCount)
                if (manager.backStackEntryCount > 0) {
                    println(manager.popBackStackImmediate())
                    manager.popBackStack()

                } else {
                    println("FALLÓ EL FRAGMENT DEL EDITPROFILEFRAGMENT")
                }
            }.addOnFailureListener {
                Toast.makeText(activity, "Error al editar el usuario", Toast.LENGTH_SHORT).show();
            }


    }


    /**
     * Función encargada de setear los datos en el XML del EditProfileFragment.
     */
    private fun añadirDatosAlXml(
        name: String?,
        lastname: String?,
        email: String?,
        password: String?,
        mEditTextUserName: EditText,
        mEditTextLastName: EditText,
        mEditTextEmail: EditText,
        mEditTextPassword: EditText
    ) {

        mEditTextUserName.setText(name)
        mEditTextLastName.setText(lastname)
        mEditTextEmail.setText(email)
        mEditTextPassword.setText(password)
    }


    /**
     * Función que nos redirige a otra ventana.
     */


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}