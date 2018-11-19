package com.victor.pruebas.demopedidosya.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import com.victor.pruebas.data.network.api.ApiClient;
import com.victor.pruebas.data.network.response.AccessTokenResponse;
import com.victor.pruebas.data.persistence.PreferencesManager;
import com.victor.pruebas.data.util.Constants;
import com.victor.pruebas.demopedidosya.util.UtilError;
import com.victor.pruebas.demopedidosya.util.UtilGenericDialog;
import com.victor.pruebas.demopedidosya.view.MainActivityView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements Presenter<MainActivityView> {

    private MainActivityView view;
    private PreferencesManager preferencesManager;

    public MainActivityPresenter (Context context){
        this.preferencesManager = new PreferencesManager(context, Constants.TOKEN_SESSION);
    }

    @Override
    public void setView(MainActivityView view) {
        this.view = view;
    }

    public void login(String clientId, String clientSecret){
        view.showLoader();
        ApiClient.getService().getTokenAccess(clientId, clientSecret).enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccessTokenResponse> call, @NonNull Response<AccessTokenResponse> response) {

                if(response.isSuccessful()) {
                    if (response.body() != null && response.body().getAccessToken() != null) {
                        preferencesManager.put(PreferencesManager.TOKEN_SESSION, response.body().getAccessToken());
                        navigateToHome();
                    }
                }else{
                    UtilError.showServiceError(view.context(), response);
                }
                view.hideLoader();
            }

            @Override
            public void onFailure(@NonNull Call<AccessTokenResponse> call, @NonNull Throwable t) {
                UtilGenericDialog.showGenericErrordialog(view.context());
                view.hideLoader();
            }
        });
    }

    private void navigateToHome() {
        view.navigateToHome();
    }
}
