package com.josie.busrouted.models;

import com.google.android.gms.maps.model.LatLng;

public class Stop {
    private String mName;
    private LatLng mLatLong;

    public Stop(String name, LatLng latLong) {
        this.mName = name;
        this.mLatLong = latLong;
    }

    public String getName() {
        return mName;
    }

    public LatLng getLatLong() {
        return mLatLong;
    }
}
