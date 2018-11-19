package com.victor.pruebas.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantEntity implements Parcelable{

    private String name;
    private String coordinates;

    protected RestaurantEntity(Parcel in) {
        name = in.readString();
        coordinates = in.readString();
    }

    public static final Creator<RestaurantEntity> CREATOR = new Creator<RestaurantEntity>() {
        @Override
        public RestaurantEntity createFromParcel(Parcel in) {
            return new RestaurantEntity(in);
        }

        @Override
        public RestaurantEntity[] newArray(int size) {
            return new RestaurantEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(coordinates);
    }
}
