package com.victor.pruebas.data.network.api;


import android.content.Context;

import com.victor.pruebas.data.network.api.services.ApiService;
import com.victor.pruebas.data.persistence.PreferencesManager;
import com.victor.pruebas.data.util.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String API_BASE_URL = "http://stg-api.pedidosya.com/public/v1/";

    public static ApiService getService() {
        return builder.build().create(ApiService.class);
    }

    public ApiService getService(Context context){
        PreferencesManager preferencesManager = new PreferencesManager(context, Constants.TOKEN_SESSION);
        HttpLoggingInterceptor serviceLogging = new HttpLoggingInterceptor();
        serviceLogging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(30, TimeUnit.SECONDS);
        client.readTimeout(30, TimeUnit.SECONDS);
        client.addInterceptor(serviceLogging);
        client.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.addHeader("Authorization", preferencesManager.getTokenSession());
            return chain.proceed(requestBuilder.build());
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
        return retrofit.create(ApiService.class);
    }

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES);

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.addInterceptor(logging).build())
                    .baseUrl(API_BASE_URL);

}
