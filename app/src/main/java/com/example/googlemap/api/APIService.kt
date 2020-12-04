package com.example.googlemap.api
import com.example.googlemap.model.direction.Direction
import com.example.googlemap.model.addressbysearch.AddressbySearch
import com.example.googlemap.model.geocode.Geocode
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @GET("directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String,
        @Query("alternatives") alternatives: Boolean = true
    ): Call<Direction>

    @GET("place/textsearch/json")
    fun getAddressbySearch(
        @Query("query") location: String,
        @Query("key") key: String
    ): Call<AddressbySearch>

    @GET("geocode/json")
    fun getNearbySearch(
        @Query("latlng") latLng: String,
        @Query("key") key: String
    ): Call<Geocode>
}