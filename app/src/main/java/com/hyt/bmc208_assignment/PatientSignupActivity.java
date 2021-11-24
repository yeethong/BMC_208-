package com.hyt.bmc208_assignment;

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

public class PatientSignupActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_patient_signup);

        //get all the user input data and store into variable
        editTextPatientUsername = findViewById(R.id.edit_text_signup_patient_username);
        editTextPatientPassword = findViewById(R.id.edit_text_signup_patient_password);
        editTextPatientEmail = findViewById(R.id.edit_text_signup_patient_email);
        editTextPatientFullName = findViewById(R.id.edit_text_signup_patient_fullName);
        editTextPatientICPassport = findViewById(R.id.edit_text_signup_patient_ICPassport);
    }

    //when the sign up button in patient sign up is clicked
    public void signUpButtonOnClick(View view) {

        String patientUsername = editTextPatientUsername.getText().toString();
        String patientPassword = editTextPatientPassword.getText().toString();
        String patientFullName = editTextPatientFullName.getText().toString();
        String patientEmail = editTextPatientEmail.getText().toString();
        String patientICPassport = editTextPatientICPassport.getText().toString();
        boolean check = validationinfo(patientUsername, patientPassword, patientFullName, patientEmail, patientICPassport);

        if (check == true) {
            Patient patient = new Patient();
            patient.setPatient_ID(UUID.randomUUID().toString());
            patient.setPatient_username(editTextPatientUsername.getText().toString());
            patient.setPatient_password(editTextPatientPassword.getText().toString());
            patient.setPatient_fullName(editTextPatientFullName.getText().toString());
            patient.setPatient_email(editTextPatientEmail.getText().toString());
            patient.setPatient_icPassport(editTextPatientICPassport.getText().toString());

            db.collection(Patient.COLLECTION_NAME)
                    .document()
                    .set(patient)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            PatientLoginActivity.PATIENT = patient;
                            startActivity(new Intent(PatientSignupActivity.this, RequestVaccination.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PatientSignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please fill up all the fields", Toast.LENGTH_LONG).show();
        }


    }

    private boolean validationinfo(String patientUsername, String patientPassword, String patientFullName, String patientEmail, String patientICPassport) {

        if (patientUsername.length() == 0) {
            editTextPatientUsername.requestFocus();
            editTextPatientUsername.setError("Username cannot be empty");
            return false;
        } else if (patientPassword.length() <= 7) {
            editTextPatientPassword.requestFocus();
            editTextPatientPassword.setError("Password must contain at least 7 characters long");
            return false;
        } else if (patientFullName.length() == 0) {
            editTextPatientFullName.requestFocus();
            editTextPatientFullName.setError("Full name cannot be blank");
            return false;
        } else if (patientEmail.length() == 0) {
            editTextPatientEmail.requestFocus();
            editTextPatientEmail.setError("Email cannot be blank");
            return false;
        } else if (patientICPassport.length() == 0) {
            editTextPatientICPassport.requestFocus();
            editTextPatientICPassport.setError("IC Passport No. cannot be blank");
            return false;
        } else {
            return true;
        }
    }
}