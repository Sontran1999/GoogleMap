package com.example.googlemap.model.addressbysearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Geometry {
    @SerializedName("location")
    @Expose
    var location: Location? = null

    @SerializedName("viewport")
    @Expose
    var viewport: Viewport? = null
}