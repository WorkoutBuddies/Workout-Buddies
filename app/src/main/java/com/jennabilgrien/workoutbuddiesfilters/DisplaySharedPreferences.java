package com.jennabilgrien.workoutbuddiesfilters;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DisplaySharedPreferences extends Activity {

    ListView listView;
    SimpleAdapter adapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        //All of the user preferences are saved as Strings here
        String genderPrefs = prefs.getString("genderpref", "Default list prefs");
        String agePrefs = prefs.getString("agepref", "Default list prefs");
        String fitnessPrefs = prefs.getString("fitnesspref", "Default list prefs");
        String levelPrefs = prefs.getString("levelpref", "Default list prefs");
        String freqPrefs = prefs.getString("freqpref", "Default list prefs");
        String availPrefs = prefs.getString("availpref", "Default list prefs");

        //Initializes a query into the ParseObject dog, where we saved the user info
        ParseQuery<ParseObject> query = ParseQuery.getQuery("dog");

        //Specifies what exactly the query is searching for
        //Thus, below it is searching, it is searching for user with the gender selected in gender prefs
        query.equals(query.whereStartsWith("Gender", genderPrefs));
        //query.equals(query.whereStartsWith("Age", agePrefs));
        query.equals(query.whereStartsWith("FitnessLevel", levelPrefs));
        //query.equals(query.whereStartsWith("GymFrequency", freqPrefs));
        query.equals(query.whereStartsWith("FitnessPlan", fitnessPrefs));
        //query.equals(query.whereStartsWith("Availability", availPrefs));

        setContentView(R.layout.listview_main);
        listView = (ListView) findViewById(R.id.gender);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> matchList, ParseException e) {
                if (e == null) {
                    //Shows number of users found, not displayed to user
                    Log.d("gender", "Retrieved " + matchList.size() + " matches");
                    //this is what will be displayed in the listview
                    List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
                    //setting the information for each user, each user has a different index in userList
                    for (ParseObject person : matchList) {
                        Map<String, String> user = new HashMap<String, String>(2);
                        user.put("name", person.getString("FirstName") + " " + person.getString("LastName"));
                        user.put("info", "Phone Number: " + person.getString("MobileNumber"));
                        userList.add(user);
                    }

                    //creating adapter for the listview
                    adapter = new SimpleAdapter(getApplicationContext(), userList, android.R.layout.simple_list_item_2,
                            new String[] {"name", "info"}, new int[] {android.R.id.text1, android.R.id.text2}){
                        //overriding the getView method to format the list exactly how we want it
                        //black text, changing the font & size
                        @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                            View v = super.getView(position, convertView, parent);
                            TextView tv1 = (TextView)v.findViewById(android.R.id.text1);
                            TextView tv2 = (TextView)v.findViewById(android.R.id.text2);
                            tv1.setTextColor(Color.BLACK);
                            tv2.setTextColor(Color.BLACK);
                            tv1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts1/Raleway-Regular.ttf"));
                            tv2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts1/Raleway-LightItalic.ttf"));
                            tv1.setTextSize(35);
                            tv2.setTextSize(23);
                            return v;
                        }
                    };

                    //setting the adapter to the listview
                    listView.setAdapter(adapter);

                } else {
                    Log.d("gender", "Error: " + e.getMessage());
                }
            }
        });
    }

}