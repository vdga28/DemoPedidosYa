package com.victor.pruebas.data.util;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationProvider implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private static final int INTERVAL_IN_MS = 10 * 1000;

    private static final int FASTEST_INTERVAL_IN_MS = 1000;

    private final GoogleApiAvailability mGoogleApiAvailability;

    private final GoogleApiClient mGoogleApiClient;

    private final FusedLocationProviderApi mFusedLocationProviderApi;

    private final Context mContext;

    private final LocationRequest mLocationRequest;

    private LocationCallback mLocationCallback;

    private Location currentLocation;

    private boolean mUsingGms = false;

    public LocationProvider(Context context) {
        this.mGoogleApiAvailability = GoogleApiAvailability.getInstance();
        this.mGoogleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
        this.mFusedLocationProviderApi = LocationServices.FusedLocationApi;

        this.mContext = context;
        mLocationRequest =
                LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(INTERVAL_IN_MS)
                        .setFastestInterval(FASTEST_INTERVAL_IN_MS);

        determineIfUsingGms();
        if (isUsingGms()) {
            mGoogleApiClient.registerConnectionCallbacks(this);
            mGoogleApiClient.registerConnectionFailedListener(this);
        }
    }

    private void determineIfUsingGms() {
        int statusCode = mGoogleApiAvailability.isGooglePlayServicesAvailable(mContext);
        if (statusCode == ConnectionResult.SUCCESS || statusCode == ConnectionResult.SERVICE_UPDATING) {
            mUsingGms = true;
        }
    }

    private boolean isUsingGms() {
        return mUsingGms;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        int permissionGranted = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionGranted == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = mFusedLocationProviderApi.getLastLocation(mGoogleApiClient);
            mFusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            if (lastKnownLocation != null && lastKnownLocation.hasAccuracy() && lastKnownLocation.getAccuracy() < 150) {
                currentLocation = lastKnownLocation;
                if (currentLocation.getLatitude() != 0 && currentLocation.getLongitude() != 0) {
                    mLocationCallback.handleNewLocation(currentLocation);
                } else {
                    mLocationCallback.handleLocationNotAvailable();
                }
            } else {
                mLocationCallback.handleLocationNotAvailable();
            }
        } else {
            mLocationCallback.handleLocationNotAvailable();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        //default implementation
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }
        if (LocationUtil.isBetterLocation(location, currentLocation)) {
            mLocationCallback.handleNewLocation(location);
        }
    }

    public void connect(LocationCallback callback) {
        this.mLocationCallback = callback;
        if (isUsingGms()) {
            if (mGoogleApiClient.isConnected()) {
                onConnected(new Bundle());
            } else {
                mGoogleApiClient.connect();
            }
        }
    }

    public void disconnect() {
        if (isUsingGms() && mGoogleApiClient.isConnected()) {
            mFusedLocationProviderApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public interface LocationCallback {

        void handleNewLocation(Location location);

        void handleLocationNotAvailable();
    }
}
