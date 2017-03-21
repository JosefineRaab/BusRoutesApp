package com.josie.busrouted.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class BusRoute {
    private String mRouteName;
    private List<Stop> mStops;

    public BusRoute(String routeName, List<Stop> stops) {
        this.mRouteName = routeName;
        this.mStops = stops;
    }

    public String getRouteName() {
        return mRouteName;
    }

    public List<Stop> getStops() {
        return mStops;
    }

    public List<LatLng> getStopLatLongs() {
        List<LatLng> latLngList = new ArrayList<>();
        for (Stop stop : mStops) {
            latLngList.add(stop.getLatLong());
        }
        return latLngList;
    }

    public Stop getFirstStop() {
        return mStops.get(0);
    }
}
