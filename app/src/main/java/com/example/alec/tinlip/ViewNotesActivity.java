package com.example.alec.tinlip;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
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
                currentLocation = location;
                updateText();
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
                dataList.add(cr.getString(0) + "   lat: " + lat +"    lon: " + lon);
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
