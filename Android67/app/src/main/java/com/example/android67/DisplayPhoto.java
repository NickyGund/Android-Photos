package com.example.android67;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayPhoto extends AppCompatActivity {

    private Button tagbutton;
    private FrameLayout frameLayout;
    private TextView tagtextbox;
    private ImageButton backarrow, forwardarrow;
    private ArrayList<String> loctags, persontags;
    private ArrayList<Photo> alb;
    private int pos_in_alb;
    private int albsize;
    private static int TAGMENUCODE = 1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tagbutton = findViewById(R.id.tagButton);
        frameLayout = findViewById(R.id.frameLay);
        tagtextbox = findViewById(R.id.tagTextView);
        backarrow = findViewById(R.id.backButton);
        forwardarrow = findViewById(R.id.forwardButton);
        loctags = new ArrayList<>();
        persontags = new ArrayList<>();

        //get intent data here
        Bundle bundle_from_gallery = getIntent().getExtras();
        String photopath = bundle_from_gallery.getString("photopath");
        Album albumfromgallery = (Album) bundle_from_gallery.getSerializable("album");
        alb = albumfromgallery.getPhotolist();
        albsize = alb.size();
        updateDisplay(photopath, albumfromgallery);



        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos_in_alb == 0){
                    Toast.makeText(DisplayPhoto.this,"There are no photos before this one!", Toast.LENGTH_SHORT).show();
                }
                else{
                    pos_in_alb -= 1;
                    Photo backphoto = alb.get(pos_in_alb);
                    String backphotopath = backphoto.getPath();
                    frameLayout.removeAllViews();
                    updateDisplay(backphotopath, albumfromgallery);
                }

            }
        });


        forwardarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pos_in_alb + 1 >= albsize){
                    Toast.makeText(DisplayPhoto.this, "There are no photos after this", Toast.LENGTH_SHORT).show();
                }
                else{
                    pos_in_alb += 1;
                    Photo forwardphoto = alb.get(pos_in_alb);
                    String forwardphotopath = forwardphoto.getPath();
                    frameLayout.removeAllViews();
                    updateDisplay(forwardphotopath, albumfromgallery);
                }
            }
        });

        tagbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = alb.get(pos_in_alb);
                Bundle bundle = new Bundle();
                bundle.putSerializable("photo", photo);
                Intent intent = new Intent(DisplayPhoto.this, TagMenu.class);
                startActivityForResult(intent, TAGMENUCODE);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need to put a bundle here so i can put seralizable
                Intent intent = new Intent();
                intent.putExtra("album", albumfromgallery);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateDisplay(String path, Album album) {
        String loctagstring = "Location: ";
        String persontagstring = "Person: ";
        pos_in_alb = 0;
        Uri myuri = Uri.parse(path);
        ImageView imageView = new ImageView(this);
        imageView.setImageURI(myuri);
        frameLayout.addView(imageView);
        for(Photo photo : album.getPhotolist()){
            if(photo.getPath().equals(path)){
                loctags = photo.getLocationTags();
                persontags = photo.getPersonTags();
                if(loctags.isEmpty() && persontags.isEmpty()){
                    break;
                }
                else if(loctags.isEmpty() && !persontags.isEmpty()){
                    for (String string : persontags){
                        persontagstring += string + ", ";
                        tagtextbox.setText(persontagstring + '\n' + loctagstring);
                    }
                }
                else if(!loctags.isEmpty() && persontags.isEmpty()){
                    for (String string : loctags){
                        loctagstring += string + ", ";
                        tagtextbox.setText(loctagstring + "\n" + persontagstring);
                    }
                }
                else{
                    for (String personstring : persontags){
                        persontagstring += personstring + ", ";
                    }
                    for (String locstring : loctags){
                        loctagstring += locstring + ", ";
                    }
                    tagtextbox.setText(persontagstring + '\n' + loctagstring);
                }
            }
            else{
                pos_in_alb += 1;
            }
        }

    }
}