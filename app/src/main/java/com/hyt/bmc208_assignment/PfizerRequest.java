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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PfizerRequest extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list = new ArrayList<>();

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pfizer_request);

        listView = findViewById(R.id.list_view_available_centre);

        drawerLayout = findViewById(R.id.drawer_layout);
        //add data into list
        list.add("columbia medical");
        list.add("sunway medical");
        list.add("subang medical");
        list.add("ponhub medical");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(PfizerRequest.this, android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PfizerRequest.this, "Ok", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PfizerRequest.this, ViewBatches.class);
                startActivity(intent);
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

    //when click on the home page (request vaccination appointment page)
    public void ClickHome(View view){
        //Redirect activity to home page
        redirectActivity(this,RequestVaccination.class);

    }

    //when click on the dashboard (appointment status page)
    public void ClickDashboard(View view){
        //Redirect activity to dashboard
        redirectActivity(this,Dashboard.class);

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