package com.apaza.moises.sucreapp.secondrelease;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.secondrelease.global.Global;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginFragment extends Fragment implements View.OnClickListener,
                                                    FacebookCallback<LoginResult>, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "LOGIN FRAGMENT";
    private static final int RC_SIGN_IN = 1009;

    private View view;
    private InputTextView itvEmail, itvPassword;
    private Button login;
    private OnLoginFragmentListener mListener;

    /*LOGIN FACEBOOK*/
    private CallbackManager callbackManager;

    /*LOGIN GOOGLE*/
    private GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;

    /*FIRE BASE*/
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                //.requestIdToken("TOKEN")//TODO This is for work with FIRE BASE
                .build();

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();

        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                    goToMainActivity();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        setupLoginWithFacebook();
        setupLoginWithGoogle();
        setupView();
        return view;
    }

    private void setupView(){
        Button login = (Button)view.findViewById(R.id.b_login);
        login.setOnClickListener(this);
    }

    private void setup(){
        /*layoutLogin = (LinearLayout)view.findViewById(R.id.layoutLogin);
        layoutLoading = (LinearLayout)view.findViewById(R.id.layoutLoading);*/
        itvEmail = (InputTextView) view.findViewById(R.id.itv_email);
        itvPassword = (InputTextView) view.findViewById(R.id.itv_password);
        Button login = (Button)view.findViewById(R.id.b_login);
        login.setOnClickListener(this);
        TextView forgotPassword = (TextView) view.findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    private void setupLoginWithFacebook(){
        LoginButton loginWithFacebook = (LoginButton) view.findViewById(R.id.lb_login_facebook);
        //loginWithFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginWithFacebook.setFragment(this);
        loginWithFacebook.registerCallback(callbackManager, this);
    }

    private void setupLoginWithGoogle(){
        SignInButton signInButton = (SignInButton)view.findViewById(R.id.b_sign_in);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(fireAuthStateListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            Global.showToastMessage("Login success");
            GoogleSignInAccount account = result.getSignInAccount();
            if(account != null)
                itvEmail.setText(account.getEmail());
        }else{
            Global.showToastMessage("Login with google failed!");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.b_login:
                //printKeyHash();
                signIn();
                break;
        }
    }

    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Global.showToastMessage("Account sign out :/");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            mListener = (OnLoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void goToMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    /*FACEBOOK CALL BACK*/
    @Override
    public void onSuccess(LoginResult loginResult) {
        /*Utils.showToastMessage("Login facebook success " + loginResult.getAccessToken().getUserId());
        loadProfileFacebook(loginResult);*/
        handlerAccessTokenFacebook(loginResult.getAccessToken());
    }

    @Override
    public void onCancel() {
        Global.showToastMessage("Login facebook canceled");
    }

    @Override
    public void onError(FacebookException error) {
        Global.showToastMessage("Error in login facebook");
    }

    private void handlerAccessTokenFacebook(AccessToken accessToken){
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Global.showToastMessage("Error in login with facebook - firebase");
                }
            }
        });
    }

    public void loadProfileFacebook(LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject me, GraphResponse response) {
                Log.d(TAG, "PROFILE FACEBOOK >>> " + me.toString());
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
        request.setParameters(bundle);
        request.executeAsync();
    }

    /*GOOGLE API CLIENT LISTENER*/
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public interface OnLoginFragmentListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*PRINT THE KEY HASH*/
    private void printKeyHash() {
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.apaza.moises.sucreapp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }
}
