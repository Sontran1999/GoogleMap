package com.example.googlemap.model.geocode

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class NortheastGeocode {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
}