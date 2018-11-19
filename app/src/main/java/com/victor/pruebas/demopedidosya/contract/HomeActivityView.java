package com.victor.pruebas.demopedidosya.contract;

import com.victor.pruebas.data.entity.RestaurantEntity;
import com.victor.pruebas.demopedidosya.BaseView;

import java.util.List;

public interface HomeActivityView extends BaseView {

    void updateRestaurantList(String coordinates, List<RestaurantEntity> restaurantEntityList);
    void showAlertDialog();
    void updateCoordinates(String coordinates);
}
