package com.example.googlemap.model.addressbysearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AddressbySearch {
    @SerializedName("html_attributions")
    @Expose
    var htmlAttributions: List<Any>? = null

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

    @SerializedName("status")
    @Expose
    var status: String? = null
}