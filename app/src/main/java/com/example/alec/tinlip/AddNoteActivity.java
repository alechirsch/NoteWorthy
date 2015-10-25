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
                if(isBetterLocation(location, currentLocation)){
                    currentLocation = location;
                }
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
            private static final int FIVE_SECONDS = 1000 * 5;

            protected boolean isBetterLocation(Location location, Location currentBestLocation) {
                if (currentBestLocation == null) {
                    // A new location is always better than no location
                    return true;
                }

                // Check whether the new location fix is newer or older
                long timeDelta = location.getTime() - currentBestLocation.getTime();
                boolean isSignificantlyNewer = timeDelta > FIVE_SECONDS;
                boolean isSignificantlyOlder = timeDelta < -FIVE_SECONDS;
                boolean isNewer = timeDelta > 0;

                // If it's been more than two minutes since the current location, use the new location
                // because the user has likely moved
                if (isSignificantlyNewer) {
                    return true;
                    // If the new location is more than two minutes older, it must be worse
                } else if (isSignificantlyOlder) {
                    return false;
                }

                // Check whether the new location fix is more or less accurate
                int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
                boolean isLessAccurate = accuracyDelta > 0;
                boolean isMoreAccurate = accuracyDelta < 0;
                boolean isSignificantlyLessAccurate = accuracyDelta > 200;

                // Check if the old and new location are from the same provider
                boolean isFromSameProvider = isSameProvider(location.getProvider(),
                        currentBestLocation.getProvider());

                // Determine location quality using a combination of timeliness and accuracy
                if (isMoreAccurate) {
                    return true;
                } else if (isNewer && !isLessAccurate) {
                    return true;
                } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                    return true;
                }
                return false;
            }

            private boolean isSameProvider(String provider1, String provider2) {
                if (provider1 == null) {
                    return provider2 == null;
                }
                return provider1.equals(provider2);
            }


        };
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
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

    public void toGoHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
