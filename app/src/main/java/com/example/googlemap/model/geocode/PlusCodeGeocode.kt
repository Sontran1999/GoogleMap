package com.example.googlemap.model.geocode

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PlusCodeGeocode {
    @SerializedName("compound_code")
    @Expose
    var compoundCode: String? = null

    @SerializedName("global_code")
    @Expose
    var globalCode: String? = null
}