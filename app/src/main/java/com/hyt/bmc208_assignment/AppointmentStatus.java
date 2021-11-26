package com.hyt.bmc208_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class AppointmentStatus extends AppCompatActivity {

    //initialize variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_status);

        //assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view){
        //open drawer
        RequestVaccination.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        //close drawer
        RequestVaccination.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        //redirect activity to home aka request vaccination appointment page
        RequestVaccination.redirectActivity(this, RequestVaccination.class);
    }

    public void ClickDashboard(View view) {
        //recreate activity
        recreate();
    }

    public void ClickLogout(View view){
        //close app
        RequestVaccination.redirectActivity(this,indexMenu.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RequestVaccination.closeDrawer(drawerLayout);
    }
}
