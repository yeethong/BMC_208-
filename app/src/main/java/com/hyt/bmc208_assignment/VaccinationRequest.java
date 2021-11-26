package com.hyt.bmc208_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//patient use case 3 request vaccination appointment
//a list view to display a list of centres to allow patient to select
public class VaccinationRequest extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TextView centreName;

    //reference to database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_request);

        drawerLayout = findViewById(R.id.drawer_layout);
        centreName = findViewById(R.id.text_view_centre_name);

        //create a list view of centre name
        //the list view is inside activity_vaccination_request.xml
        ListView listCentre = findViewById(R.id.list_view_available_centre);
        List<String> centres = new ArrayList<String>();
        centres.add("abc");

        Bundle extras = getIntent().getExtras();
        if ( extras != null) {
            String value = extras.getString("vaccineName");
            centreName.setText(value);
            String patientUsername = extras.getString("patient_username");


            if (centreName.getText().toString().equals("Pfizer")) {
                db.collection(PfizerBatch.COLLECTION_NAME)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String flag = "not equals";
                                        for (int i = 0; i < centres.size(); i++) {
                                            if (document.getString("centreName").equals(centres.get(i))) {
                                                flag = "equals";
                                            }
                                        }
                                        if (flag.equals("not equals")) {
                                            centres.add(document.getString("centreName"));
                                        }

                                    }
                                    centres.remove(0);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(VaccinationRequest.this, android.R.layout.simple_list_item_1, centres);
                                    listCentre.setAdapter(adapter);
                                    listCentre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Toast.makeText(VaccinationRequest.this, "Ok can do", Toast.LENGTH_SHORT).show();

                                            String selectedCentre = (String) adapterView.getItemAtPosition(i);
                                            Intent batch = new Intent(VaccinationRequest.this, ViewBatches.class);
                                            batch.putExtra("centreName", selectedCentre);
                                            batch.putExtra("vaccine", centreName.getText().toString());
                                            batch.putExtra("patient_username", patientUsername);
                                            startActivity(batch);
                                        }
                                    });

                                }
                            }

                        });

            }
            else if (centreName.getText().toString().equals("Sinovac")){
                db.collection(SinovacBatch.COLLECTION_NAME)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String flag = "not equals";
                                        for (int i = 0; i < centres.size(); i++) {
                                            if (document.getString("centreName").equals(centres.get(i))) {
                                                flag = "equals";
                                            }
                                        }
                                        if (flag.equals("not equals")) {
                                            centres.add(document.getString("centreName"));
                                        }

                                    }
                                    centres.remove(0);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(VaccinationRequest.this, android.R.layout.simple_list_item_1, centres);
                                    listCentre.setAdapter(adapter);
                                    listCentre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Toast.makeText(VaccinationRequest.this, "Ok can do", Toast.LENGTH_SHORT).show();

                                            String selectedCentre = (String) adapterView.getItemAtPosition(i);
                                            Intent batch = new Intent(VaccinationRequest.this, ViewBatches.class);
                                            batch.putExtra("centreName", selectedCentre);
                                            batch.putExtra("vaccine", centreName.getText().toString());
                                            batch.putExtra("patient_username", patientUsername);
                                            startActivity(batch);
                                        }
                                    });

                                }
                            }

                        });

            }
            else if (centreName.getText().toString().equals("AstraZeneca")){
                db.collection(AstraBatch.COLLECTION_NAME)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String flag = "not equals";
                                        for (int i = 0; i < centres.size(); i++) {
                                            if (document.getString("centreName").equals(centres.get(i))) {
                                                flag = "equals";
                                            }
                                        }
                                        if (flag.equals("not equals")) {
                                            centres.add(document.getString("centreName"));
                                        }

                                    }
                                    centres.remove(0);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(VaccinationRequest.this, android.R.layout.simple_list_item_1, centres);
                                    listCentre.setAdapter(adapter);
                                    listCentre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Toast.makeText(VaccinationRequest.this, "Ok can do", Toast.LENGTH_SHORT).show();

                                            String selectedCentre = (String) adapterView.getItemAtPosition(i);
                                            Intent batch = new Intent(VaccinationRequest.this, ViewBatches.class);
                                            batch.putExtra("centreName", selectedCentre);
                                            batch.putExtra("vaccine", centreName.getText().toString());
                                            batch.putExtra("patient_username", patientUsername);
                                            startActivity(batch);
                                        }
                                    });

                                }
                            }

                        });
            }
            else{
                Toast.makeText(VaccinationRequest.this, "Fail", Toast.LENGTH_SHORT).show();
            }

        }




        //add centre data into list
//        list.add("columbia medical");
//        list.add("sunway medical");
//        list.add("subang medical");
//        list.add("ponhub medical");
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(VaccinationRequest.this, android.R.layout.simple_list_item_1,list);
//
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(VaccinationRequest.this, ViewBatches.class);
//                startActivity(intent);
//            }
//        });

    }
    //when the 3 stripe menu is clicked open the menu
    public void ClickMenu(View view){
        //open drawer
        openDrawer(drawerLayout);

    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //when the logo is clicked close the menu
    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //when click on the home page (request vaccination appointment page)
    public void ClickHome(View view){
        //Redirect activity to home page to select the vaccine type
        Bundle extras = getIntent().getExtras();
        String patientUsername = extras.getString("patient_username");
        Intent back = new Intent(this, RequestVaccination.class);
        back.putExtra("patient_username", patientUsername);
        startActivity(back);
    }

    //when click on the dashboard (appointment status page)
    public void ClickDashboard(View view){
        //Redirect activity to dashboard
        redirectActivity(this, AppointmentStatus.class);

    }

    //when click on the log out
    public void ClickLogout(View view){
        //close app
        redirectActivity(this,indexMenu.class);
    }

    public static void redirectActivity(Activity activity, Class aClass){
        //initialize intent
        Intent intent = new Intent(activity,aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //starts activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }
}