package com.victor.pruebas.demopedidosya.presenter;


import com.victor.pruebas.demopedidosya.BaseView;

interface Presenter<T extends BaseView> {

    /**
     * Setter of the view.
     */
    void setView(T view);
}
