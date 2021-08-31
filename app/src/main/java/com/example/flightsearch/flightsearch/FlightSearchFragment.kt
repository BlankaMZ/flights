package com.example.flightsearch.flightsearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.flightsearch.R
import com.example.flightsearch.databinding.FragmentFlightSearchBinding
import com.example.flightsearch.domain.FlightResponse
import com.example.flightsearch.flightslist.FlightsListFragment

class FlightSearchFragment : Fragment() {

    private lateinit var viewModel: FlightSearchViewModel
    private lateinit var viewModelFactory: FlightSearchViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentFlightSearchBinding>(
            inflater,
            R.layout.fragment_flight_search, container, false
        )
        viewModelFactory = FlightSearchViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(FlightSearchViewModel::class.java)

        binding.lifecycleOwner = this
        binding.flightSearchViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.statusMessage.observe(viewLifecycleOwner, Observer<String> { message ->
            message?.let {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.flightData.observe(viewLifecycleOwner, Observer<FlightResponse> { response ->
            response?.let {
                if (response?.message == null) {
                    this.findNavController()
                        .navigate(
                            FlightSearchFragmentDirections.actionFlightSearchFragmentToFlightsListFragment(
                                response
                            )
                        )
                    viewModel.onFlightsDataNavigated()
                }

            }
        })
    }
}