package com.hyt.bmc208_assignment;

import static com.hyt.bmc208_assignment.Admin.COLLECTION_NAME;

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

public class AdminSignupActivity extends AppCompatActivity {

    EditText editTextAdminUsername;
    EditText editTextAdminPassword;
    EditText editTextAdminEmail;
    EditText editTextAdminFullName;
    EditText editTextAdminStaffID;

    //reference to database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        //get all the user input data and store into variable
        editTextAdminUsername = findViewById(R.id.edit_text_signup_admin_username);
        editTextAdminPassword = findViewById(R.id.edit_text_signup_admin_password);
        editTextAdminEmail = findViewById(R.id.edit_text_signup_admin_email);
        editTextAdminFullName = findViewById(R.id.edit_text_signup_admin_fullName);
        editTextAdminStaffID = findViewById(R.id.edit_text_signup_admin_staffID);
    }

    //when the sign up button in admin sign up is click
    public void signUpButtonOnClick(View view) {

        //save patient as global admin
        Admin admin = new Admin();
        admin.setAdmin_ID(UUID.randomUUID().toString());
        admin.setAdmin_username(editTextAdminUsername.getText().toString());
        admin.setAdmin_password(editTextAdminPassword.getText().toString());
        admin.setAdmin_email(editTextAdminEmail.getText().toString());
        admin.setAdmin_fullName(editTextAdminFullName.getText().toString());
        admin.setAdmin_ID(editTextAdminStaffID.getText().toString());

        db.collection(COLLECTION_NAME)
                .document()
                .set(admin)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //pass it as global user
                        AdminLoginActivity.ADMIN = admin;
                        startActivity(new Intent(AdminSignupActivity.this, MenuActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminSignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }
}