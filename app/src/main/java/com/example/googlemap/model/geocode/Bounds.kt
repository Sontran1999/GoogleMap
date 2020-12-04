package com.example.googlemap.model.geocode
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Bounds {
    @SerializedName("northeast")
    @Expose
    var northeast: NortheastGeocode? = null

    @SerializedName("southwest")
    @Expose
    var southwest: SouthwestGeocode? = null
}