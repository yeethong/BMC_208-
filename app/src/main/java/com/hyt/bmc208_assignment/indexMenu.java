package com.hyt.bmc208_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class indexMenu extends AppCompatActivity {

    Button patient_button;
    Button admin_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_menu);

        patient_button = findViewById(R.id.patient_button);
        admin_button = findViewById(R.id.admin_button);

        //when patient button is clicked go to patient log in
        patient_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(indexMenu.this, PatientLoginActivity.class));
            }
        });

        //when admin button is clicked go ot admin log in
        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(indexMenu.this, AdminLoginActivity.class));
            }
        });


    }
}