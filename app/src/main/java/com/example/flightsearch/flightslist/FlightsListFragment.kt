package com.example.flightsearch.flightslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.flightsearch.R
import com.example.flightsearch.databinding.FragmentFlightSearchBinding
import com.example.flightsearch.databinding.FragmentFlightsListBinding
import com.example.flightsearch.domain.FlightResponse
import com.example.flightsearch.domain.asFlightsInfoList

class FlightsListFragment : Fragment() {

    private lateinit var viewModel: FlightsListViewModel
    private lateinit var viewModelFactory: FlightsListViewModelFactory

    private var flightsListAdapter: FlightsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentFlightsListBinding>(
            inflater,
            R.layout.fragment_flights_list,
            container,
            false
        )

        val arguments = FlightsListFragmentArgs.fromBundle(requireArguments())
        viewModelFactory = FlightsListViewModelFactory(arguments.flightsResponse)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FlightsListViewModel::class.java)

        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.flightsListViewModel = viewModel


        flightsListAdapter = FlightsListAdapter(FlightsListListener { flight ->
            viewModel.onFlightClicked(flight)
        })
        binding.flightsList.adapter = flightsListAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.flightsInfo.observe(viewLifecycleOwner, Observer<FlightResponse> { response ->
            response?.let {
                flightsListAdapter?.submitList(response.asFlightsInfoList())
            }
        })
    }

}