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
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.getTag
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.googlemap.R
import com.example.googlemap.view.fragment.SearchFragment
import com.example.googlemap.viewmodel.ViewModelAPI
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener {

    private lateinit var mMap: GoogleMap
    private val PERMISSION_REQUEST = 1
    val arr = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    lateinit var mapFragment: SupportMapFragment
    lateinit var viewModelAPI : ViewModelAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val btn_gps: ImageButton = findViewById(R.id.btn_gps)
        val btn_sheet: ImageButton = findViewById(R.id.btn_sheet)
        btn_sheet.setOnClickListener(this)
        btn_gps.setOnClickListener(this)
        setActionBar()
        viewModelAPI = ViewModelAPI()
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
                    mMap.addMarker(
                        MarkerOptions().position(currentLocation)
                            .title("Marker in current location")
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20F))
                })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST -> for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    //TODO: Action when permission denied
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
                var searchFragment = SearchFragment()
                searchFragment.show(supportFragmentManager, BottomSheetDialogFragment().tag)
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

//    fun getDirection(origin: String, destination: String){
//        viewModelAPI.routes.observe(this,{
//            if( it != null ){
//
//            }
//        })
//    }
}