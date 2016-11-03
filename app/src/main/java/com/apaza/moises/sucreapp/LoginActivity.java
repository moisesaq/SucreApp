package com.apaza.moises.sucreapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.apaza.moises.sucreapp.fragment.LoginFragment;
import com.apaza.moises.sucreapp.fragment.SplashFragment;
import com.apaza.moises.sucreapp.global.Global;

public class LoginActivity extends AppCompatActivity implements SplashFragment.OnSplashFragmentListener, LoginFragment.OnLoginFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Global.setContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showFragment(SplashFragment.newInstance());
    }

    public void showFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.replace(R.id.content_login, fragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*SPLASH FRAGMENT LISTENER*/
    @Override
    public void onShowLogin() {
        showFragment(LoginFragment.newInstance());
    }
}
