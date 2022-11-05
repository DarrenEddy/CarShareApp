package com.example.testautofill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;


import java.util.ArrayList;

import CityReader.City;
import CityReader.Reader;

public class BrowseActivity extends AppCompatActivity {
    ArrayList<City> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        cities= Reader.getCityArrayList(getResources().openRawResource(R.raw.canadacities)); // use of READER class
        String[] autoCompleteFormat = Reader.citiesArray(getResources().openRawResource(R.raw.canadacities));

        //setup for Autocomplete Text Drop Down
        AutoCompleteTextView depart = findViewById(R.id.fromTextView);
        AutoCompleteTextView arrival = findViewById(R.id.toTextView);
        Button search = findViewById(R.id.enterSearch);

        ArrayAdapter<String> adapter = new ArrayAdapter<>((Context) this, android.R.layout.simple_dropdown_item_1line, autoCompleteFormat);
        depart.setAdapter(adapter);
        arrival.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String departText = depart.getText().toString();
                String arrivalText = arrival.getText().toString();

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.browseBox);

                //TextView temp = new TextView(BrowseActivity.this);
                //temp.setText(cities.get(0).toString());//EOD Oct.12 issue crashes when trying to access cities here
                //linearLayout.addView(temp);

                for (City i: cities)
                    {

                        int did = i.compareForId(departText.split(",")[0]);
                        int aid = i.compareForId(arrivalText.split(",")[0]);



                        //issue with montreal might need to not use ASCII version From CSV

                        if(did != 0)
                        {
                            TextView temp = new TextView(BrowseActivity.this);

                           // System.out.println("Departure City ID = "+did);
                            //TODO Search database for Leg Containing this departure ID
                            //TextView temp = new TextView(BrowseActivity.this);
                            temp.setText(i.getName()+": "+i.latAndLon());
                            linearLayout.addView(temp);
                        }


                        if(aid != 0)
                        {
                            TextView temp = new TextView(BrowseActivity.this);

                            //System.out.println("Arrival City ID = "+aid);
                            //TODO Search Remaining database for Leg Containing this arrrival ID
                           // TextView temp = new TextView(BrowseActivity.this);
                            temp.setText(i.getName()+": "+i.latAndLon());
                            linearLayout.addView(temp);
                        }
                    }




            }
        });
    }
}