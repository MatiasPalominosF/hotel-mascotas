package com.example.hotelmascotas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelmascotas.R
import com.example.hotelmascotas.model.Hotel
import com.example.hotelmascotas.recyclerView.adapter.AlojamientoAdapter
import com.example.hotelmascotas.recyclerView.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_hotel.*


class HotelFragment : Fragment(), AlojamientoAdapter.OnPasteleriaClickListener {

    private lateinit var adapter: AlojamientoAdapter

    val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hotel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
    }


    fun setupRecyclerView() {
        rv_alojamiento.layoutManager = LinearLayoutManager(requireContext())
        rv_alojamiento.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        adapter = AlojamientoAdapter(requireContext(), this)
        rv_alojamiento.adapter = adapter


    }

    private fun observeData() {
        shimmer_view_container.startShimmer()
        viewModel.fetchDataHoteles().observe(viewLifecycleOwner, Observer {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onPasteleriaClick(hotel: Hotel) {
        val bundle = Bundle()
        bundle.putParcelable("hotel", hotel)
        findNavController().navigate(R.id.tomarAlojamientoFragment, bundle)
    }
}