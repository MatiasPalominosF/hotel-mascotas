package com.example.hotelmascotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelmascotas.fragments.MisMascotasFragment
import kotlinx.android.synthetic.main.lista_mascotas.view.*

class AdaptadorCustom(context: Context, items:ArrayList<Mascota>): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>(){

    var items:ArrayList<Mascota>?=null

    var viewHolder:ViewHolder? = null

    var inflater:LayoutInflater? = null

    init {
        this.items = items
        this.inflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.nombre?.text = item?.nombre
        holder.foto?.setImageResource(item?.foto!!)
        holder.raza?.text = item?.raza
    }

    override fun getItemCount(): Int {
            return this.items?.count()!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustom.ViewHolder {
        val vista = inflater?.inflate(R.layout.lista_mascotas, parent, false)
        viewHolder = ViewHolder(vista!!)
        return viewHolder!!
    }



    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        var vista = vista

        var foto:ImageView? = null

        var nombre:TextView? = null

        var raza:TextView? = null

        init {
            foto = vista.imagen_mascota
            nombre = vista.nombre_mascota
            raza = vista.raza_mascota
        }

    }


}