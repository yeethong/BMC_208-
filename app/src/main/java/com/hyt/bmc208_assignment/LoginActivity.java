package com.hyt.bmc208_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    //can access this user from other activity
    public static Patient PATIENT;


    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewSignup;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CheckBox checkBoxShowPassword;
    CheckBox checkBoxRemember;

    boolean hasUsername;
    boolean hasPassword;
    SharedPreferences sharedPreferences;

    public static String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.edit_text_login_username);
        editTextPassword = findViewById(R.id.edit_text_login_password);
        buttonLogin = findViewById(R.id.button_login);
        textViewSignup = findViewById(R.id.text_view_signup);

        checkBoxShowPassword = findViewById(R.id.checkbox_show_password);
        checkBoxRemember = findViewById(R.id.checkbox_remember);

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //if username is empty then the log in button is blur
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hasUsername = !s.toString().trim().isEmpty();
                updateBtnLogin();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //if password less than 6 then the log in button is blur
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hasPassword = s.toString().trim().length() > 5;
                updateBtnLogin();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //when the log in button is clicked
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection(Patient.COLLECTION_NAME)
                        .whereEqualTo("patient_username",editTextUsername.getText().toString())
                        .whereEqualTo("patient_password", editTextPassword.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Patient patient = documentSnapshot.toObject(Patient.class);
                                    PATIENT = patient;
                                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                                    finish();
                                    break;
                                }
                                if(PATIENT == null){
                                    Toast.makeText(LoginActivity.this, "Wrong un or pw", Toast.LENGTH_LONG).show();
                                }
                            }

                        });

            }
        });









        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        getLoginDetails();

    }

    //function to save all the data in fields
    public void saveLoginDetails() {
        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean remember = checkBoxRemember.isChecked();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("remember", remember);
        editor.apply();
    }

    //function to clear all the data in fields
    public void clearLoginDetails() {
        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", "");
        editor.putString("password", "");
        editor.putBoolean("remember", false);
        editor.apply();
    }

    public void getLoginDetails() {
        try {
            sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");
            String password = sharedPreferences.getString("password", "");
            boolean remember = sharedPreferences.getBoolean("remember", false);
            editTextUsername.setText(username);
            editTextPassword.setText(password);
            checkBoxRemember.setChecked(remember);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void ShowPasswordClick(View view) {
        if (checkBoxShowPassword.isChecked()) {
            editTextPassword.setTransformationMethod(null);
        } else {
            editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    public void rememberClick(View view) {
        if (checkBoxRemember.isChecked()) {
            saveLoginDetails();
        } else {
            clearLoginDetails();
        }
    }

    private void updateBtnLogin(){
        buttonLogin.setEnabled(true);
    }


    //function to hide the keyboard
    private void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}