package com.hyt.bmc208_assignment;


import static com.hyt.bmc208_assignment.Patient.COLLECTION_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity {

    EditText editTextPatientUsername;
    EditText editTextPatientPassword;
    EditText editTextPatientEmail;
    EditText editTextPatientFullName;
    EditText editTextPatientICPassport;


    //reference to database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //get all the user input data and store into variable
        editTextPatientUsername = findViewById(R.id.edit_text_signup_patient_username);
        editTextPatientPassword = findViewById(R.id.edit_text_signup_patient_password);
        editTextPatientEmail = findViewById(R.id.edit_text_signup_patient_email);
        editTextPatientFullName = findViewById(R.id.edit_text_signup_patient_fullName);
        editTextPatientICPassport = findViewById(R.id.edit_text_signup_patient_ICPassport);



    }

    //when the sign up button in patient sign up is clicked
    public void signUpButtonOnClick(View view) {

        //save patient as global patient
        Patient patient = new Patient();
        patient.setPatient_ID(UUID.randomUUID().toString());
        patient.setPatient_username(editTextPatientUsername.getText().toString());
        patient.setPatient_password(editTextPatientPassword.getText().toString());
        patient.setPatient_email(editTextPatientEmail.getText().toString());
        patient.setPatient_fullName(editTextPatientFullName.getText().toString());
        patient.setPatient_icPassport(editTextPatientICPassport.getText().toString());

        db.collection(COLLECTION_NAME)
                .document()
                .set(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //pass it as global user
                        LoginActivity.PATIENT = patient;
                        startActivity(new Intent(SignupActivity.this, MenuActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });







    }
}