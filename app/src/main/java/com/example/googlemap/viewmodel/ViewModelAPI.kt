package com.example.googlemap.viewmodel

import Routes
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlemap.api.APIService
import com.example.googlemap.api.ApiUtils

import retrofit2.Response

class ViewModelAPI: ViewModel() {
//    var mAPI: APIService? = ApiUtils().getAPIService()
//    var routes: MutableLiveData<Routes> = MutableLiveData()

//    fun getDirection(origin: String, destination: String) {
//        val call = mAPI?.getDirection(origin,destination)
//        call?.enqueue(object : retrofit2.Callback<Routes> {
//            override fun onFailure(call: retrofit2.Call<Routes>, t: Throwable) {
//                Log.d("MainActivity", "error loading from API")
//                routes?.postValue(null)
//            }
//
//            override fun onResponse(call: retrofit2.Call<Routes>, response: Response<Routes>) {
//                if (response.isSuccessful) {
//                    Log.d("MainActivity", "google loaded from API")
//                    routes?.postValue(response.body())
//                } else {
//                    Log.d("MainActivity", response.message() + response.code())
//                    routes?.postValue(null)
//                }
//            }
//        })
//    }
}