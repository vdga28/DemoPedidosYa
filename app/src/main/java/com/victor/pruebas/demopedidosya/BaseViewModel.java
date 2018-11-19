package com.victor.pruebas.demopedidosya;

import android.arch.lifecycle.ViewModel;

import com.victor.pruebas.demopedidosya.contract.BaseContract;

public final class BaseViewModel<V extends BaseContract.View, P extends BaseContract.Presenter<V>> extends ViewModel {
    private P presenter;

    public BaseViewModel() {
    }

    public void setPresenter(P presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
        }

    }

    public P getPresenter() {
        return this.presenter;
    }

    protected void onCleared() {
        super.onCleared();
        this.presenter.onPresenterDestroy();
        this.presenter = null;
    }
}