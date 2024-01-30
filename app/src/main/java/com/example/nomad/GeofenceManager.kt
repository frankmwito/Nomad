package com.example.nomad
import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class GeofenceManager(private val context: Context) : ViewModel() {

  private val geofencingClient: GeofencingClient =
    LocationServices.getGeofencingClient(context)

  private val GEOFENCE_RADIUS = 500f // 500 meters

  val isCheckIn = mutableStateOf(false)

  fun addGeofence(geofence: Geofence) {
    val geofencingRequest = GeofencingRequest.Builder()
      .addGeofence(geofence)
      .build()

    if (ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return
    }
    geofencingClient.addGeofences(geofencingRequest, getGeofencePendingIntent())
      .addOnSuccessListener {
        Log.d("GeofenceManager", "Geofence added successfully")
      }
      .addOnFailureListener { e ->
        Log.e("GeofenceManager", "Error adding geofence: ${e.message}")
      }
  }

  private fun getGeofencePendingIntent(): PendingIntent {
    val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
  }

  fun removeGeofence() {
    geofencingClient.removeGeofences(getGeofencePendingIntent())
  }

  fun checkInOut() {
    viewModelScope.launch {
      if (isCheckIn.value) {
        removeGeofence()
        isCheckIn.value = false
      } else {
        addGeofence(
          Geofence.Builder()
            .setRequestId("workplace")
            .setCircularRegion(
              /* latitude= */ 40.7128,
              /* longitude= */ -74.0060,
              GEOFENCE_RADIUS
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()
        )
        isCheckIn.value = true
      }
    }
  }
}