package com.example.hotelmascotas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmascotas.AdaptadorCustom
import com.example.hotelmascotas.Mascota
import com.example.hotelmascotas.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MisMascotasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MisMascotasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var adaptador: AdaptadorCustom? = null
    private var recyclerViewM:RecyclerView? = null
    var mascotas:ArrayList<Mascota>? = null
    private var layoutManager:RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        this.recyclerViewM = view?.findViewById(R.id.recyclerViewMascotas)
        mascotas = ArrayList()
        mascotas?.add(Mascota("Mascota 1", R.drawable.ic_launcher_background, "Raza 1"))
        mascotas?.add(Mascota("Mascota 2", R.drawable.ic_launcher_background, "Raza 2"))
        mascotas?.add(Mascota("Mascota 2", R.drawable.ic_launcher_background, "Raza 2"))
        mascotas?.add(Mascota("Mascota 2", R.drawable.ic_launcher_background, "Raza 2"))
        mascotas?.add(Mascota("Mascota 2", R.drawable.ic_launcher_background, "Raza 2"))
        mascotas?.add(Mascota("Mascota 2", R.drawable.ic_launcher_background, "Raza 2"))


        this.adaptador = AdaptadorCustom(requireContext(), mascotas!!)
        recyclerViewM?.layoutManager = layoutManager
        recyclerViewM?.adapter = adaptador

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_mascotas, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MisMascotasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MisMascotasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}