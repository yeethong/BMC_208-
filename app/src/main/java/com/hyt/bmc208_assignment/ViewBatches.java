package com.hyt.bmc208_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewBatches extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_batches);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_batches);

        drawerLayout = findViewById(R.id.drawer_layout);

        AvailableBatches[] availableBatches = {
                new AvailableBatches("B111", "1/1/22", "1199"),
                new AvailableBatches("B111", "1/1/22", "899"),
                new AvailableBatches("B111", "1/1/22", "799"),
                new AvailableBatches("B111", "1/1/22", "200"),
                new AvailableBatches("B111", "1/1/22", "100")
        };
        AvailableBatchesAdapter adapter = new AvailableBatchesAdapter(availableBatches);
        recyclerView.setAdapter(adapter);
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
        redirectActivity(this, RequestVaccination.class);

    }

    //when click on the dashboard (appointment status page)
    public void ClickDashboard(View view) {
        //Redirect activity to dashboard
        redirectActivity(this, Dashboard.class);

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