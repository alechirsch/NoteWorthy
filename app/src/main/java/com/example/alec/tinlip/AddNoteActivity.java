package com.example.alec.tinlip;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {

    EditText noteText;
    DatabaseOperations DB = new DatabaseOperations(this);
    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noteText = (EditText) findViewById(R.id.noteText);
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    // TODO(kgeffen) Maybe, see how it looks before putting in work
    // 
    // Whenever activity is viewed it should reset the textbox to empty
    // http://stackoverflow.com/questions/18703841/call-method-on-activity-load-android
    

    // TODO(kgeffen)
    // Submit button interacts with sql, adding the note

    public void addNote(View v) {
        // If text box had no content, don't add the note
        // TODO(kgeffen) Get the text field
        if( noteText.getText().toString().equals("") ) {
            return;
        }

        // Add entry to db
        // Get gps data
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = MainActivity.currentLocation;

        // Format and push to sqlLite

        DB.insert(DB, location.getLatitude(), location.getLongitude(), noteText.getText().toString());

        // Empty the text box

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // TODO(kgeffen) Display the confirmation snapbar
    }

}
