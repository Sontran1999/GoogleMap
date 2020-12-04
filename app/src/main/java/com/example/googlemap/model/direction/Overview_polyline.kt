package com.example.googlemap.model.direction
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Overview_polyline {
	@SerializedName("points")
	@Expose
	var points: String? = null
}