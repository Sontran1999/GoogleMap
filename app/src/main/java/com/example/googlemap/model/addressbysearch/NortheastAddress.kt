package com.example.googlemap.model.addressbysearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class NortheastAddress {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
}