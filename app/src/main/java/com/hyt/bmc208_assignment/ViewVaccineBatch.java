package com.hyt.bmc208_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class ViewVaccineBatch extends AppCompatActivity {

    //initialize variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vaccine_batch);

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

    public void ClickAddNewBatch(View view){
        //redirect activity to add new batch
        RequestVaccination.redirectActivity(this,RecordNewBatch.class);
    }

    public void ClickViewVaccineBatch(View view) {
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