package com.example.alec.tinlip;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_view_notes, mobileArray);
//
//        ListView listView = (ListView) findViewById(R.id.list);
//        listView.setAdapter(adapter);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
        DatabaseOperations DB = new DatabaseOperations(this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Cursor cr = DB.getNotes(DB, loc.getLatitude(), loc.getLongitude());

        final ArrayList<String> dataList = new ArrayList<String>();
        while(cr.moveToNext()){
            double lat = cr.getDouble(1);
            double lon = cr.getDouble(2);
            double currlat = loc.getLatitude();
            double currlon = loc.getLongitude();
            Location noteLocation = new Location("");
            noteLocation.setLatitude(lat);
            noteLocation.setLongitude(lon);
            double distance = loc.distanceTo(noteLocation);
//            double distance = (
//                    3959 * Math.acos (
//                            Math.cos ( Math.toRadians(currlat) )
//                                    * Math.cos( Math.toRadians( lat ) )
//                                    * Math.cos( Math.toRadians( lon ) - Math.toRadians(currlon) )
//                                    + Math.sin ( Math.toRadians(currlat) )
//                                    * Math.sin( Math.toRadians( lat ) )
//                    )
//            );
            if(distance < 5){//5.0/5280){
                dataList.add(cr.getString(0));
            }
        }
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, dataList);
        listview.setAdapter(adapter);


    }

}
