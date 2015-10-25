package com.example.alec.tinlip;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewNotesActivity extends AppCompatActivity {

    LocationManager manager;
    public Location currentLocation;
    ListView listview;
    DatabaseOperations DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        listview = (ListView) findViewById(R.id.listview);
        DB = new DatabaseOperations(this);

        // LISTENER
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
                    updateText();
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
            private static final int FIFTEEN_SECONDS = 1000 * 15;

            protected boolean isBetterLocation(Location location, Location currentBestLocation) {
                if (currentBestLocation == null) {
                    // A new location is always better than no location
                    return true;
                }

                // Check whether the new location fix is newer or older
                long timeDelta = location.getTime() - currentBestLocation.getTime();
                boolean isSignificantlyNewer = timeDelta > FIFTEEN_SECONDS;
                boolean isSignificantlyOlder = timeDelta < -FIFTEEN_SECONDS;
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
        updateText();

        // END LISTENER

        updateText();
    }

    private void updateText() {
        
        Cursor cr = DB.getNotes(DB);

        final ArrayList<String> dataList = new ArrayList<String>();
        while(cr.moveToNext()){
            double lat = cr.getDouble(1);
            double lon = cr.getDouble(2);
            Location noteLocation = new Location("");
            noteLocation.setLatitude(lat);
            noteLocation.setLongitude(lon);
            double distance = currentLocation.distanceTo(noteLocation);

            if(distance < 8){
                dataList.add(cr.getString(0));
            }
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, dataList);
        listview.setAdapter(adapter);
    }
    public void toGoHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toAddNote(View view){
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

}
