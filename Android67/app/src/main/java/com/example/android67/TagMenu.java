package com.example.android67;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add = findViewById(R.id.addTagButton);
        delete = findViewById(R.id.deleteTagButton);
        tagtextview = findViewById(R.id.tagmenuTextView);
        personinput = findViewById(R.id.persontagEditText);
        locinput = findViewById(R.id.locationtagEditText);

        Bundle bundle_from_display = getIntent().getExtras();
        Photo photo_from_display = (Photo) bundle_from_display.getSerializable("photofromdisplay");
        persontags = photo_from_display.getPersonTags();
        locationtags = photo_from_display.getLocationTags();
        updateTextBox();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String person_input_tag = personinput.getText().toString().trim();
                String loc_input_tag = locinput.getText().toString().trim();
                boolean dupe = false;
                if(person_input_tag.length() != 0 && loc_input_tag.length() != 0){
                    Toast.makeText(TagMenu.this, "Please only enter one tag at a time!", Toast.LENGTH_SHORT).show();
                }
                else if(person_input_tag.length()!= 0){
                    for(String tag : persontags){
                        if(person_input_tag.equals(tag)){
                            dupe = true;
                            Toast.makeText(TagMenu.this, "This Tag already exists for this photo!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(!dupe) {
                        persontags.add(person_input_tag);
                        photo_from_display.setPersonTags(persontags);
                        updateTextBox();
                    }
                }
                else if(loc_input_tag.length() != 0 ){
                    for(String tag : locationtags){
                        if(loc_input_tag.equals(tag)){
                            dupe = true;
                            Toast.makeText(TagMenu.this, "This Tag already exists for this photo!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(!dupe){
                        locationtags.add(loc_input_tag);
                        photo_from_display.setLocationTags(locationtags);
                        updateTextBox();
                    }
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String person_input_tag = personinput.getText().toString().trim();
                String loc_input_tag = locinput.getText().toString().trim();
                if(person_input_tag.length() != 0 && loc_input_tag.length() != 0){
                    Toast.makeText(TagMenu.this, "Please only enter one tag at a time!", Toast.LENGTH_SHORT).show();
                }
                else if(person_input_tag.length() != 0){
                    for (String persontag : persontags){
                        if(person_input_tag.equals(persontag)){
                            persontags.remove(person_input_tag);
                            photo_from_display.setPersonTags(persontags);
                            updateTextBox();
                            break;
                        }
                    }
                }
                else if (loc_input_tag.length() != 0){
                    for (String loctag : locationtags){
                        if(loc_input_tag.equals(loctag)){
                            locationtags.remove(loc_input_tag);
                            photo_from_display.setLocationTags(locationtags);
                            updateTextBox();;
                            break;
                        }
                    }
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to put a bundle here so i can put seralizable
                Intent intent = new Intent(TagMenu.this, DisplayPhoto.class);
                intent.putExtra("photo", photo_from_display);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateTextBox(){

        if(!persontags.isEmpty() || !locationtags.isEmpty()){
            String loctagstring = "Location: ";
            String persontagstring = "Person: ";
            Log.d("debugtag", "updatetextbox");
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