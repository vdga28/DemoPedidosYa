package com.victor.pruebas.data.network.api.services;

import com.victor.pruebas.data.network.response.AccessTokenResponse;
import com.victor.pruebas.data.network.response.RestaurantsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("tokens")
    Call<AccessTokenResponse> getTokenAccess(@Query("clientId") String clientId, @Query("clientSecret") String clientSecret);

    @GET("search/restaurants")
    Call<RestaurantsResponse> getRestaurantList(@Query(value = "point", encoded = true) String point,
                                                @Query("country") int country,
                                                @Query("max") int max,
                                                @Query("offset") int offset);
}
