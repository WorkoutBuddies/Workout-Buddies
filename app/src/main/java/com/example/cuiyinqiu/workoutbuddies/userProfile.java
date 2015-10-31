package com.example.cuiyinqiu.workoutbuddies;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.provider.MediaStore;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class userProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText usernameEditText, passwordEditText, firstnameEditText, lastnameEditText, emailEditText, mobileEditText ;
    private Spinner agePicker, genderPicker, fitnessPlanPicker, fitnessLevelPicker, gymFrequencyPicker, availabilityPicker ;
    private static final String[] ages = { "Choose Your Age" , "18-25", "26-40", "40-65", "65+"} ;
    private static final String[] genders = { "Choose Your Gender" , "Male" , "Female", "Neutral" } ;
    private static final String[] fitnessPlan = { "Choose Your Fitness Plan", "Weight Loss", "Muscle Gain", "Maintenance" } ;
    private static final String[] fitnessLevel = { "Choose Your Fitness Level", "Beginner", "Intermediate", "Expert" } ;
    private static final String[] gymFrequency = { "Choose Your Gym Frequency", "1-2 Days Per Week", "3-4 Days Per Week", "5-7 Days Per Week" } ;
    private static final String[] availability = { "Choose Your Availability", "5am - 9am", "9am - 12 pm", "12pm - 4pm", "4pm - 7pm", "7pm - 12am" } ;
    private Button SubmitButton, PicButton, CancelButton;
    private ImageView image ;
    private static int LOAD_IMAGE_RESULTS = 1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "7soWwAwPRuzUVjRfOv7LF4N6POp7tMe2YrEYuub5", "DWPjnu4B77OIgEgEmYT6CVlTYxizm38C6liNCXPU");


        agePicker = (Spinner)findViewById(R.id.age);
        final ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(userProfile.this,
                android.R.layout.simple_spinner_item,ages);
        genderPicker = (Spinner)findViewById(R.id.gender);
        final ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(userProfile.this,
                android.R.layout.simple_spinner_item,genders) ;
        fitnessPlanPicker = (Spinner)findViewById(R.id.fitnessPlan) ;
        final ArrayAdapter<String> fitnessPlanAdapter = new ArrayAdapter<String>(userProfile.this,
                android.R.layout.simple_spinner_item,fitnessPlan) ;
        fitnessLevelPicker = (Spinner)findViewById(R.id.fitnessLevel) ;
        final ArrayAdapter<String> fitnessLevelAdapter = new ArrayAdapter<String>(userProfile.this,
                android.R.layout.simple_spinner_item,fitnessLevel) ;
        gymFrequencyPicker = (Spinner)findViewById(R.id.gymFrequency) ;
        final ArrayAdapter<String> gymFrequencyAdapter = new ArrayAdapter<String>(userProfile.this,
                android.R.layout.simple_spinner_item,gymFrequency) ;
        availabilityPicker = (Spinner)findViewById(R.id.availability) ;
        final ArrayAdapter<String> availabilityAdapter = new ArrayAdapter<String>(userProfile.this,
                android.R.layout.simple_spinner_item, availability) ;

        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agePicker.setAdapter(ageAdapter);
        agePicker.setOnItemSelectedListener(this);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderPicker.setAdapter(genderAdapter);
        genderPicker.setOnItemSelectedListener(this);
        fitnessPlanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fitnessPlanPicker.setAdapter(fitnessPlanAdapter);
        fitnessPlanPicker.setOnItemSelectedListener(this);
        fitnessLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fitnessLevelPicker.setAdapter(fitnessLevelAdapter);
        fitnessLevelPicker.setOnItemSelectedListener(this);
        gymFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gymFrequencyPicker.setAdapter(gymFrequencyAdapter);
        gymFrequencyPicker.setOnItemSelectedListener(this);
        availabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availabilityPicker.setAdapter(availabilityAdapter);
        availabilityPicker.setOnItemSelectedListener(this);


        SubmitButton = (Button) findViewById(R.id.submit) ;
        CancelButton = (Button) findViewById(R.id.cancel) ;
        PicButton = (Button) findViewById(R.id.picture) ;
        image = (ImageView) findViewById(R.id.upload) ;
        usernameEditText = (EditText) findViewById(R.id.username) ;
        passwordEditText = (EditText) findViewById(R.id.password) ;
        firstnameEditText = (EditText) findViewById(R.id.firstname) ;
        lastnameEditText = (EditText) findViewById(R.id.lastname) ;
        emailEditText = (EditText) findViewById(R.id.email) ;
        mobileEditText = (EditText) findViewById(R.id.mobile) ;

        CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    startActivity(new Intent(userProfile.this, MapsActivity.class));
            }
        });

        SubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String ageSelected = agePicker.getSelectedItem().toString() ;
                final String genderSelected = genderPicker.getSelectedItem().toString() ;
                final String fitnessPlanSelected = fitnessPlanPicker.getSelectedItem().toString();
                final String fitnessLevelSelected = fitnessLevelPicker.getSelectedItem().toString() ;
                final String gymFrequencySelected = gymFrequencyPicker.getSelectedItem().toString() ;
                final String availabilitySelected = availabilityPicker.getSelectedItem().toString() ;
                final String username = usernameEditText.getText().toString() ;
                final String password = passwordEditText.getText().toString() ;
                final String firstname = firstnameEditText.getText().toString() ;
                final String lastname = lastnameEditText.getText().toString() ;
                final String email = emailEditText.getText().toString() ;
                final String mobile = mobileEditText.getText().toString() ;
                final String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if ( username.length() == 0 ) {
                    usernameEditText.requestFocus() ;
                    usernameEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( password.length() == 0 ) {
                    passwordEditText.requestFocus() ;
                    passwordEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( firstname.length() == 0 ) {
                    firstnameEditText.requestFocus() ;
                    firstnameEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( !firstname.matches("[a-zA-Z]+")) {
                    firstnameEditText.requestFocus() ;
                    firstnameEditText.setError("ENTER ONLY ALPHABETICAL CHARACTERS!");
                }else if ( lastname.length() == 0 ) {
                    lastnameEditText.requestFocus() ;
                    lastnameEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( !lastname.matches("[a-zA-Z]+")) {
                    lastnameEditText.requestFocus() ;
                    lastnameEditText.setError("ENTER ONLY ALPHABETICAL CHARACTERS!");
                }else if ( email.length() == 0 ) {
                    emailEditText.requestFocus() ;
                    emailEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( !email.matches(Expn) ) {
                    emailEditText.requestFocus() ;
                    emailEditText.setError("INVALID EMAIL ADDRESS!");
                }else if ( mobile.length() == 0 ) {
                    mobileEditText.requestFocus() ;
                    mobileEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( !mobile.matches("[0-9]+") || mobile.length() != 10 ) {
                    mobileEditText.requestFocus() ;
                    mobileEditText.setError("INVALID MOBILE NUMBER!");
                }else if ( ageSelected.equals(ages[0]) ) {
                    Toast.makeText(userProfile.this, "CHOOSE YOUR AGE!", Toast.LENGTH_SHORT).show();
                }else if ( genderSelected.equals(genders[0]) ) {
                    Toast.makeText(userProfile.this, "CHOOSE YOUR GENDER!", Toast.LENGTH_SHORT).show();
                }else if ( fitnessPlanSelected.equals(fitnessPlan[0]) ) {
                    Toast.makeText(userProfile.this, "CHOOSE YOUR FITNESS PLAN!", Toast.LENGTH_SHORT).show();
                }else if ( fitnessLevelSelected.equals(fitnessLevel[0]) ) {
                    Toast.makeText(userProfile.this, "CHOOSE YOUR FITNESS LEVEL!", Toast.LENGTH_SHORT).show();
                }else if ( gymFrequencySelected.equals(gymFrequency[0]) ) {
                    Toast.makeText(userProfile.this, "CHOOSE YOUR GYM FREQUENCY!", Toast.LENGTH_SHORT).show();
                }else if ( availabilitySelected.equals(availability[0]) ) {
                    Toast.makeText(userProfile.this, "CHOOSE YOUR AVAILABILITY!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(userProfile.this, "VALIDATION SUCCESSFUL!", Toast.LENGTH_LONG).show();
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.signUpInBackground();
                    ParseObject Dog=new ParseObject("dog");
                    Dog.put("Gender",genderSelected );
                    Dog.put("FirstName", firstname);
                    Dog.put("LastName", lastname);
                    Dog.put("Email",email);
                    Dog.put("MobileNumber",mobile);
                    Dog.put("Age",ageSelected);
                    Dog.put("BodyType","Thin");
                    Dog.put("LifeStyle", "Health");
                    Dog.saveInBackground();
                    ParseObject UserInformation=new ParseObject("UserInformation");
                    UserInformation.put("Gender",genderSelected );
                    UserInformation.put("First Name",firstname );
                    UserInformation.put("Last Name", lastname);
                    UserInformation.put("Email",email);
                    UserInformation.put("Mobile Number",mobile );
                    UserInformation.put("Age",ageSelected);
                    UserInformation.put("Body_Type","");
                    UserInformation.put("LifeStyle", "");
                    UserInformation.saveInBackground();
                }
            }
        });

        PicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null ) {
            Uri pickedImage = data.getData() ;
            String[] filePath = { MediaStore.Images.Media.DATA } ;
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null) ;
            cursor.moveToFirst() ;
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0])) ;
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            cursor.close();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
