package com.example.hotelmascotas.recyclerView.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelmascotas.model.Mascota
import com.example.hotelmascotas.R
import com.example.hotelmascotas.fragments.MisMascotasFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_mis_mascotas.view.*
import kotlinx.android.synthetic.main.lista_mascotas.view.*
import org.jetbrains.anko.activityManager

class AdaptadorCustom(
    private val context: Context,
    private val itemClickListener: OnOrderClickListener,
    private val misMascotasFragment: MisMascotasFragment
) :
    RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    private var dataList = mutableListOf<Mascota>()


    interface OnOrderClickListener {
        fun onOrderClick(mascota: Mascota)
    }

    fun setListData(data: MutableList<Mascota>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.lista_mascotas, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mascota = dataList[position]
        holder.bindView(mascota)
    }

    inner class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        fun bindView(mascota: Mascota) {
            Glide.with(context).load(mascota.foto).centerCrop().into(itemView.imagen_mascota)
            itemView.nombre_mascota.text = mascota.nombre
            itemView.raza_mascota.text = mascota.raza
            itemView.delete_button.setOnClickListener {
                db.collection("pets").document(mascota.id)
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Mascota eliminada",
                            Toast.LENGTH_LONG
                        ).show()
                        misMascotasFragment.findNavController().navigate(R.id.misMascotasFragment)
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Error al eliminar",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            itemView.setOnClickListener {
                itemClickListener.onOrderClick(mascota)
            }
        }
    }


}