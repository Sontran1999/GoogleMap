package com.example.googlemap.model.direction
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Routes {
	@SerializedName("bounds")
	@Expose
	var bounds: Bounds? = null

	@SerializedName("copyrights")
	@Expose
	var copyrights: String? = null

	@SerializedName("legs")
	@Expose
	var legs: List<Legs>? = null

	@SerializedName("overview_polyline")
	@Expose
	var overviewPolyline: Overview_polyline? = null

	@SerializedName("summary")
	@Expose
	var summary: String? = null

	@SerializedName("warnings")
	@Expose
	var warnings: List<Any>? = null

	@SerializedName("waypoint_order")
	@Expose
	var waypointOrder: List<Any>? = null
}