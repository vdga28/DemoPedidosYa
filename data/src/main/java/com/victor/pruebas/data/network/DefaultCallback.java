package com.victor.pruebas.data.network;

import android.support.annotation.NonNull;


import com.victor.pruebas.data.entity.ResponseEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultCallback<T> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful()) {
            ResponseEntity body = ((ResponseEntity) response.body());
            if (body != null && body.getError() != null){

            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {

    }


}
