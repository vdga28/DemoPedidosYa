package com.victor.pruebas.demopedidosya.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.victor.pruebas.data.entity.RestaurantEntity;
import com.victor.pruebas.data.persistence.PreferencesManager;
import com.victor.pruebas.data.util.Constants;
import com.victor.pruebas.data.util.LocationProvider;
import com.victor.pruebas.demopedidosya.R;
import com.victor.pruebas.demopedidosya.presenter.HomeActivityPresenter;
import com.victor.pruebas.demopedidosya.contract.HomeActivityView;
import com.victor.pruebas.demopedidosya.view.adapter.RestaurantListAdapter;
import com.victor.pruebas.navigator.Navigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeActivityView{

    PreferencesManager preferencesManager;
    HomeActivityPresenter presenter;
    LocationProvider locationProvider;
    private String coordinates;
    private RestaurantListAdapter restaurantListAdapter;
    List<RestaurantEntity> restaurantEntityList = new ArrayList<>();

    public static final int REQUEST_LOCATION = 2;

    @BindView(R.id.rv_restaurant_list)
    RecyclerView mRVRestaurantList;

    @BindView(R.id.fab_map)
    FloatingActionButton mFabMap;

    @BindView(R.id.v_progress)
    View vProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        preferencesManager = new PreferencesManager(this.getBaseContext(), Constants.TOKEN_SESSION);
        ButterKnife.bind(this);
        if(getIntent().getParcelableArrayListExtra(Navigator.ENTITY_LIST)!= null && getIntent().getStringExtra(Navigator.COORDINATES)!= null) {
            restaurantEntityList = getIntent().getParcelableArrayListExtra(Navigator.ENTITY_LIST);
            coordinates = getIntent().getStringExtra(Navigator.COORDINATES);
        }
        locationProvider = new LocationProvider(this);
        presenter = new HomeActivityPresenter(locationProvider);
        presenter.setView(this);
        initUI();
        checkPermission();

    }

    @Override
    public void initUI() {
        super.initUI();
        restaurantListAdapter = new RestaurantListAdapter(restaurantEntityList,this);
        mRVRestaurantList.setLayoutManager(new LinearLayoutManager(this));
        mRVRestaurantList.setAdapter(restaurantListAdapter);

        mRVRestaurantList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached && !vProgress.isShown()) {
                    presenter.refreshRestaurantList();
                }
            }
        });
    }

    @Override
    public void showLoader() {
        vProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        vProgress.setVisibility(View.GONE);
    }

    @OnClick(R.id.fab_map)
    public void goToMap(){
        if(coordinates == null && restaurantEntityList == null) coordinates = "-34.9032784,-56.1881599";
        Navigator.getInstance().navigateToMapActivity(this, RestaurantMap.class,
                true, coordinates, restaurantEntityList);
    }


    private void checkPermission() {
        int permissionGranted = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionGranted != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            validateNavigationFrom();
        }
    }

    @Override
    public void updateRestaurantList(String coordinates, List<RestaurantEntity> restaurantEntityList) {
        this.coordinates = coordinates;
        restaurantListAdapter.bindList(restaurantEntityList);
        restaurantListAdapter.notifyDataSetChanged();
        this.restaurantEntityList.addAll(restaurantEntityList);
    }

    @Override
    public void updateCoordinates(String coordinates){
        this.coordinates = coordinates;
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.update_position));
        builder.setMessage(getString(R.string.search_restaurants_question));
        builder.setPositiveButton(R.string.retry, (dialogInterface, i) -> presenter.getCurrentLocation());
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void validateNavigationFrom (){
        if (restaurantEntityList != null && !restaurantEntityList.isEmpty()
                && coordinates!= null && !coordinates.isEmpty()) {
            updateRestaurantList(coordinates, restaurantEntityList);
            presenter.setPoint(coordinates);
        }
        else presenter.getCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            validateNavigationFrom();
        }
    }
}
