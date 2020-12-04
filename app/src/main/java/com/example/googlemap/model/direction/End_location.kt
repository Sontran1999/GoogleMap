package com.example.googlemap.model.direction
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class End_location {
	@SerializedName("lat")
	@Expose
	var lat: Double? = null

	@SerializedName("lng")
	@Expose
	var lng: Double? = null
}