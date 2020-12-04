package com.example.googlemap.viewmodel


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlemap.R
import com.example.googlemap.api.APIService
import com.example.googlemap.api.ApiUtils
import com.example.googlemap.common.Constants
import com.example.googlemap.common.MapsFactory
import com.example.googlemap.model.direction.Direction
import com.example.googlemap.model.direction.Routes
import com.example.googlemap.model.addressbysearch.AddressbySearch
import com.example.googlemap.model.addressbysearch.Result
import com.example.googlemap.model.geocode.Geocode
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import retrofit2.Call

import retrofit2.Response

class ViewModelAPI : ViewModel() {
    var mAPI: APIService? = ApiUtils().getAPIService()
    var direction: MutableLiveData<Direction> = MutableLiveData()
    var locationLiveData: MutableLiveData<AddressbySearch> = MutableLiveData()
    var geocodeLiveData: MutableLiveData<Geocode> = MutableLiveData()
    private val mTimeSquare = LatLng(40.758895, -73.985131)
    private var mRouteMarkerList = ArrayList<Marker>()
    private var mResultMarkerList = ArrayList<Marker>()
    private lateinit var mRoutePolyline: Polyline

    fun getDirection(origin: String, destination: String) {
        val call =
            mAPI?.getDirections(origin, destination, Constants.GOOGLE_API_KEY)
        call?.enqueue(object : retrofit2.Callback<Direction> {
            override fun onFailure(call: Call<Direction>, t: Throwable) {
                Log.d("MainActivity", "error loading from API")
                direction?.postValue(null)
            }

            override fun onResponse(call: Call<Direction>, response: Response<Direction>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "google loaded from API")
                    direction?.postValue(response.body())
                } else {
                    Log.d("MainActivity", response.message() + response.code())
                    direction?.postValue(null)
                }
            }
        }
        )
    }

    fun getLocation(location: String) {
        val call = mAPI?.getAddressbySearch(
            location,
            Constants.GOOGLE_API_KEY
        )
        call?.enqueue(object : retrofit2.Callback<AddressbySearch> {
            override fun onResponse(
                call: Call<AddressbySearch>,
                response: Response<AddressbySearch>
            ) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "google loaded from API")
                    locationLiveData?.postValue(response.body())
                } else {
                    Log.d("MainActivity", response.message() + response.code())
                    locationLiveData?.postValue(null)
                }
            }

            override fun onFailure(call: Call<AddressbySearch>, t: Throwable) {
                Log.d("MainActivity", "error loading from API")
                direction?.postValue(null)
            }

        })
    }

    fun getGeocode(latlng: String) {
        val call = mAPI?.getNearbySearch(
            latlng,
            Constants.GOOGLE_API_KEY
        )
        call?.enqueue(object : retrofit2.Callback<Geocode> {
            override fun onResponse(call: Call<Geocode>, response: Response<Geocode>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "google loaded from API")
                    geocodeLiveData?.postValue(response.body())
                } else {
                    Log.d("MainActivity", response.message() + response.code())
                    geocodeLiveData?.postValue(null)
                }
            }

            override fun onFailure(call: Call<Geocode>, t: Throwable) {
                Log.d("MainActivity", "error loading from API")
                geocodeLiveData?.postValue(null)
            }

        })
    }


    @SuppressLint("NewApi")
    fun setMarkersAndRoute(route: Routes, mContext: Context, mGoogleMap: GoogleMap) {
        val startLatLng = LatLng(
            route.legs?.get(0)?.startLocation?.lat!!,
            route.legs?.get(0)?.startLocation?.lng!!
        )
        val startMarkerOptions: MarkerOptions =
            MarkerOptions().position(startLatLng).title(route.legs?.get(0)?.startAddress)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(MapsFactory.drawMarker(mContext, "S"))
                )
        val endLatLng = LatLng(
            route.legs?.get(0)?.endLocation?.lat!!,
            route.legs?.get(0)?.endLocation?.lng!!
        )
        val endMarkerOptions: MarkerOptions =
            MarkerOptions().position(endLatLng).title(route.legs?.get(0)?.endAddress)
                .icon(BitmapDescriptorFactory.fromBitmap(MapsFactory.drawMarker(mContext, "E")))
        val startMarker = mGoogleMap.addMarker(startMarkerOptions)
        val endMarker = mGoogleMap.addMarker(endMarkerOptions)
        mRouteMarkerList.add(startMarker)
        mRouteMarkerList.add(endMarker)

        val polylineOptions = MapsFactory.drawRoute(mContext)
        val pointsList = PolyUtil.decode(route.overviewPolyline?.points)
        for (point in pointsList) {
            polylineOptions.add(point)
        }

        mRoutePolyline = mGoogleMap.addPolyline(polylineOptions)

        mGoogleMap.animateCamera(MapsFactory.autoZoomLevel(mRouteMarkerList))
    }

    fun setMarkersAndZoom(resultList: List<Result>, mGoogleMap: GoogleMap) {
        val resultBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_spot_marker)
        for (result in resultList) {
            val name = result.formattedAddress
            val latitude = result.geometry?.location?.lat
            val longitude = result.geometry?.location?.lng
            val latLng = LatLng(latitude!!, longitude!!)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng).title(name).icon(resultBitmap)

            val marker = mGoogleMap.addMarker(markerOptions)
            mResultMarkerList.add(marker)
        }

        mGoogleMap.animateCamera(MapsFactory.autoZoomLevel(mResultMarkerList))
    }

    fun removeMaker(mGoogleMap: GoogleMap) {
        mGoogleMap.clear()
    }

}