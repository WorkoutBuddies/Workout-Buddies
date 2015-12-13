package com.jennabilgrien.workoutbuddiesfilters;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //The parse client key and application id
        Parse.initialize(this, "7soWwAwPRuzUVjRfOv7LF4N6POp7tMe2YrEYuub5", "DWPjnu4B77OIgEgEmYT6CVlTYxizm38C6liNCXPU");

        //This will be enable a Parse user on the app
        ParseUser.enableAutomaticUser();
        //ParseUser.getCurrentUser().saveInBackground();

        //Sets default ACL to the Parse user
        //Parse user is allowed to see others with the proper query
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
