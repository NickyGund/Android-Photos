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
    private Album albumfromgallery;
    private Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_photo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        move_button = findViewById(R.id.moveID3);
        to_alb = findViewById(R.id.destInput);
        //get intent data here
        Bundle bundle_from_gallery = getIntent().getExtras();
        String photopath = bundle_from_gallery.getString("photopath");
        albumfromgallery = (Album) bundle_from_gallery.getSerializable("album");
        alblist = (ArrayList<Album>)bundle_from_gallery.getSerializable("alblist");


        //updateDisplay(photopath, albumfromgallery);

        move_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i =0;
                for(Photo photos : albumfromgallery.photolist){
                    if(photos.getPath().equals(photopath)){
                        photo = photos;
                        albumfromgallery.photolist.remove(i);
                    }
                    i++;
                }
                int j =0;
                for(Album alb : alblist){
                    if(alb.getName().equals(albumfromgallery.getName())){
                        alblist.set(j,albumfromgallery);
                    }
                    if(alb.getName().equals(to_alb.getText().toString().trim())){
                        alblist.get(j).photolist.add(photo);
                        Intent intent = new Intent(MovePhoto.this, PhotoGallery.class);
                        Log.d("debugtag", photopath);
                        int requestcode = 3;
                        Bundle bundle = new Bundle();
                        //Log.d("debugtag", "name is " + albumDestinationName);
                        bundle.putSerializable("from_album", alblist);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, requestcode);
                    }
                    j++;
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("album", albumfromgallery);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}