package com.apaza.moises.sucreapp.secondrelease;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Patterns;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apaza.moises.sucreapp.R;
import com.apaza.moises.sucreapp.secondrelease.global.OnTextChangeListener;
import com.apaza.moises.sucreapp.secondrelease.global.SavedState;

import java.util.regex.Pattern;

public class InputTextView extends LinearLayout{

    private ImageView icon;
    private TextInputLayout textInputLayout;
    private EditText editText;

    public InputTextView(Context context) {
        super(context);
        setupView();
    }

    public InputTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
        initialize(attrs);
    }

    private void setupView(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_input_text, this, true);
        icon = (ImageView)findViewById(R.id.icon);
        textInputLayout = (TextInputLayout)findViewById(R.id.textInputLayout);
        textInputLayout.setErrorEnabled(true);
        editText = (EditText)findViewById(R.id.editText);
        editText.addTextChangedListener(new OnTextChangeListener() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayout.setError(null);
            }
        });
    }

    private void initialize(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.InputTextView);

        Drawable imageIcon = typedArray.getDrawable(R.styleable.InputTextView_iconImage);
        setImageIcon(imageIcon);

        boolean errorEnabled = typedArray.getBoolean(R.styleable.InputTextView_errorEnabled, false);
        setErrorEnabled(errorEnabled);

        boolean maxLengthEnabled = typedArray.getBoolean(R.styleable.InputTextView_maxLengthEnabled, false);
        setMaxLengthEnabled(maxLengthEnabled);

        int maxLength = typedArray.getInteger(R.styleable.InputTextView_maxLength, 0);
        setMaxLength(maxLength);

        int inputType = typedArray.getInt(R.styleable.InputTextView_android_inputType, InputType.TYPE_NULL);
        setInputType(inputType);

        int lines = typedArray.getInt(R.styleable.InputTextView_android_lines, 1);
        setLines(lines);

        String text = typedArray.getString(R.styleable.InputTextView_android_text);
        setText(text);

        String hint = typedArray.getString(R.styleable.InputTextView_hint);
        setHint(hint);

        typedArray.recycle();
    }

    public void setText(String text){
        if(text != null)
            editText.setText(text);
    }

    public String getText(){
        return editText.getText().toString();
    }

    public void clearField(){
        this.editText.getText().clear();
    }

    public void setImageIcon(Drawable imageIcon){
        if(imageIcon != null){
            icon.setVisibility(View.VISIBLE);
            icon.setImageDrawable(imageIcon);
        }
    }

    public void setErrorEnabled(boolean enabled){
        textInputLayout.setErrorEnabled(enabled);
    }

    public void setMaxLengthEnabled(boolean enabled){
        textInputLayout.setCounterEnabled(enabled);
    }

    public void setMaxLength(int maxLength){
        textInputLayout.setCounterMaxLength(maxLength);
    }

    public void setInputType(int inputType){
        editText.setInputType(inputType);
    }

    public void setLines(int lines){
        if(lines > 1){
            editText.setLines(lines);
            editText.setGravity(Gravity.START|Gravity.TOP);
        }
    }

    public void setHint(String text){
        if(text != null && !text.isEmpty())
            textInputLayout.setHint(text);
    }

    public boolean isTextValid(String textError){
        String text = editText.getText().toString();
        Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");

        if(text.isEmpty()){
            textInputLayout.setError(textInputLayout.getHint() + ", field empty");
            return false;
        }

        if(textInputLayout.isCounterEnabled()){
            if(text.length() <= textInputLayout.getCounterMaxLength()){
                textInputLayout.setError(null);
                return true;
            }else{
                textInputLayout.setError(textError);
                return false;
            }
        }
        textInputLayout.setError(null);
        return true;
    }

    public boolean isPhoneValid(){
        String phone = editText.getText().toString().trim();

        if(phone.isEmpty()){
            textInputLayout.setError("Phone empty");
            return false;
        }

        if(!Patterns.PHONE.matcher(phone).matches()){
            textInputLayout.setError("Phone invalid");
            return false;
        }else{
            textInputLayout.setError(null);
        }
        return true;
    }

    public boolean isEmailValid(){
        String email = editText.getText().toString().trim();
        if(email.isEmpty()){
            textInputLayout.setError(textInputLayout.getHint() + ", field empty");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputLayout.setError("Email invalid");
            return false;
        }else {
            textInputLayout.setError(null);
        }
        return true;
    }

    public boolean isPasswordValid(){
        String password = editText.getText().toString().trim();
        if(password.isEmpty()){
            textInputLayout.setError(textInputLayout.getHint() + ", field empty");
            return false;
        }
        textInputLayout.setError(null);
        return true;
    }

    public void setError(String error){
        textInputLayout.setError(error);
    }

    /*SAVE STATE OF THE VIEWS*/
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.childrenStates = new SparseArray();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).saveHierarchyState(ss.childrenStates);
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }
}
