package com.example.flightsearch.flightslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flightsearch.domain.FlightResponse

class FlightsListViewModelFactory(private val flightsResponse: FlightResponse) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightsListViewModel::class.java)) {
            return FlightsListViewModel(flightsResponse) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}