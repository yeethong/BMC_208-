package com.hyt.bmc208_assignment;

import static com.hyt.bmc208_assignment.Admin.COLLECTION_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class AdminSignupActivity extends AppCompatActivity {

    EditText editTextAdminUsername;
    EditText editTextAdminPassword;
    EditText editTextAdminEmail;
    EditText editTextAdminFullName;
    EditText editTextAdminStaffID;

    //display the centre name and address
    TextView textViewCentreName;
    TextView textViewCentreAddress;

    Spinner spinner_centre;

    //reference to database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference databaseReference;

    TextView textViewAddCentre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        ValueEventListener eventListener;
        ArrayList<String> list_of_centre = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        //get all the user input data and store into variable
        editTextAdminUsername = findViewById(R.id.edit_text_signup_admin_username);
        editTextAdminPassword = findViewById(R.id.edit_text_signup_admin_password);
        editTextAdminEmail = findViewById(R.id.edit_text_signup_admin_email);
        editTextAdminFullName = findViewById(R.id.edit_text_signup_admin_fullName);
        editTextAdminStaffID = findViewById(R.id.edit_text_signup_admin_staffID);

        textViewCentreName = findViewById(R.id.text_view_centre_name);
        textViewCentreAddress = findViewById(R.id.text_view_centre_address);

        spinner_centre = findViewById(R.id.spinner_adminCentre);
        databaseReference = FirebaseDatabase.getInstance().getReference("centre_name");

        textViewAddCentre = findViewById(R.id.text_view_admin_addNewCentre);

        db.collection("Centres")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list_of_centre.add(document.getString("centre_name"));
                            }
                            ArrayAdapter<String> center_adapter = new ArrayAdapter<String>(AdminSignupActivity.this, android.R.layout.simple_spinner_item, list_of_centre);
                            center_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_centre.setAdapter(center_adapter);

                            spinner_centre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String item = list_of_centre.get(position);
                                    textViewCentreName.setText(item);

                                    CollectionReference deliveryRef = db.collection("Centres");
                                    Query nameQuery = deliveryRef.whereEqualTo("centre_name",item);
                                    nameQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String id = document.getString("centre_address");
                                                    textViewCentreAddress.setText(id);
                                                }
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                });
    }

    //when the sign up button in admin sign up is click
    public void signUpButtonOnClick(View view) {

        String  adminUsername = editTextAdminUsername.getText().toString();
        String adminPassword = editTextAdminPassword.getText().toString();
        String adminFullName = editTextAdminFullName.getText().toString();
        String adminEmail = editTextAdminEmail.getText().toString();
        String adminStaffID = editTextAdminStaffID.getText().toString();
        boolean check = validationInfo(adminUsername,adminPassword,adminFullName,adminEmail,adminStaffID);

        if (check==true) {
            Admin admin = new Admin();
            admin.setAdmin_ID(UUID.randomUUID().toString());
            admin.setAdmin_username(editTextAdminUsername.getText().toString());
            admin.setAdmin_password(editTextAdminPassword.getText().toString());
            admin.setAdmin_fullName(editTextAdminFullName.getText().toString());
            admin.setAdmin_email(editTextAdminEmail.getText().toString());
            admin.setAdmin_staffID(editTextAdminStaffID.getText().toString());
            admin.setAdmin_centre(textViewCentreName.getText().toString());

            db.collection(COLLECTION_NAME)
                    .document()
                    .set(admin)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            AdminLoginActivity.ADMIN = admin;
                            //go back to log in once admin sign up
                            startActivity(new Intent(AdminSignupActivity.this, AdminLoginActivity.class));
                            Toast.makeText(AdminSignupActivity.this, "Admin sign up successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminSignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else{
            Toast.makeText(getApplicationContext(), "Please fill up all the fields", Toast.LENGTH_LONG).show();
        }


        //add a new healthcare centre into database
        textViewAddCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminSignupActivity.this, AddNewCentre.class));
            }
        });

    }


//    private void addNewCentre() {
//        final Dialog addNewCenter = new Dialog(AdminSignupActivity.this);
//        addNewCenter.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        addNewCenter.setCancelable(true);
//        addNewCenter.setContentView(R.layout.activity_add_new_centre);
//
//        final EditText centerName = addNewCenter.findViewById(R.id.edit_text_add_new_centreName);
//        final EditText centreAddress = addNewCenter.findViewById(R.id.edit_text_add_new_address);
//        Button addButton = addNewCenter.findViewById(R.id.button_add_new_centre);
//
//        addButton.setOnClickListener(view -> {
//            Healthcare_centre healthcare_centre = new Healthcare_centre();
//            healthcare_centre.setCentre_ID(UUID.randomUUID().toString());
//            healthcare_centre.setCentre_name(centerName.getText().toString());
//            healthcare_centre.setCentre_address(centreAddress.getText().toString());
//
//            db.collection(Healthcare_centre.COLLECTION_NAME)
//                    .document()
//                    .set(healthcare_centre)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            AdminSignupActivity. = administratorCenter;
//                            String center = centername.getText().toString();
//                            String addressCenter = address.getText().toString();
//                            showDetails(center,addressCenter);
//                            addcenter.dismiss();
//                            Toast.makeText(getApplicationContext(), "New centre added successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
////                db.collection("Centres").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
////
////                        }
////                    }
//                });
//
//        });
//        addcenter.show();
//
//    }


    //admin validation function
    private boolean validationInfo(String adminUsername, String adminPassword, String adminFullName, String adminEmail, String adminStaffID) {

        if (adminUsername.length() == 0) {
            editTextAdminUsername.requestFocus();
            editTextAdminUsername.setError("Username cannot be blank");
            return false;
        } else if (adminPassword.length() <= 7) {
            editTextAdminPassword.requestFocus();
            editTextAdminPassword.setError("Password must have at least 7 character long");
            return false;
        } else if (adminEmail.length() == 0) {
            editTextAdminEmail.requestFocus();
            editTextAdminEmail.setError("Email cannot be blank");
            return false;
        } else if (adminFullName.length() == 0) {
            editTextAdminFullName.requestFocus();
            editTextAdminFullName.setError("FullName cannot be blank");
            return false;
        } else if (adminStaffID.length() == 0) {
            editTextAdminStaffID.requestFocus();
            editTextAdminStaffID.setError("Staff ID cannot be blank");
            return false;
        } else {
            return true;
        }

    }



}