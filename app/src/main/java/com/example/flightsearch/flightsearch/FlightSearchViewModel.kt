package com.example.flightsearch.flightsearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightsearch.domain.FlightResponse
import com.example.flightsearch.domain.Station
import com.example.flightsearch.network.FlightApi
import com.example.flightsearch.network.StationsList
import com.example.flightsearch.network.asDomainModel
import com.example.flightsearch.network.toFlight
import kotlinx.coroutines.launch
import java.lang.Exception

class FlightSearchViewModel : ViewModel() {

    val inputOrigin = MutableLiveData<String>()
    val inputDestination = MutableLiveData<String>()
    val inputDepartureDate = MutableLiveData<String>()
    val inputNoOfAdults = MutableLiveData<String>()
    val inputNoOfTeens = MutableLiveData<String>()
    val inputNoOfChildren = MutableLiveData<String>()

    // The internal MutableLiveData String that stores the most recent response
    private val _stationsList = MutableLiveData<List<Station>>()

    // The external immutable LiveData for the response String
    val stationsList: LiveData<List<Station>>
        get() = _stationsList

    private val _flightData = MutableLiveData<FlightResponse>()

    val flightData: LiveData<FlightResponse>
        get() = _flightData

    private val _statusMessage = MutableLiveData<String>()

    val statusMessage: LiveData<String>
        get() = _statusMessage

    init {
//        getAvailableStations()
    }

    fun searchTheFlight() {
        getAvailableFlights()
    }

    private fun getAvailableStations() {
        viewModelScope.launch {
            try {

                val listResult = StationsList(FlightApi.retrofitService.getStations())
                _stationsList.value = listResult.asDomainModel()
            } catch (e: Exception) {
                Log.i("Failure", e.toString())
            }
        }
    }

    private fun getAvailableFlights() {
        viewModelScope.launch {
            try {
                val result = FlightApi.retrofitService.getFlight(
                    "2021-12-30",
                    "WRO",
                    "DUB",
                    "1",
                    "0",
                    "0",
                ).toFlight()
                _flightData.value = result
                if (result.message == null) {
                    _statusMessage.value = "We found something!"
                } else {
                    _statusMessage.value = result.message
                }
            } catch (e: Exception) {
                _statusMessage.value = e.toString()
            }
        }
    }

    fun onFlightsDataNavigated() {
        _flightData.value = null
    }
}