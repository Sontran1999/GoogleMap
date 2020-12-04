package com.example.googlemap.model.geocode

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Geocode {
    @SerializedName("plus_code")
    @Expose
    var plusCode: PlusCodeGeocode? = null

    @SerializedName("results")
    @Expose
    var results: List<ResultGeocode>? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}