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

public class PatientLoginActivity extends AppCompatActivity {

    public static Patient PATIENT;

    EditText editTextPatientUsername;
    EditText editTextPatientPassword;
    Button buttonPatientLogin;
    TextView textViewPatientSignup;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CheckBox checkBoxShowPassword;
    CheckBox checkBoxRemember;

    boolean hasUsername;
    boolean hasPassword;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        editTextPatientUsername = findViewById(R.id.edit_text_login_patient_username);
        editTextPatientPassword = findViewById(R.id.edit_text_login_patient_password);
        buttonPatientLogin = findViewById(R.id.button_login_patient);
        textViewPatientSignup = findViewById(R.id.text_view_patient_signup);

        checkBoxShowPassword = findViewById(R.id.checkbox_patient_show_password);
        checkBoxRemember = findViewById(R.id.checkbox_patient_remember);

        editTextPatientUsername.addTextChangedListener(new TextWatcher() {
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

        editTextPatientPassword.addTextChangedListener(new TextWatcher() {
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

        buttonPatientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection(Patient.COLLECTION_NAME)
                        .whereEqualTo("patient_username" ,editTextPatientUsername.getText().toString())
                        .whereEqualTo("patient_password", editTextPatientPassword.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Patient patient = documentSnapshot.toObject(Patient.class);
                                    PATIENT = patient;
                                    //if correct patient username and password then go to patient menu page
                                    startActivity(new Intent(PatientLoginActivity.this, MenuActivity.class));
                                    break;
                                }
                                //if it is empty then show error message to them
                                if(PATIENT == null){
                                    Toast.makeText(PatientLoginActivity.this, "Wrong patient username or password", Toast.LENGTH_LONG).show();
                                }

                            }

                        });
            }
        });

        textViewPatientSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientLoginActivity.this, PatientSignupActivity.class));
            }
        });
        getLoginDetails();

    }

    //function to save all the data in fields
    public void saveLoginDetails() {
        sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);
        String username = editTextPatientUsername.getText().toString();
        String password = editTextPatientPassword.getText().toString();
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
            editTextPatientUsername.setText(username);
            editTextPatientPassword.setText(password);
            checkBoxRemember.setChecked(remember);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void ShowPasswordClick(View view) {
        if (checkBoxShowPassword.isChecked()) {
            editTextPatientPassword.setTransformationMethod(null);
        } else {
            editTextPatientPassword.setTransformationMethod(new PasswordTransformationMethod());
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
        buttonPatientLogin.setEnabled(true);
    }
}