package com.hyt.bmc208_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminLoginActivity extends AppCompatActivity {

    public static Admin ADMIN;

    EditText editTextAdminUsername;
    EditText editTextAdminPassword;
    Button buttonAdminLogin;
    TextView textViewAdminSignup;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CheckBox checkBoxShowPassword;
    CheckBox checkBoxRemember;

    boolean hasUsername;
    boolean hasPassword;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        editTextAdminUsername = findViewById(R.id.edit_text_login_admin_username);
        editTextAdminPassword = findViewById(R.id.edit_text_login_admin_password);
        buttonAdminLogin = findViewById(R.id.button_login_admin);
        textViewAdminSignup = findViewById(R.id.text_view_admin_signup);

        checkBoxShowPassword = findViewById(R.id.checkbox_admin_show_password);
        checkBoxRemember = findViewById(R.id.checkbox_admin_remember);

        editTextAdminUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //if username is empty then the log in button is blur
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextAdminPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(Admin.COLLECTION_NAME)
                        .whereEqualTo("admin_username" ,editTextAdminUsername.getText().toString())
                        .whereEqualTo("admin_password", editTextAdminPassword.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Admin admin = documentSnapshot.toObject(Admin.class);
                                    ADMIN = admin;
                                    //if correct admin username and password then go to admin menu page
                                    startActivity(new Intent(AdminLoginActivity.this, RecordNewBatch.class));
                                    break;
                                }
                                //if it is empty then show error message to them
                                if(ADMIN == null){
                                    Toast.makeText(AdminLoginActivity.this, "Wrong admin username or password", Toast.LENGTH_LONG).show();
                                }

                            }

                        });
            }
        });

        textViewAdminSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this, AdminSignupActivity.class));
            }
        });
        getLoginDetails();

    }

    //function to save all the data in fields
    public void saveLoginDetails() {
        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String username = editTextAdminUsername.getText().toString();
        String password = editTextAdminPassword.getText().toString();
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
            editTextAdminUsername.setText(username);
            editTextAdminPassword.setText(password);
            checkBoxRemember.setChecked(remember);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void ShowPasswordClick(View view) {
        if (checkBoxShowPassword.isChecked()) {
            editTextAdminPassword.setTransformationMethod(null);
        } else {
            editTextAdminPassword.setTransformationMethod(new PasswordTransformationMethod());
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
        buttonAdminLogin.setEnabled(true);
    }
}