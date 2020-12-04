package com.example.googlemap.model.direction
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Direction {
	@SerializedName("geocoded_waypoints")
	@Expose
	var geocodedWaypoints: List<Geocoded_waypoints>? = null

	@SerializedName("routes")
	@Expose
	var routes: List<Routes>? = null

	@SerializedName("status")
	@Expose
	var status: String? = null
}