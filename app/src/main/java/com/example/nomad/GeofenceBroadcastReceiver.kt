package com.example.nomad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent


class GeofenceBroadcastReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {
    if (intent.action == "android.location.PROVIDERS_CHANGED") {
      return
    }

    val geofencingEvent: GeofencingEvent? = GeofencingEvent.fromIntent(intent)
    if (geofencingEvent == null) {
      Log.e("GeofenceBroadcastReceiver", "Error receiving geofencing event: geofencingEvent is null")
      return
    }

    if (geofencingEvent.hasError()) {
      val errorCode = geofencingEvent.errorCode
      Log.e("GeofenceBroadcastReceiver", "Error receiving geofencing event. Error code: $errorCode")
      return
    }

    val geofenceTransition = geofencingEvent.geofenceTransition
    if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
      Log.i("GeofenceBroadcastReceiver", "Employee checked in")
      // Update UI or perform other actions when the employee checks in
    }
  }
}