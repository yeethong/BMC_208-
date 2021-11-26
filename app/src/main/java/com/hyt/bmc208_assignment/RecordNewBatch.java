package com.hyt.bmc208_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

//admin use case 2 record new vaccine batch
public class RecordNewBatch extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DrawerLayout drawerLayout;
    TableLayout addNewBatchTable;
    Button selectVaccineButton;
    TextView vaccineID;
    TextView vaccineName;
    TextView vaccineManufacturer;
    Spinner mySpinner;

    LinearLayout newBatchNo;
    LinearLayout newBatchExpiryDate;
    LinearLayout newBatchQuantityAvailable;
    Button recordBatchButton;

    // allow admin to enter new batch info
    EditText editTextBatchNo;
    Button expiryDateButton;
    DatePickerDialog.OnDateSetListener setListener;
    EditText editTextBatchQuantityAvailable;
    TextView displayExpiryDate;

    //reference to database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_batch);

        Bundle extras = getIntent().getExtras();
        String centre = extras.getString("centre_name");

        drawerLayout = findViewById(R.id.drawer_layout);
        mySpinner = findViewById(R.id.spinner_1);
        vaccineID = findViewById(R.id.batch_vaccineID);
        vaccineName = findViewById(R.id.batch_vaccineName);
        vaccineManufacturer = findViewById(R.id.batch_vaccineManufacturer);
        addNewBatchTable = findViewById(R.id.table_layout_vaccine);
        selectVaccineButton = findViewById(R.id.select_vaccine_button);

        //the linearlayout for batch
        newBatchNo = findViewById(R.id.new_batchNo);
        newBatchExpiryDate = findViewById(R.id.new_batch_expiryDate);
        newBatchQuantityAvailable = findViewById(R.id.new_batch_quantityAvailable);
        recordBatchButton = findViewById(R.id.record_button);

        //admin input for batch
        //this display is to show user selected expiry date
        editTextBatchNo = findViewById(R.id.edit_text_new_batchNo);
        expiryDateButton = findViewById(R.id.expirydate_button);
        displayExpiryDate = findViewById(R.id.display_expirydate);
        editTextBatchQuantityAvailable = findViewById(R.id.edit_text_batch_quantityAvailable);


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
                        newBatchNo.setVisibility(View.INVISIBLE);
                        newBatchExpiryDate.setVisibility(View.INVISIBLE);
                        newBatchQuantityAvailable.setVisibility(View.INVISIBLE);
                        recordBatchButton.setVisibility(View.INVISIBLE);
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

        //to show the text field of adding new batch after selecting the select button
        selectVaccineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newBatchNo.setVisibility(View.VISIBLE);
                newBatchExpiryDate.setVisibility(View.VISIBLE);
                newBatchQuantityAvailable.setVisibility(View.VISIBLE);
                recordBatchButton.setVisibility(View.VISIBLE);
            }
        });

        expiryDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        //to add a new batch into database
        recordBatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String batchNo = editTextBatchNo.getText().toString();
                String quantityAvailable = editTextBatchQuantityAvailable.getText().toString();
//                String expiryDate = displayExpiryDate.getText().toString();
//                PfizerBatch(batchNo,quantityAvailable,centre);
                boolean check = validationInfo(batchNo,quantityAvailable);

                if (vaccineID.getText().toString().equals("V110") && check == true){
                    PfizerBatch pfizerBatch = new PfizerBatch();
                    pfizerBatch.setPfizer_batch_ID(UUID.randomUUID().toString());
                    pfizerBatch.setBatchNo(batchNo);
                    pfizerBatch.setCentreName(centre);
                    pfizerBatch.setExpiryDate(displayExpiryDate.getText().toString());
                    pfizerBatch.setQuantityAvailable(quantityAvailable);

                    db.collection(PfizerBatch.COLLECTION_NAME)
                            .document()
                            .set(pfizerBatch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
//                                  RecordNewBatch = pfizerBatch;
                                    Toast.makeText(RecordNewBatch.this, "New Batch for Pfizer Added Successfully", Toast.LENGTH_SHORT).show();
                                    editTextBatchNo.getText().clear();
                                    editTextBatchQuantityAvailable.getText().clear();
                                    displayExpiryDate.setText("");
                                    newBatchNo.setVisibility(View.INVISIBLE);
                                    newBatchExpiryDate.setVisibility(View.INVISIBLE);
                                    newBatchQuantityAvailable.setVisibility(View.INVISIBLE);
                                    recordBatchButton.setVisibility(View.INVISIBLE);
                                    addNewBatchTable.setVisibility(View.INVISIBLE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RecordNewBatch.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (vaccineID.getText().toString().equals("V111") && check == true){
                    SinovacBatch sinovacBatch = new SinovacBatch();
                    sinovacBatch.setSinovac_batch_ID(UUID.randomUUID().toString());
                    sinovacBatch.setBatchNo(batchNo);
                    sinovacBatch.setCentreName(centre);
                    sinovacBatch.setExpiryDate(displayExpiryDate.getText().toString());
                    sinovacBatch.setQuantityAvailable(quantityAvailable);

                    db.collection(SinovacBatch.COLLECTION_NAME)
                            .document()
                            .set(sinovacBatch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RecordNewBatch.this, "New Batch for Sinovac Added Successfully", Toast.LENGTH_SHORT).show();
                                    editTextBatchNo.getText().clear();
                                    editTextBatchQuantityAvailable.getText().clear();
                                    displayExpiryDate.setText("");
                                    newBatchNo.setVisibility(View.INVISIBLE);
                                    newBatchExpiryDate.setVisibility(View.INVISIBLE);
                                    newBatchQuantityAvailable.setVisibility(View.INVISIBLE);
                                    recordBatchButton.setVisibility(View.INVISIBLE);
                                    addNewBatchTable.setVisibility(View.INVISIBLE);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RecordNewBatch.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else if (vaccineID.getText().toString().equals("V112") && check == true){
                    AstraBatch astraBatch = new AstraBatch();
                    astraBatch.setAstraZeneca_batch_ID(UUID.randomUUID().toString());
                    astraBatch.setBatchNo(batchNo);
                    astraBatch.setCentreName(centre);
                    astraBatch.setExpiryDate(displayExpiryDate.getText().toString());
                    astraBatch.setQuantityAvailable(quantityAvailable);

                    db.collection(AstraBatch.COLLECTION_NAME)
                            .document()
                            .set(astraBatch)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RecordNewBatch.this, "New Batch for AstraZeneca Added Successfully", Toast.LENGTH_SHORT).show();
                                    editTextBatchNo.getText().clear();
                                    editTextBatchQuantityAvailable.getText().clear();
                                    displayExpiryDate.setText("");

                                    newBatchNo.setVisibility(View.INVISIBLE);
                                    newBatchExpiryDate.setVisibility(View.INVISIBLE);
                                    newBatchQuantityAvailable.setVisibility(View.INVISIBLE);
                                    recordBatchButton.setVisibility(View.INVISIBLE);
                                    addNewBatchTable.setVisibility(View.INVISIBLE);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RecordNewBatch.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please fill up all the fields", Toast.LENGTH_LONG).show();
                }


            }

            //validate for batch no and quantity available
            private boolean validationInfo(String batchNo, String quantityAvailable) {

                if (batchNo.length() == 0) {
                    editTextBatchNo.requestFocus();
                    editTextBatchNo.setError("Batch No cannot be blank");
                    return false;
                } else if (quantityAvailable.length() == 0) {
                    editTextBatchQuantityAvailable.requestFocus();
                    editTextBatchQuantityAvailable.setError("Quantity cannot be empty");
                    return false;
                }
                else {
                    return true;
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        displayExpiryDate.setText(currentDateString);

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

    //when click on view list of vaccine batch page
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