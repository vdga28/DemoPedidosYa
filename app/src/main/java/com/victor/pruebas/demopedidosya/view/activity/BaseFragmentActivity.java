package com.victor.pruebas.demopedidosya.view.activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.victor.pruebas.demopedidosya.BaseView;

public class BaseFragmentActivity extends FragmentActivity implements BaseView {
    @Override
    public void initUI() {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }
}
