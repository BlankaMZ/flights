package com.example.flightsearch.flightslist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.flightsearch.domain.FlightInfo

@BindingAdapter("showFlightNumber")
fun TextView.showFlightNumber(item: FlightInfo) {
    text = "Flight number: ${item.flightNumber}"
}