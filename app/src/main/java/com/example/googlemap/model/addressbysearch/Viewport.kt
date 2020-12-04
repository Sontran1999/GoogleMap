package com.example.googlemap.model.addressbysearch

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Viewport {
    @SerializedName("northeast")
    @Expose
    var northeast: NortheastAddress? = null

    @SerializedName("southwest")
    @Expose
    var southwest: SouthwestAddress? = null
}