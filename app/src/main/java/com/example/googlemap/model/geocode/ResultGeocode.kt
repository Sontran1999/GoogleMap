package com.example.googlemap.model.geocode

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ResultGeocode {
    @SerializedName("address_components")
    @Expose
    var addressComponents: List<AddressComponent>? = null

    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String? = null

    @SerializedName("geometry")
    @Expose
    var geometry: GeometryGeocode? = null

    @SerializedName("place_id")
    @Expose
    var placeId: String? = null

    @SerializedName("plus_code")
    @Expose
    var plusCode: PlusCodeGeocode? = null

    @SerializedName("types")
    @Expose
    var types: List<String>? = null
}