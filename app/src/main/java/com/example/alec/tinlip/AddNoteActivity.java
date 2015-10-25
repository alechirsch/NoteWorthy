package com.example.alec.tinlip;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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
    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        noteText = (EditText) findViewById(R.id.noteText);
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        currentLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            @Override
            public void onProviderEnabled(String provider) {

            }
            @Override
            public void onProviderDisabled(String provider) {

            }


        };
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
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

        // Format and push to sqlLite

        if(currentLocation != null) {
            DB.insert(DB, currentLocation.getLatitude(), currentLocation.getLongitude(), noteText.getText().toString());
        }
        // Empty the text box

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // TODO(kgeffen) Display the confirmation snapbar
    }


    public void toViewNotes(View view){
        Intent intent = new Intent(this, ViewNotesActivity.class);
        startActivity(intent);
    }

}
