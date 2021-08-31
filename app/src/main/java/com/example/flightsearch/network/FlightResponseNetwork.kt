package com.example.flightsearch.network

import com.example.flightsearch.domain.FlightResponse
import com.example.flightsearch.domain.*

data class FlightResponseNetwork(
    val termsOfUse: String?,
    val currency: String?,
    val currPrecision: Int?,
    val routeGroup: String?,
    val tripType: String?,
    val upgradeType: String?,
    val trips: List<TripNetwork>?,
    val serverTimeUTC: String?,
    val message: String?
)

fun FlightResponseNetwork.toFlight() = FlightResponse(
    termsOfUse = termsOfUse,
    currency = currency,
    trips = trips?.map {
        it.toTrip()
    },
    message = message
)

data class TripNetwork(
    val origin: String,
    val originName: String,
    val destination: String,
    val destinationName: String,
    val routeGroup: String,
    val tripType: String,
    val upgradeType: String,
    val dates: List<DatesNetwork>,
)

fun TripNetwork.toTrip() = Trip(
    origin = origin,
    originName = originName,
    destination = destination,
    destinationName = destinationName,
    dates = dates.map {
        it.toDates()
    }
)

data class DatesNetwork(
    val dateOut: String,
    val flights: List<FlightNetwork>
)

fun DatesNetwork.toDates() = Dates(
    dateOut = dateOut,
    flights = flights.map {
        it.toFlight()
    }
)

data class FlightNetwork(
    val faresLeft: Int,
    val flightKey: String,
    val infantsLeft: Int,
    val regularFare: RegularFareNetwork,
    val operatedBy: String,
    val segments: List<SegmentNetwork>,
    val flightNumber: String,
    val time: List<String>,
    val timeUTC: List<String>,
    val duration: String
)

fun FlightNetwork.toFlight() = Flight(
    flightNumber = flightNumber,
    timeUTC = timeUTC,
    duration = duration
)

data class RegularFareNetwork(
    val fareKey: String,
    val fareClass: String,
    val fares: List<FareNetwork>
)

data class FareNetwork(
    val type: String,
    val amount: Float,
    val count: Int,
    val hasDiscount: Boolean,
    val publishedFare: Float,
    val discountInPercent: Int,
    val hasPromoDiscount: Boolean,
    val discountAmount: Float,
    val hasBogof: Boolean
)

data class SegmentNetwork(
    val segmentNr: Integer,
    val origin: String,
    val destination: String,
    val flightNumber: String,
    val time: List<String>,
    val timeUTC: List<String>,
    val duration: String
)