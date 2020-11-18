
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Geocoded_waypoints {
	@SerializedName("geocoder_status")
	@Expose
	var geocoderStatus: String? = null

	@SerializedName("place_id")
	@Expose
	var placeId: String? = null

	@SerializedName("types")
	@Expose
	var types: List<String>? = null
}