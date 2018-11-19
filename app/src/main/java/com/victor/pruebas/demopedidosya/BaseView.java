package com.victor.pruebas.demopedidosya;

import android.content.Context;

public interface BaseView {

    void initUI();

    Context context();

    void showLoader();

    void hideLoader();

}
