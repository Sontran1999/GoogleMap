package com.example.googlemap.api

class ApiUtils {

    val BASE_URL = "https://maps.googleapis.com/maps/api/"

    fun getAPIService(): APIService {
        return RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)
    }
}