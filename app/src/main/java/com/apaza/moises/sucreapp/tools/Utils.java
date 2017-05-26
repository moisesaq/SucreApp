package com.apaza.moises.sucreapp.tools;

import android.widget.Toast;

import com.apaza.moises.sucreapp.secondrelease.SucreApp;

public class Utils {

    public static void showToast(String message){
        Toast.makeText(SucreApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
