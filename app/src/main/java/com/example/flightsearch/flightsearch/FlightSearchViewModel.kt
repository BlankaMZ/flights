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
        getAvailableStations()
    }

    fun searchTheFlight() {
        getAvailableFlights()
    }

    private fun getAvailableStations() {
        viewModelScope.launch {
            try {

                val listResult = StationsList(FlightApi.retrofitService.getStations().stations)
                _stationsList.value = listResult.asDomainModel()
            } catch (e: Exception) {
                Log.i("Failure", e.toString())
            }
        }
    }

    private fun getAvailableFlights() {
        viewModelScope.launch {
            if (inputOrigin.value != null && inputDestination.value != null && inputDepartureDate.value != null && inputNoOfAdults.value != null) {
                if (inputNoOfAdults.value!!.toInt() < 1) {
                    _statusMessage.value = "Must be min 1 adult passenger"
                } else if (numberOfPassengers() > 25) {
                    _statusMessage.value = "Must be less then 25 passengers"
                } else {
                    try {
                        val result = FlightApi.retrofitService.getFlight(
                            inputDepartureDate.value!!,
                            inputOrigin.value!!,
                            inputDestination.value!!,
                            inputNoOfAdults.value!!,
                            inputNoOfChildren.value ?: "0",
                            inputNoOfTeens.value ?: "0",
                        ).toFlight()
                        _flightData.value = result
                        if (result.message == null) {
                            if (result.trips?.first()?.dates?.first()?.flights?.isNotEmpty() == true) {
                                _statusMessage.value = "We found something!"
                            } else {
                                _statusMessage.value = "No results!"
                            }
                        } else {
                            _statusMessage.value = result.message
                        }
                    } catch (e: Exception) {
                        _statusMessage.value = e.toString()
                    }
                }
            } else {
                _statusMessage.value = "Fill the fields!"
            }
        }
    }

    fun onFlightsDataNavigated() {
        _flightData.value = null
    }

    fun chooseDate(year: Int, month: Int, day: Int) {
        inputDepartureDate.value = "$year-$month-$day"
    }

    private fun numberOfPassengers(): Int {
        var adults = inputNoOfAdults.value?.toIntOrNull() ?: 0
        var teens = inputNoOfAdults.value?.toIntOrNull() ?: 0
        var children = inputNoOfAdults.value?.toIntOrNull() ?: 0
        return adults + teens + children
    }
}