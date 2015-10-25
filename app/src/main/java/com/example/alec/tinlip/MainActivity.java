package com.example.alec.tinlip;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button submit;
    Context context = this;
    public static Location currentLocation;
    TextView locationLong;
    TextView locationLat;
    TextView locationTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationLat = (TextView) findViewById(R.id.textViewLat);
        locationLong = (TextView) findViewById(R.id.textViewLong);
        locationTime = (TextView) findViewById(R.id.textViewTime);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void toAddNote(View view){
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    public void toViewNotes(View view){
        Intent intent = new Intent(this, ViewNotesActivity.class);
        startActivity(intent);
    }

    private void updateText(){
        locationLong.setText("Latitude: " + currentLocation.getLatitude());
        locationLat.setText("Longitude: " + currentLocation.getLongitude());
        locationTime.setText(new Date().toString());
    }
}
