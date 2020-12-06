package com.example.android67;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TagMenu extends AppCompatActivity {

   private Button add, delete;
   private TextView tagtextview;
   private EditText personinput, locinput;
   private ArrayList<String> persontags,locationtags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        add = findViewById(R.id.addTagButton);
        delete = findViewById(R.id.deleteTagButton);
        tagtextview = findViewById(R.id.tagTextView);
        personinput = findViewById(R.id.persontagEditText);
        locinput = findViewById(R.id.locationtagEditText);

        Bundle bundle_from_display = getIntent().getExtras();
        Photo photo_from_display = (Photo) bundle_from_display.getSerializable("photo");
        persontags = photo_from_display.getPersonTags();
        locationtags = photo_from_display.getLocationTags();
        udpateTextBox();


    }

    @SuppressLint("SetTextI18n")
    private void udpateTextBox(){

        if(!persontags.isEmpty() || !locationtags.isEmpty()){
            String loctagstring = "Location: ";
            String persontagstring = "Person: ";
            if(persontags.isEmpty()){
                for (String tag : locationtags){
                    loctagstring += tag + ", ";
                    tagtextview.setText(loctagstring + '\n' + persontagstring);
                }
            }
            else if(locationtags.isEmpty()){
                for (String tag : persontags){
                    persontagstring += tag + ", ";
                    tagtextview.setText(loctagstring + '\n' + persontagstring);
                }
            }
            else{
                for(String tag : persontags){
                    persontagstring += tag + ", ";
                }
                for(String loctag : locationtags){
                    loctagstring += loctag + ", ";
                }
                tagtextview.setText(loctagstring + '\n' + persontagstring);

            }
        }
    }
}