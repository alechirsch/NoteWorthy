package com.example.alec.tinlip;

import android.Manifest;
import android.content.Context;
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

public class MainActivity extends AppCompatActivity {
    Button submit;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView locationLong = (TextView) findViewById(R.id.textViewLong);
        final TextView locationLat = (TextView) findViewById(R.id.textViewLat);
        final TextView locationAlt = (TextView) findViewById(R.id.textViewAlt);
        final TextView locationNote = (TextView) findViewById(R.id.textViewNote);
        final TextView locationTime = (TextView) findViewById(R.id.textViewTime);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        final Location loc = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        DatabaseOperations DB = new DatabaseOperations(context);
        DB.insert(DB, loc.getLatitude(), loc.getLongitude(), loc.getAltitude(), "The Note!!!");

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
//                final Location loc = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//            }
//        });
        Cursor cr = DB.getNote(DB);
        cr.moveToFirst();
        locationLong.setText("Latitude: " + cr.getString(0));
        locationLat.setText("Longitude: " + cr.getString(1));
        locationAlt.setText("Altitude: " + cr.getString(2));
        locationNote.setText("Note: " + cr.getString(3));
        locationTime.setText("Time: " + cr.getString(4));

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
}
