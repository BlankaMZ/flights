package com.example.flightsearch.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FlightResponse(
    val termsOfUse: String?,
    val currency: String?,
    val trips: List<Trip>?,
    val message: String?,
) : Parcelable

@Parcelize
data class Trip(
    val origin: String,
    val originName: String,
    val destination: String,
    val destinationName: String,
    val dates: List<Dates>
) : Parcelable

@Parcelize
data class Dates(
    val dateOut: String,
    val flights: List<Flight>
) : Parcelable

@Parcelize
data class Flight(
    val flightNumber: String,
    val timeUTC: List<String>,
    val duration: String
) : Parcelable