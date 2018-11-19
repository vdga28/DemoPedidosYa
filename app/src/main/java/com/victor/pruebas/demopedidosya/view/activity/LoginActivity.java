package com.victor.pruebas.demopedidosya.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.victor.pruebas.demopedidosya.R;
import com.victor.pruebas.demopedidosya.presenter.LoginActivityPresenter;
import com.victor.pruebas.demopedidosya.contract.LoginActivityView;
import com.victor.pruebas.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginActivityView {

    LoginActivityPresenter presenter;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_password)
    EditText etPassword;


    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.v_progress)
    View vProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginActivityPresenter(this.getBaseContext());
        presenter.setView(this);
        initUI();
    }

    @Override
    public void initUI() {
        super.initUI();
    }

    @Override
    public void showLoader() {
        vProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        vProgress.setVisibility(View.GONE);
    }

    @OnClick (R.id.btn_login)
    public void login(){
        String username;
        String password;
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();
        presenter.login(username,password);
        vProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToHome(){
        Navigator.getInstance().navigateToActivity(this, HomeActivity.class, false, null);
    }
}
