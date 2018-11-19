package com.victor.pruebas.demopedidosya.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.victor.pruebas.data.entity.RestaurantEntity;
import com.victor.pruebas.data.network.api.ApiClient;
import com.victor.pruebas.data.network.response.RestaurantsResponse;
import com.victor.pruebas.demopedidosya.R;
import com.victor.pruebas.demopedidosya.contract.RestaurantMapView;
import com.victor.pruebas.demopedidosya.util.Util;
import com.victor.pruebas.demopedidosya.util.UtilError;
import com.victor.pruebas.demopedidosya.util.UtilGenericDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantMapPresenter implements Presenter<RestaurantMapView> {

    private RestaurantMapView view;
    private int offset;

    @Override
    public void setView(RestaurantMapView view) {
        this.view = view;
    }

    public void getRestaurantsList(LatLng coordinates){
        ApiClient apiClient = new ApiClient();
        String point = Util.parseCoordinateToString(coordinates);
        int country = 1;
        int max = 20;
        apiClient.getService(view.context()).getRestaurantList(point, country, max,offset).enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantsResponse> call, @NonNull Response<RestaurantsResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    if (!response.body().getData().isEmpty()){
                    List<RestaurantEntity> restaurantEntities = response.body().getData();
                    view.setMarkers(restaurantEntities);
                    }else{
                        UtilGenericDialog.showDialog(view.context(),view.context().getString(R.string.error_sorry),
                                view.context().getString(R.string.not_find_restaurant));
                    }
                }else{
                    UtilError.showServiceError(view.context(),response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantsResponse> call, @NonNull Throwable t) {
                UtilGenericDialog.showGenericErrordialog(view.context());
            }
        });
    }
}
