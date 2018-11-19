package com.victor.pruebas.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import com.victor.pruebas.demopedidosya.BaseView;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    boolean backPressedEnable = true;

    private boolean isStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST && resultCode == RESULT_OK) {
        }
    }

    void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    public void clearBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final int backStackCount = getSupportFragmentManager().getBackStackEntryCount();

        for (int i = 0; i < backStackCount; i++) {
            final int backStackId = getSupportFragmentManager().getBackStackEntryAt(i).getId();
            fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    void injectView(Activity activity) {
        ButterKnife.bind(activity);
    }


    @Override
    public void initUI() {

    }

    @Override
    public Context context() {
        return this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStopped = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isStopped = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStopped = true;
    }


    public void setupToolbar(String title, View.OnClickListener listener) {

    }

    public void setTitleToolbar(String title, int color) {

    }

    public void setIconToolbar(int icon, View.OnClickListener listener) {

    }

    public void hideIconToolbar() {

    }

    public void hideToolbar() {

    }

    public void showToolbar() {

    }

    public void setBackPressedEnable(boolean backPressedEnable) {
        this.backPressedEnable = backPressedEnable;
    }

    public boolean isStopped() {
        return isStopped;
    }

    @Override
    public void onBackPressed() {
        if (backPressedEnable) {
            super.onBackPressed();
        }
    }


}
