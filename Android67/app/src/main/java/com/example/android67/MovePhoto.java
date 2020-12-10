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

import java.util.ArrayList;



public class MovePhoto extends AppCompatActivity {
    private Button move_button;
    private EditText to_alb;
    private ArrayList<Album> alblist;
    private ArrayList<Photo> photolist;
    private ArrayList<Photo> mainlist;
    private Album albumfromgallery;
    private Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_photo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        move_button = findViewById(R.id.moveID3);
        to_alb = findViewById(R.id.destInput);
        //get intent data here
        Bundle bundle_from_gallery = getIntent().getExtras();
        String photopath = bundle_from_gallery.getString("photopath");
        albumfromgallery = (Album) bundle_from_gallery.getSerializable("album");
        alblist = (ArrayList<Album>)bundle_from_gallery.getSerializable("alblist");
        mainlist = albumfromgallery.getPhotolist();

        //updateDisplay(photopath, albumfromgallery);

        move_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i =0;
                i=0;
                for(Photo photos : mainlist){
                    if(photos.getPath().equals(photopath)){
                        photo = new Photo(photos.getPath(), photos.getLocationTags(), photos.getPersonTags());
                        mainlist.remove(photos);
                        albumfromgallery.setPhotolist(mainlist);
                    }
                    i++;
                }
                for(Album alb : alblist){
                    if(alb.getName().equals(to_alb.getText().toString().trim())){
                        photolist = alb.getPhotolist();
                        photolist.add(photo);
                        alb.setPhotolist(photolist);
                        for(Photo photo : alb.getPhotolist()){
                            Log.d("debugtag", photo.getPath());
                        }
                        Intent intent = new Intent(MovePhoto.this, PhotoGallery.class);
                        Bundle bundle = new Bundle();
                        //Log.d("debugtag", "name is " + albumDestinationName);
                        bundle.putSerializable("album", albumfromgallery);
                        bundle.putSerializable("alblist", alblist);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("album", albumfromgallery);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}