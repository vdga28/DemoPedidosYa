package com.victor.pruebas.demopedidosya.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.victor.pruebas.data.entity.RestaurantEntity;
import com.victor.pruebas.demopedidosya.R;
import com.victor.pruebas.demopedidosya.contract.RestaurantMapView;
import com.victor.pruebas.demopedidosya.presenter.RestaurantMapPresenter;
import com.victor.pruebas.demopedidosya.util.Util;
import com.victor.pruebas.navigator.Navigator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.victor.pruebas.demopedidosya.view.activity.HomeActivity.REQUEST_LOCATION;

public class RestaurantMap extends BaseFragmentActivity implements OnMapReadyCallback, RestaurantMapView {

    private GoogleMap mMap;
    List<RestaurantEntity> restaurantEntityList;
    private static final float PADDING = 15F;
    RestaurantMapPresenter presenter = new RestaurantMapPresenter();
    String coordinates;

    @BindView(R.id.fab_list)
    FloatingActionButton mFabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_map);
        ButterKnife.bind(this);
        restaurantEntityList = getIntent().getParcelableArrayListExtra(Navigator.ENTITY_LIST);
        coordinates = getIntent().getStringExtra(Navigator.COORDINATES);
        presenter.setView(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    public void initUI() {
        initMap();
        setMarkers(restaurantEntityList);
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(keyCode, event);
            showExitDialog();
            return true;
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        permissionsMap();
    }

    @Override
    public void setMarkers(List<RestaurantEntity> restaurantEntityList) {

        for (int i = 0; i < restaurantEntityList.size(); i++) {
            LatLng restaurantPosition = Util.getCoordinates(restaurantEntityList.get(i).getCoordinates());
            String name = restaurantEntityList.get(i).getName();
            mMap.addMarker(new MarkerOptions().position(restaurantPosition).title(name));
        }
        this.restaurantEntityList = restaurantEntityList;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) initUI();
    }

    private void permissionsMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }else {
            initUI();
        }
    }

    private void initMap() {
        LatLng currentPosition = Util.getCoordinates(coordinates);
        updateCameraPosition(currentPosition);
        mMap.setOnMapLongClickListener(this::showAlertDialog);
    }

    private void updateCameraPosition(LatLng coordinate){
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(coordinate, PADDING);
        mMap.animateCamera(cu);
    }


    @OnClick(R.id.fab_list)
    public void goToList(){
        Navigator.getInstance().navigateToMapActivity(this, HomeActivity.class,
                true, coordinates, restaurantEntityList);
    }

    public void showAlertDialog(LatLng coordinate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.update_position));
        builder.setMessage(getString(R.string.search_restaurants_question));
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> updatePosition(coordinate));
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.warning));
        builder.setMessage(getString(R.string.exit_message));
        builder.setPositiveButton("Aceptar", (dialogInterface, i) -> this.finish());
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updatePosition(LatLng latLng){
        updateCameraPosition(latLng);
        presenter.getRestaurantsList(latLng);
    }

}
