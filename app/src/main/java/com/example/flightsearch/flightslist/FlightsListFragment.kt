package com.example.flightsearch.flightslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.flightsearch.R
import com.example.flightsearch.databinding.FragmentFlightSearchBinding
import com.example.flightsearch.databinding.FragmentFlightsListBinding

class FlightsListFragment : Fragment() {

    private lateinit var viewModel: FlightsListViewModel
    private lateinit var viewModelFactory: FlightsListViewModelFactory

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

        binding.lifecycleOwner = this
        binding.flightsListViewModel = viewModel

        return binding.root


    }

}