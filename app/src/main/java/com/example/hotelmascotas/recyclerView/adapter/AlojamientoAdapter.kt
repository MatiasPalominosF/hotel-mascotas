package com.example.hotelmascotas.recyclerView.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hotelmascotas.R
import com.example.hotelmascotas.model.Hotel
import kotlinx.android.synthetic.main.order_row_alojamiento.view.*


class AlojamientoAdapter(
    private val context: Context,
    private val itemClickListener: OnPasteleriaClickListener
) :
    RecyclerView.Adapter<AlojamientoAdapter.MainViewHolder>() {

    private var dataList = mutableListOf<Hotel>()

    interface OnPasteleriaClickListener {
        fun onPasteleriaClick(hotel: Hotel)
    }

    fun setListData(data: MutableList<Hotel>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.order_row_alojamiento, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val hotel = dataList[position]
        holder.bindView(hotel)
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(hotel: Hotel) {
            Glide.with(context).load(hotel.imagen).centerCrop().into(itemView.imagen_hotel)
            itemView.nombreHotel.text = hotel.nombre
            itemView.descripcionHotel.text = hotel.descripcion
            if (hotel.abierto) {
                itemView.abiertoHotel.text = "Abierto"
            } else {
                itemView.abiertoHotel.text = "Cerrado"
            }
            itemView.setOnClickListener {
                if (hotel.abierto) {
                    itemClickListener.onPasteleriaClick(hotel)
                } else {
                    Toast.makeText(context, "El hotel se encuentra cerrado", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }

    }
}