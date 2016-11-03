package com.apaza.moises.sucreapp.global;

import android.app.Activity;
import android.widget.Toast;

public class Global {
    private static Activity context;

    public static void setContext(Activity context){
        Global.context = context;
    }

    public static Activity getContext(){
        return context;
    }

    public static void showToastMessage(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
