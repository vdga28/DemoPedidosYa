package com.victor.pruebas.demopedidosya.util;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class Util {

    private static final char COMMA = ',';

    public static String parseCoordinateToString(Location location){
        return String.format("%s,%s", String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
    }

    public static String parseCoordinateToString(LatLng coordinate){
        return String.format("%s,%s", String.valueOf(coordinate.latitude), String.valueOf(coordinate.longitude));
    }

    private static double parseLatitude(String coordinates) {
        return Double.parseDouble(coordinates.substring(0,coordinates.indexOf(COMMA)-1));
    }

    private static double parseLongitude(String coordinates) {
        return Double.parseDouble(coordinates.substring((coordinates.indexOf(COMMA)+1),coordinates.length()-1));
    }

    public static LatLng getCoordinates (String coordinates) {
        return new LatLng(parseLatitude(coordinates), parseLongitude(coordinates));
    }
}
