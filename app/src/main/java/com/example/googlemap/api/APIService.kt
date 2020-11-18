package com.example.googlemap.api


import Json4Kotlin_Base
import Routes
import retrofit2.http.*

interface APIService {
    @GET("directions/json")
    fun getDirection(
        @Query("origin") origin: String?,
        @Query("destination") Destination: String?
    ):retrofit2.Call<Json4Kotlin_Base>
}