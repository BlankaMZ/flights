package com.example.flightsearch.flightslist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.flightsearch.domain.FlightInfo
import com.example.flightsearch.domain.FlightResponse
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("showFlightNumber")
fun TextView.showFlightNumber(item: FlightInfo) {
    text = "Flight number: ${item.flightNumber}"
}

@BindingAdapter("showOriginAndDestination")
fun TextView.showOriginAndDestination(item: FlightResponse) {
    text = "From: ${item.trips?.first()?.originName} to: ${item.trips?.first()?.destinationName}"
}

@BindingAdapter("showLocalOrgTime")
fun TextView.showLocalOrgTime(item: FlightInfo) {
    text = formatUTCTime(item.timeUTC[0])
}

@BindingAdapter("showLocalDstTime")
fun TextView.showLocalDstTime(item: FlightInfo) {
    text = formatUTCTime(item.timeUTC[1])
}

fun formatUTCTime(dataToFormat: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date =
        dateFormat.parse(dataToFormat)
    val formatter =
        SimpleDateFormat("dd.MM, HH:mm", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(date)
}