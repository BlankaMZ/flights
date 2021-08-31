package com.example.flightsearch.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val STATIONS_LIST_ENDPOINT = "https://tripstest.ryanair.com/static/stations.json"
private const val FLIGHT_SEARCH_ENDPOINT = "Availability"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://www.ryanair.com/api/booking/v4/en-gb/")
    .build()

interface FlightApiService {

    @Headers("Content-type: application/json")
    @GET(STATIONS_LIST_ENDPOINT)
    suspend fun getStations(): List<StationNetwork>

    @Headers("Content-type: application/json")
    @GET(FLIGHT_SEARCH_ENDPOINT)
    suspend fun getFlight(
        @Query("dateout") dateout: String, @Query("origin") origin: String,
        @Query("destination") destination: String, @Query("adt") adt: String,
        @Query("chd") chd: String, @Query("teen") teen: String,
        @Query("inf") inf: String = "0", @Query("ToUs") toUs: String = "AGREED"
    ): FlightResponseNetwork

//    @Headers("x-apikey: 795ad45e4dc222bc0e5bd1c163bb885e3635e")
//    @POST(CAR_LIST_ENDPOINT)
//    suspend fun addCar(@Body newCar: CarNetwork) : CarNetwork
//
//    @Headers("x-apikey: 795ad45e4dc222bc0e5bd1c163bb885e3635e")
//    @GET(PERSON_LIST_ENDPOINT)
//    suspend fun getPeople(): List<PersonNetwork>
}


object FlightApi {
    val retrofitService: FlightApiService by lazy {
        retrofit.create(FlightApiService::class.java)
    }
}