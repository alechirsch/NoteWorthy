package com.example.alec.tinlip;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AddNoteActivity extends AppCompatActivity {

    final EditText noteText = (EditText) findViewById(R.id.noteText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
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
    }

    // TODO(kgeffen) Maybe, see how it looks before putting in work
    // 
    // Whenever activity is viewed it should reset the textbox to empty
    // http://stackoverflow.com/questions/18703841/call-method-on-activity-load-android
    

    // TODO(kgeffen)
    // Submit button interacts with sql, adding the note
    @Override
    public void addNote(View v) {
        // If text box had no content, don't add the note
        // TODO(kgeffen) Get the text field
        if( noteText.getText().toString().equals("") ) {
            return;
        }

        // Add entry to db
        // Get gps data


        // Format and push to sqlLite


        // Empty the text box
        noteText.setText("");

        // TODO(kgeffen) Display the confirmation snapbar
    }

}
