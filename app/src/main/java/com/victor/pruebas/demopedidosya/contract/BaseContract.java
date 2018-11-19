package com.victor.pruebas.demopedidosya.contract;

import android.os.Bundle;
import android.arch.lifecycle.Lifecycle;

public interface BaseContract {
    interface Presenter<V extends BaseContract.View> {
        Bundle getStateBundle();

        V getView();

        void onPresenterCreated();

        void onPresenterDestroy();

    }

    interface View {

    }
}
