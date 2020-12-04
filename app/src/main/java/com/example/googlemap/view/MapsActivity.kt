package com.example.googlemap.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.googlemap.R
import com.example.googlemap.common.MapsFactory
import com.example.googlemap.view.fragment.SearchFragment
import com.example.googlemap.viewmodel.ViewModelAPI
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_maps.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var mMap: GoogleMap
    private val PERMISSION_REQUEST = 1
    val arr = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var mapFragment: SupportMapFragment
    lateinit var viewModelAPI: ViewModelAPI
    var check = false
    lateinit var mRouteMarkerList: ArrayList<Marker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        btn_sheet.setOnClickListener(this)
        btn_gps.setOnClickListener(this)
        btn_Search.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
        setActionBar()
        viewModelAPI = ViewModelAPI()
        setFocus(false)
        mRouteMarkerList = arrayListOf()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(this, arr, PERMISSION_REQUEST)
        }
    }

    fun setFocus(boolean: Boolean) {
        if (boolean) {
            layoutDirection.visibility = View.VISIBLE
            card.visibility = View.GONE
        } else {
            card.visibility = View.VISIBLE
            layoutDirection.visibility = View.GONE
        }
    }

    fun setActionBar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setLogo(R.drawable.ic_google_maps)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.lastLocation
            .addOnSuccessListener(this,
                OnSuccessListener { location ->
                    if (location == null) {
                        return@OnSuccessListener
                    }
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    getAdressByGeocode(currentLocation)
                })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(object : GoogleMap.OnMapLongClickListener {
            override fun onMapLongClick(p0: LatLng) {
                getAdressByGeocode(p0)
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST -> for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {

                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_gps -> {
                getCurrentLocation()
            }
            R.id.btn_sheet -> {
//                var searchFragment = SearchFragment()
//                searchFragment.show(supportFragmentManager, BottomSheetDialogFragment().tag)
                if (check) {
                    setFocus(false)
                    check = false
                } else {
                    setFocus(true)
                    check = true
                }
            }
            R.id.btn_Search -> {
                viewModelAPI.removeMaker(mMap)
                getDirection(txt_diemdi.text.toString(), txt_diemden.text.toString())
            }
            R.id.btn_clear -> {
                viewModelAPI.removeMaker(mMap)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        val manager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.queryHint = " Tìm kiếm ở đây"
        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query != null) {
                    locationBySearch(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    fun locationBySearch(location: String) {
        viewModelAPI.locationLiveData.observe(this, {
            viewModelAPI.removeMaker(mMap)
            if (it != null) {
                if (it.status.equals("OK")) {
                    it.results?.let { it1 -> viewModelAPI.setMarkersAndZoom(it1, mMap) }
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelAPI.getLocation(location)
    }

    fun getDirection(origin: String, destination: String) {
        viewModelAPI.direction.observe(this, {
            viewModelAPI.removeMaker(mMap)
            if (it != null) {
                if (it.status.equals("OK")) {
                    var min: Int = it.routes?.get(0)?.legs?.get(0)?.distance?.value!!.toInt()
                    var count = 0
                    var km = 0
                    it.routes?.forEachIndexed { index, routes ->
                        km = it.routes?.get(index)?.legs?.get(0)?.distance?.value!!.toInt()
                        if (km < min) {
                            min = km
                            count = index
                        }
                    }
                    Toast.makeText(this, min.toString(), Toast.LENGTH_SHORT).show()
                    it.routes?.get(count)
                        ?.let { it1 -> viewModelAPI.setMarkersAndRoute(it1, this, mMap) }
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModelAPI.getDirection(origin, destination)
    }

    fun getAdressByGeocode(p0: LatLng) {
        viewModelAPI.geocodeLiveData.observe(this, {
            viewModelAPI.removeMaker(mMap)
            if (it != null) {
                val localMarkerOptions: MarkerOptions =
                    MarkerOptions().position(p0)
                        .title(p0.toString())
                        .snippet(it.results?.get(0)?.formattedAddress)
                val localMarker = mMap.addMarker(localMarkerOptions)
                mRouteMarkerList.add(localMarker)
                mMap.animateCamera(MapsFactory.autoZoomLevel(mRouteMarkerList))
            }
        })
        var latLng: String = p0.latitude.toString() + "," + p0.longitude.toString()
        viewModelAPI.getGeocode(latLng)

    }
}
