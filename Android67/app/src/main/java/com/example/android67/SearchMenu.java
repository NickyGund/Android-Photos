package com.example.android67;

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
import android.widget.Toast;

import java.util.ArrayList;

public class SearchMenu extends AppCompatActivity {
    private Button and, or;
    private EditText persontag, locationtag;
    private ArrayList<Album> mainactalblist;
    private ArrayList<Photo> searchresults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        and = findViewById(R.id.andButton);
        or = findViewById(R.id.orButton);
        persontag = findViewById(R.id.personTagInput);
        locationtag = findViewById(R.id.locationTagInput);
        searchresults = new ArrayList<>();

        mainactalblist = (ArrayList<Album>) getIntent().getExtras().get("albumlist");

        or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personinput = persontag.getText().toString().trim();
                String locationinput = locationtag.getText().toString().trim();
                if (personinput.isEmpty() || locationinput.isEmpty()) {
                    Toast.makeText(SearchMenu.this, "Please have a locationtag and a persontag inputted", Toast.LENGTH_SHORT).show();
                } else {
                    for (Album alb : mainactalblist) {
                        Log.d("debugtag", alb.getName());
                        for (Photo photo : alb.getPhotolist()) {
                            boolean personfound = true;
                            boolean locationfound = true;
                            Log.d("debugtag", photo.getPath());
                            ArrayList<String> persontags = photo.getPersonTags();
                            ArrayList<String> locationtags = photo.getLocationTags();
                            if(persontags.isEmpty()){
                                personfound = false;
                            }
                            for (String tag : persontags) {
                                for (int i = 0; i < personinput.length(); i++) {
                                    Log.d("debugtag", String.valueOf(personinput.charAt(i)));
                                    Log.d("debugtag", String.valueOf(tag.charAt(i)));
                                    if (personinput.charAt(i) == tag.charAt(i)) {
                                        continue;
                                    } else {
                                        personfound = false;
                                    }
                                }
                                if (personfound) {
                                    searchresults.add(photo);
                                    break;
                                }
                            }
                            if (!personfound) {
                                for (String loctag : locationtags) {
                                    for (int i = 0; i < locationinput.length(); i++) {
                                        Log.d("debugtag", String.valueOf(personinput.charAt(i)));
                                        Log.d("debugtag", String.valueOf(loctag.charAt(i)));
                                        if (locationinput.charAt(i) == loctag.charAt(i)) {
                                            continue;
                                        } else {
                                            locationfound = false;
                                        }
                                    }
                                    if (locationfound) {
                                        searchresults.add(photo);
                                    }
                                }

                            }
                        }
                    }
                    // add intent here
                    Intent intent = new Intent(SearchMenu.this, SearchResult.class);
                    intent.putExtra("photolist", searchresults);
                    startActivity(intent);
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}