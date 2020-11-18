import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.Route


class Json4Kotlin_Base {
	@SerializedName("geocoded_waypoints")
	@Expose
	var geocodedWaypoints: List<Geocoded_waypoints>? = null

	@SerializedName("routes")
	@Expose
	var routes: List<Route>? = null

	@SerializedName("status")
	@Expose
	var status: String? = null
}