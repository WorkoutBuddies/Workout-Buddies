package com.jennabilgrien.workoutbuddiesfilters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

public class Filters extends AppCompatActivity {
    TextView textView;

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //sets the layout for this page, includes background image and format for buttons
        setContentView(R.layout.activity_filters);

        //Initializing buttons
        Button btnPrefs = (Button) findViewById(R.id.btnPrefs);
        Button btnGetPrefs = (Button) findViewById(R.id.btnGetPreferences);
        final Button btnShowMaps = (Button) findViewById(R.id.btnShowMaps);

        textView = (TextView) findViewById(R.id.txtPrefs);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    //Preferences Button
                    case R.id.btnPrefs:
                        Intent intent = new Intent(Filters.this,
                                PrefsActivity.class);
                        startActivity(intent);
                        break;
                    //Show Matches Button
                    case R.id.btnGetPreferences:
                        Intent intent2 = new Intent(Filters.this,
                                DisplaySharedPreferences.class);
                        startActivity(intent2);
                        break;
                    //Show Maps Button
                    case R.id.btnShowMaps:
                        btnShowMaps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(Filters.this, MapsActivity.class));
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        };

        //Allows buttons to be clickable
        btnPrefs.setOnClickListener(listener);
        btnGetPrefs.setOnClickListener(listener);
        btnShowMaps.setOnClickListener(listener);
    }

}


