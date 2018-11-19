package com.victor.pruebas.demopedidosya.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.victor.pruebas.data.entity.ErrorEntity;
import com.victor.pruebas.demopedidosya.R;

import java.io.IOException;

import retrofit2.Response;

public class UtilError {

    private static final String TAG = UtilError.class.getSimpleName();
    private static final String INVALID_TOKEN = "INVALID_TOKEN";

    private UtilError(){
        //Required Implementation
    }

    public static void showServiceError(Context context, Response response) {
        try {
            if (response.errorBody() != null) {
                showErrorDialog(context, response.errorBody().string());
            }
        } catch (IOException e) {
            Log.e(TAG, "onResponse: ", e);
        }
    }

    private static void showErrorDialog(Context context, String error){

        ErrorEntity errorEntity = new Gson().fromJson(error, new TypeToken<ErrorEntity>() {
        }.getType());
        if(errorEntity.getCode().equals(INVALID_TOKEN))
            UtilGenericDialog.showDialog(context, context.getString(R.string.invalid_credentials),
                    context.getString(R.string.verify_data));
        else
            UtilGenericDialog.showDialog(context, errorEntity.getCode(), errorEntity.getMessages().toString());
    }
}
