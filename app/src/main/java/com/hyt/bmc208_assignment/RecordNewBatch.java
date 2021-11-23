package com.hyt.bmc208_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class RecordNewBatch extends AppCompatActivity {

    DrawerLayout drawerLayout;
    TableLayout addNewBatchTable;
    Button addNewBatchButton;
    TextView vaccineID;
    TextView vaccineName;
    TextView vaccineManufacturer;
    Spinner mySpinner;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_batch);

        drawerLayout = findViewById(R.id.drawer_layout);
        mySpinner = findViewById(R.id.spinner_1);
        vaccineID = findViewById(R.id.batch_vaccineID);
        vaccineName = findViewById(R.id.batch_vaccineName);
        vaccineManufacturer = findViewById(R.id.batch_vaccineManufacturer);
        addNewBatchTable = findViewById(R.id.table_layout_vaccine);
        addNewBatchButton = findViewById(R.id.add_NewBatchButton);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RecordNewBatch.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vaccines));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        addNewBatchTable.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        addNewBatchTable.setVisibility(View.VISIBLE);
                        vaccineID.setText("V110");
                        vaccineName.setText("Pfizer");
                        vaccineManufacturer.setText("Pfizer Inc");
                        break;
                    case 2:
                        addNewBatchTable.setVisibility(View.VISIBLE);
                        vaccineID.setText("V111");
                        vaccineName.setText("Sinovac");
                        vaccineManufacturer.setText("Sinovac Inc");
                        break;
                    case 3:
                        addNewBatchTable.setVisibility(View.VISIBLE);
                        vaccineID.setText("V112");
                        vaccineName.setText("Astra Zeneca");
                        vaccineManufacturer.setText("Astra Zeneca Inc");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addNewBatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

    //when click on the record new batch
    public void ClickAddNewBatch(View view){
        //Recreate activity
        recreate();

    }

    //when click on the dashboard (appointment status page)
    public void ClickViewVaccineBatch(View view){
        //Redirect activity to dashboard
        redirectActivity(this,ViewVaccineBatch.class);
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