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

public class AddNewCentre extends AppCompatActivity {

    EditText editTextNewCentreName;
    EditText editTextNewAddress;

    //reference to database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_centre);

        //get all the user input data and store into variable
        editTextNewCentreName = findViewById(R.id.edit_text_add_new_centreName);
        editTextNewAddress = findViewById(R.id.edit_text_add_new_address);
    }

    //when the add button in is clicked
    public void addNewCentreButtonOnClick(View view) {
        String newCentreName = editTextNewCentreName.getText().toString();
        String newCentreAddress = editTextNewAddress.getText().toString();
        boolean check = validationInfo(newCentreName, newCentreAddress);

        if (check == true) {
            Healthcare_centre healthcare_centre = new Healthcare_centre();
            healthcare_centre.setCentre_ID(UUID.randomUUID().toString());
            healthcare_centre.setCentre_name(editTextNewCentreName.getText().toString());
            healthcare_centre.setCentre_address(editTextNewAddress.getText().toString());

            db.collection(Healthcare_centre.COLLECTION_NAME)
                    .document()
                    .set(healthcare_centre)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(AddNewCentre.this, AdminSignupActivity.class));
                            Toast.makeText(getApplicationContext(), "New centre added successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewCentre.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please fill up all the fields", Toast.LENGTH_LONG).show();
        }


        }

    //patient validation function
    private boolean validationInfo(String newCentreName, String newCentreAddress) {

        if (newCentreName.length() == 0) {
            editTextNewCentreName.requestFocus();
            editTextNewCentreName.setError("Centre name cannot be empty");
            return false;
        } else if (newCentreAddress.length() == 0) {
            editTextNewAddress.requestFocus();
            editTextNewAddress.setError("Address cannot be empty");
            return false;
        } else {
            return true;
        }

    }


}