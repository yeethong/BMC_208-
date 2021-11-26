package com.hyt.bmc208_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewBatches extends AppCompatActivity {

    DrawerLayout drawerLayout;

    //reference to database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_batches);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_batches);

        drawerLayout = findViewById(R.id.drawer_layout);
        ArrayList<SelectedBatchNo> batch = new ArrayList<SelectedBatchNo>();

        Bundle extras = getIntent().getExtras();
        String centreName = extras.getString("centreName");
        String vaccine = extras.getString("vaccine");
        String patient_username = extras.getString("patient_username");
        String vaccineBatch = "";
        if(vaccine.equals("Pfizer")){
            vaccineBatch="Pfizer_Batch";
        }
        else if (vaccine.equals("Sinovac")){
            vaccineBatch="Sinovac_Batch";
        }
        else if (vaccine.equals("AstraZeneca")){
            vaccineBatch="AstraZeneca_Batch";
        }

        db.collection(vaccineBatch)
                .whereEqualTo("centreName",centreName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        batch.add(new SelectedBatchNo(document.getString("batchNo"), document.getString("expiryDate"), document.getString("quantityAvailable") ));
                        PatientBatchAdapter adapter = new PatientBatchAdapter(batch);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });

//        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(PatientViewBatch.this, AppointmentCalendar.class);
//                //We have to pass key-value parameters
//                intent.putExtra("PatientBatchID", Batches.get(position));
//                intent.putExtra("patientEmail", email);
//                intent.putExtra("center", center);
//                intent.putExtra("vaccine", vaccine);
//                startActivity(intent);
//            }
//        }));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewBatches.this, RecordNewBatch.class);
                startActivity(intent);
            }
        });
    }

    //when the 3 stripe menu is clicked open the menu
    public void ClickMenu(View view) {
        //open drawer
        openDrawer(drawerLayout);

    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //when the logo is clicked close the menu
    public void ClickLogo(View view) {
        //close drawer
        closeDrawer(drawerLayout);

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //when drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //when click on the home page (request vaccination appointment page)
    public void ClickHome(View view) {
        //Redirect activity to home page
        Bundle extras = getIntent().getExtras();
        String patientUsername = extras.getString("patient_username");
        Intent back = new Intent(ViewBatches.this, VaccinationRequest.class);
        back.putExtra("patient_username", patientUsername);
        startActivity(back);

    }

    //when click on the appointment status page
    public void ClickDashboard(View view) {
        //Redirect activity to dashboard
        redirectActivity(this, AppointmentStatus.class);
    }

    //when click on the log out
    public void ClickLogout(View view) {
        //close app
        redirectActivity(this, indexMenu.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //initialize intent
        Intent intent = new Intent(activity, aClass);
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