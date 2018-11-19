package com.victor.pruebas.demopedidosya.contract;

import com.victor.pruebas.data.entity.RestaurantEntity;
import com.victor.pruebas.demopedidosya.BaseView;

import java.util.List;

public interface RestaurantMapView extends BaseView{

    void setMarkers(List<RestaurantEntity> restaurantEntityList);
}
