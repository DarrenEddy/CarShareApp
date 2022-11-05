package com.example.testautofill;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import CityReader.City;
import CityReader.Reader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findLeg = findViewById(R.id.findLegButton);
        Button createLeg = findViewById(R.id.createLegButton);
        Button accountButton = findViewById(R.id.accountButton);

        findLeg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), BrowseActivity.class);
                startActivity(nextActivity);
            }
        });


        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), Login.class);
                startActivity(nextActivity);
            }
        });





    }




}