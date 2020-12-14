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

}