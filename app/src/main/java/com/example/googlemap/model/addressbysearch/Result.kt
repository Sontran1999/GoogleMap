package com.example.googlemap.model.addressbysearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Result {
    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String? = null

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("place_id")
    @Expose
    var placeId: String? = null

    @SerializedName("plus_code")
    @Expose
    var plusCode: PlusCode? = null

    @SerializedName("reference")
    @Expose
    var reference: String? = null

    @SerializedName("types")
    @Expose
    var types: List<String>? = null
}