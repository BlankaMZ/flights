package com.example.flightsearch.flightslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flightsearch.domain.FlightResponse

class FlightsListViewModel(private val flightsResponse: FlightResponse) : ViewModel() {

    private val _flightsInfo = MutableLiveData<FlightResponse>()

    val flightsInfo: LiveData<FlightResponse>
        get() = _flightsInfo

    private fun setFlightsInfo() {
        _flightsInfo.value = flightsResponse
    }

    init {
        setFlightsInfo()
    }
}