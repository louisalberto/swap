package com.example.now;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service implements LocationListener {

	private static final String LOGTAG = "LocationService";

	private LocationManager manager;
	private Location currentLocation;

//	Intent mIntent;

	/* Service Setup Methods */
	@Override
	public void onCreate() {
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Log.i(LOGTAG, "Location Service Running...");
	}

	public void startTracking() {
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return;
			// OR Register the listener with the Location Manager to receive
			// location updates from Wireless Network
			// manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
			// 0, 0, this);
		}
		Toast.makeText(this, "Determining Current Location...",
				Toast.LENGTH_LONG).show();

		// Register the listener with the Location Manager to receive location
		// updates from GPS
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0,
				this);

	}

	public void stopTracking() {
		manager.removeUpdates(this);
	}

	@Override
	public void onDestroy() {
		manager.removeUpdates(this);
		Log.i(LOGTAG, "Location Service Stopped...");
	}
	
	public Location getLocation() {
		return this.currentLocation;
	}

	/* Service Access Methods */
	public class LocationBinder extends Binder {
		LocationService getService() {
			return LocationService.this;
		}
	}

	private final IBinder binder = new LocationBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	/* LocationListener Methods */
	// @Override
	public void onLocationChanged(Location location) {
		this.currentLocation = location;
		Log.i(LOGTAG, "Adding new location");
		Log.i(LOGTAG,
				currentLocation.getLatitude() + ", "
						+ currentLocation.getLongitude());
		Toast.makeText(
				this,
				String.format(currentLocation.getLatitude() + ", "
						+ currentLocation.getLongitude()), Toast.LENGTH_SHORT)
				.show();

	}

	public void onProviderDisabled(String provider) {}

	public void onProviderEnabled(String provider) {}

	public void onStatusChanged(String provider, int status, Bundle extras) {}

}
