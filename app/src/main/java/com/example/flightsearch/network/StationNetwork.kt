package com.example.flightsearch.network

import com.example.flightsearch.domain.Station

data class StationNetwork(
    val code: String,
    val name: String,
    val alternateName: String,
    //alias code to implement
    val countryCode: String,
    val countryName: String
    //rest to implement
)

data class StationsList(val stations: List<StationNetwork>)

fun StationsList.asDomainModel(): List<Station> {
    return stations.map {
        Station(
            code = it.code,
            name = it.name,
            alternateName = it.alternateName,
            countryCode = it.countryCode,
            countryName = it.countryName,
        )
    }
}