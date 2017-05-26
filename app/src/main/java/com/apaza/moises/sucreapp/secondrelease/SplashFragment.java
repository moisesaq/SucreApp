package com.apaza.moises.sucreapp.secondrelease;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apaza.moises.sucreapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFragment extends Fragment{

    private static final String TAG = "SPLASH FRAGMENT";
    private View view;
    private OnSplashFragmentListener mListener;

    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        startSplash();
        //goToLoginActivity();
    }

    private void startSplash(){
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, " >>> " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null)
                    goToMainActivity();
                else{
                    goToLoginFragment();
                }
            }
        }.start();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToLoginFragment(){
        mListener.onShowLogin();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSplashFragmentListener) {
            mListener = (OnSplashFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnSplashFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSplashFragmentListener {
        void onShowLogin();
    }
}
