package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired : Boolean = false

    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback?.let {
            fusedLocationClient?.removeLocationUpdates(it)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 10000
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(30000)
                .setMaxUpdateDelayMillis(100)
                .build()

            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        setContent {

            var currentLocation by remember {
                mutableStateOf(LatLng(0.toDouble(), 0.toDouble()))
            }

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for (location in p0.locations) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                    }
                }
            }

            MyApplicationTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LocationScreen(currentLocation, this)
                }
            }
        }
    }

    @Composable
    private fun LocationScreen(currentLocation: LatLng, context : Context) {

        val launchMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
          permisionMaps ->
            val areGranted = permisionMaps.values.reduce { acc, next -> acc && next}
            if (areGranted) {
                locationRequired = true
                startLocationUpdates()
                Toast.makeText(context, "Access granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Access denied", Toast.LENGTH_SHORT).show()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Your location: ${currentLocation.latitude}/${currentLocation.longitude}")
                Button(onClick = {
                    /*Throw LatLng to api*/
                    if (permissions.all {
                            ContextCompat.checkSelfPermission(
                                context,
                                it
                            ) == PackageManager.PERMISSION_GRANTED
                        }) {
                        startLocationUpdates()
                    } else {
                        launchMultiplePermissions.launch(permissions)
                    }
                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://dev.daskal.ru/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val body = mapOf(
                        "lat" to currentLocation.latitude,
                        "lon" to currentLocation.longitude
                    )
                    val result = mutableStateOf("")

                    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

                    retrofitAPI.createPost(body).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call : Call<ResponseBody>, response: Response<ResponseBody>) {
                            
                        }
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                        }
                    })

                })
                {
                Text(text = "Get Lng/Lat")
                }
            }
        }
    }
}



