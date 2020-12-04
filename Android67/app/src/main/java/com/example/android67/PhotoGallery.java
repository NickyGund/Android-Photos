package com.example.android67;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PhotoGallery extends AppCompatActivity {

    private Button add, delete, display;
    private RelativeLayout relativeLayout;
    private static final int PHOTOPICKCODE = 1;
    private Album album_from_main;
    private ArrayList<Photo> photoalb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle_from_main = getIntent().getExtras();
        album_from_main = (Album) bundle_from_main.getSerializable("album");
        photoalb = album_from_main.getPhotolist();

        add = findViewById(R.id.addPhoto);
        delete = findViewById(R.id.delPhoto);
        display = findViewById(R.id.displayPhoto);
        relativeLayout = findViewById(R.id.rel_layout);

        for(Photo photo : photoalb){
            Log.d("debugtag", photo.getPath());
            String photopath = photo.getPath();
            Uri myuri = Uri.parse(photopath);
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(myuri);
            relativeLayout.addView(imageView);
        }
        Log.d("debugtag", album_from_main.getName());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTOPICKCODE);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("album", album_from_main);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTOPICKCODE && data != null){
            String uri = data.getData().toString();
            Uri myURI = Uri.parse(uri);
            Photo newphoto = new Photo(uri);
            photoalb.add(newphoto);
            album_from_main.setPhotolist(photoalb);
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(myURI);
            relativeLayout.addView(imageView);
        }
        }



}