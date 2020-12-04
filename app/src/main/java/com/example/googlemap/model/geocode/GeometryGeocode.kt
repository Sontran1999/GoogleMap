package com.example.googlemap.model.geocode

import com.example.googlemap.model.addressbysearch.Viewport

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class GeometryGeocode {
    @SerializedName("location")
    @Expose
    var location: LocationGeocode? = null

    @SerializedName("location_type")
    @Expose
    var locationType: String? = null

    @SerializedName("viewport")
    @Expose
    var viewport: Viewport? = null

    @SerializedName("bounds")
    @Expose
    var bounds: Bounds? = null
}