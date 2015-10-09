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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class userProfile extends AppCompatActivity {

    EditText usernameEditText, passwordEditText, firstnameEditText, lastnameEditText, emailEditText, mobileEditText, ageEditText, genderEditText ;
    Button SubmitButton, PicButton;
    ImageView image ;
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

        SubmitButton = (Button) findViewById(R.id.submit) ;
        PicButton = (Button) findViewById(R.id.picture) ;
        image = (ImageView) findViewById(R.id.upload) ;
        usernameEditText = (EditText) findViewById(R.id.username) ;
        passwordEditText = (EditText) findViewById(R.id.password) ;
        firstnameEditText = (EditText) findViewById(R.id.firstname) ;
        lastnameEditText = (EditText) findViewById(R.id.lastname) ;
        emailEditText = (EditText) findViewById(R.id.email) ;
        mobileEditText = (EditText) findViewById(R.id.mobile) ;
        ageEditText = (EditText) findViewById(R.id.age) ;
        genderEditText = (EditText) findViewById(R.id.gender) ;

        SubmitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String username = usernameEditText.getText().toString() ;
                final String password = passwordEditText.getText().toString() ;
                final String firstname = firstnameEditText.getText().toString() ;
                final String lastname = lastnameEditText.getText().toString() ;
                final String email = emailEditText.getText().toString() ;
                final String mobile = mobileEditText.getText().toString() ;
                final String age = ageEditText.getText().toString() ;
                final String gender = genderEditText.getText().toString() ;
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
                }else if ( age.length() == 0 ) {
                    ageEditText.requestFocus() ;
                    ageEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( !age.matches("[0-9]+") || (Integer.parseInt(age) <= 0 || Integer.parseInt(age) > 120 ) ) {
                    ageEditText.requestFocus() ;
                    ageEditText.setError("INVALID AGE!");
                }else if ( gender.length() == 0 ) {
                    genderEditText.requestFocus() ;
                    genderEditText.setError("FIELD CANNOT BE EMPTY!");
                }else if ( !gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female") ) {
                    genderEditText.requestFocus() ;
                    genderEditText.setError("ENTER ONLY \"MALE\" OR \"FEMALE\"!");
                }else {
                    Toast.makeText(userProfile.this, "VALIDATION SUCCESSFUL!", Toast.LENGTH_LONG).show();
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
}
