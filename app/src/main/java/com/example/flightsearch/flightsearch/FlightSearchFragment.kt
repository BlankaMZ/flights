package com.example.flightsearch.flightsearch

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
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
import java.util.*

class FlightSearchFragment : Fragment(), DatePickerDialog.OnDateSetListener {

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

        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.flightSearchViewModel = viewModel


        binding.departureDateText.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(requireContext(), this, year, month, day)
            datePickerDialog.show()

        }


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
                if (response?.message == null && response.trips?.first()?.dates?.first()?.flights?.isNotEmpty() == true) {
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        viewModel.chooseDate(year, month, day)
    }
}