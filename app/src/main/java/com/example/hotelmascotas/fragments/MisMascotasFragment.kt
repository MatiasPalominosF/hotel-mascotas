package com.example.hotelmascotas.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelmascotas.model.Mascota
import com.example.hotelmascotas.R
import com.example.hotelmascotas.recyclerView.adapter.AdaptadorCustom
import com.example.hotelmascotas.recyclerView.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_mis_mascotas.*


class MisMascotasFragment : Fragment(), AdaptadorCustom.OnOrderClickListener {

    //Creando instancia del adapter
    private lateinit var adapter: AdaptadorCustom

    //Creando instancia viewmodel
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mis_mascotas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        adapter = AdaptadorCustom(requireContext(), this, this)
        recyclerViewMascotas.adapter = adapter
        observeData()
        registrar_mascota.setOnClickListener {
            findNavController().navigate(R.id.resgistrarMascotaFragment)
        }
    }

    private fun observeData() {
        shimmer_view_container.startShimmer()
        viewModel.fetchDataMascotas().observe(viewLifecycleOwner, Observer {
            Log.d("DATOS", "$it")
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }


    private fun setupRecyclerView() {
        recyclerViewMascotas.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewMascotas.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun onOrderClick(mascota: Mascota) {
        val bundle = Bundle()
        bundle.putParcelable("mascota", mascota)
        Log.d("BUNDLE", "$bundle")

    }



}