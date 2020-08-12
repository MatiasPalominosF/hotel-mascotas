package com.example.hotelmascotas.recyclerView.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelmascotas.Mascota
import com.example.hotelmascotas.R
import kotlinx.android.synthetic.main.lista_mascotas.view.*

class AdaptadorCustom(
    private val context: Context,
    private val itemClickListener: OnOrderClickListener
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
        fun bindView(mascota: Mascota) {
            Glide.with(context).load(mascota.foto).centerCrop().into(itemView.imagen_mascota)
            itemView.nombre_mascota.text = mascota.nombre
            itemView.raza_mascota.text = mascota.raza
            itemView.setOnClickListener {
                itemClickListener.onOrderClick(mascota)
            }
        }
    }


}