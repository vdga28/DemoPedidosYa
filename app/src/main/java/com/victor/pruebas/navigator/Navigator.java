package com.victor.pruebas.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.victor.pruebas.data.entity.RestaurantEntity;

import java.util.ArrayList;
import java.util.List;

public class Navigator {
    private static Navigator instance = null;
    public static final String ENTITY_LIST = "RestaurantList";
    public static final String COORDINATES = "coordinates";

    private Navigator() {
    }

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }

        return instance;
    }

    public void navigateToActivity(Activity activity, Class<?> destine, boolean destroy, Intent bundle) {
        if (activity != null) {
            Intent intent = new Intent(activity.getApplicationContext(), destine);
            if (bundle != null) {
                intent.putExtras(bundle);
            }

            activity.startActivity(intent);
            if (destroy) {
                activity.finish();
            }
        }

    }

    public void navigateToMapActivity(Activity activity, Class<?> destine, boolean destroy,
                                      String coordinates, List<RestaurantEntity> restaurantEntityList) {
        if (activity != null) {
            Intent intent = new Intent(activity.getApplicationContext(), destine);
            intent.putParcelableArrayListExtra(ENTITY_LIST, (ArrayList<? extends Parcelable>) restaurantEntityList);
            intent.putExtra(COORDINATES,coordinates);
            activity.startActivity(intent);
            if (destroy) {
                activity.finish();
            }
        }

    }
}
