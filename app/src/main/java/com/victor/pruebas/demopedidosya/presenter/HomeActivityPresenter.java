package com.victor.pruebas.demopedidosya.presenter;

import android.location.Location;
import android.support.annotation.NonNull;

import com.victor.pruebas.data.entity.RestaurantEntity;
import com.victor.pruebas.data.network.api.ApiClient;
import com.victor.pruebas.data.network.response.RestaurantsResponse;
import com.victor.pruebas.data.util.LocationProvider;
import com.victor.pruebas.demopedidosya.R;
import com.victor.pruebas.demopedidosya.util.Util;
import com.victor.pruebas.demopedidosya.util.UtilError;
import com.victor.pruebas.demopedidosya.util.UtilGenericDialog;
import com.victor.pruebas.demopedidosya.contract.HomeActivityView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityPresenter implements Presenter<HomeActivityView>  {

    private LocationProvider locationProvider;
    private boolean isBestLocation;
    private HomeActivityView view;

    private String point;

    public void setPoint(String point) {
        this.point = point;
    }

    private int offset;

    public HomeActivityPresenter(LocationProvider locationProvider) {
        this.locationProvider = locationProvider;
    }

    @Override
    public void setView(HomeActivityView view) {
        this.view = view;
    }

    public void getCurrentLocation() {
        view.showLoader();
        locationProvider.connect(new LocationProvider.LocationCallback() {
            @Override
            public void handleNewLocation(Location location) {
                if(!isBestLocation){
                    isBestLocation = true;
                    point = Util.parseCoordinateToString(location);
                    getRestaurantsList();
                }
            }

            @Override
            public void handleLocationNotAvailable() {
                view.hideLoader();
                view.showAlertDialog();
            }
        });
    }

    private void getRestaurantsList(){
        view.showLoader();
        ApiClient apiClient = new ApiClient();
        int country = 1;
        int max = 20;
        apiClient.getService(view.context()).getRestaurantList(point, country, max,offset).enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantsResponse> call, @NonNull Response<RestaurantsResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(!response.body().getData().isEmpty()) {
                        List<RestaurantEntity> restaurantEntities = response.body().getData();
                        view.updateRestaurantList(point, restaurantEntities);
                    }else{
                        UtilGenericDialog.showDialog(view.context(),view.context().getString(R.string.error_sorry),
                                view.context().getString(R.string.not_find_restaurant));
                        view.updateCoordinates(point);
                    }
                }else{
                    UtilError.showServiceError(view.context(),response);
                }
                view.hideLoader();
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantsResponse> call, @NonNull Throwable t) {
                UtilGenericDialog.showDialog(view.context(),
                        view.context().getString(R.string.error_sorry),
                        view.context().getString(R.string.error_search));
                view.hideLoader();
            }
        });
    }


    public void refreshRestaurantList(){
        offset++;
        getRestaurantsList();
    }

    public void stopListeningForLocation() {
        locationProvider.disconnect();
    }

}


