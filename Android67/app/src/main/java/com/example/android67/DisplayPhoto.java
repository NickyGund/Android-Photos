package com.example.android67;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
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
    private Album albumfromgallery;

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
        tagtextbox = findViewById(R.id.tagboxTextView);
        backarrow = findViewById(R.id.backButton);
        forwardarrow = findViewById(R.id.forwardButton);


        //get intent data here
        Bundle bundle_from_gallery = getIntent().getExtras();
        String photopath = bundle_from_gallery.getString("photopath");
        albumfromgallery = (Album) bundle_from_gallery.getSerializable("album");
        alb = albumfromgallery.getPhotolist();
        albsize = alb.size();
        updateDisplay(photopath, albumfromgallery);


        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos_in_alb == 0) {
                    Toast.makeText(DisplayPhoto.this, "There are no photos before this one!", Toast.LENGTH_SHORT).show();
                } else {
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
                Log.d("debugtag", String.valueOf(pos_in_alb));
                if(albsize == 1){
                    Toast.makeText(DisplayPhoto.this, "Only one photo in the album", Toast.LENGTH_SHORT).show();
                }
                else if (pos_in_alb + 1 >= albsize) {
                    Toast.makeText(DisplayPhoto.this, "There are no photos after this", Toast.LENGTH_SHORT).show();
                } else {
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
                Intent intent = new Intent(DisplayPhoto.this, TagMenu.class);
                Photo photo = alb.get(pos_in_alb);
                Log.d("debugtag", photo.getPath());
                Bundle bundle = new Bundle();
                bundle.putSerializable("photofromdisplay", photo);
                intent.putExtras(bundle);
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
        Log.d("debugtag", "updatedisplay");
        String loctagstring = "Location: ";
        String persontagstring = "Person: ";
        pos_in_alb = 0;
        boolean found = false;
        Uri myuri = Uri.parse(path);
        ImageView imageView = new ImageView(this);
        imageView.setImageURI(myuri);
        frameLayout.addView(imageView);
        for (Photo photo : album.getPhotolist()) {
            if (photo.getPath().equals(path)) {
                Log.d("debugtag", "posvalue is " +  pos_in_alb);
                loctags = photo.getLocationTags();
                persontags = photo.getPersonTags();
                if (!persontags.isEmpty() || !loctags.isEmpty()) {
                    for (String tag : persontags) {
                        persontagstring += tag + ", ";
                    }
                    for (String loctag : loctags) {
                        Log.d("debugtag", loctag);
                        loctagstring += loctag + ", ";
                    }
                    tagtextbox.setText(loctagstring + '\n' + persontagstring);
                    }
                break;
                }
            else {
                pos_in_alb += 1;
            }
        }
        Log.d("debugtag", "posvalue is " +  pos_in_alb);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("debugtag", "do i get to the activity result?");
        if(requestCode == 1){
            Log.d("debugtag", "do i get into the requestcode?");
            Photo photofrommenu = (Photo) data.getSerializableExtra("photo");
            String photostring = photofrommenu.getPath();
            int counter = 0;
            for (Photo photo :  alb){
                if(photo.getPath().equals(photofrommenu.getPath())){
                    alb.set(counter, photofrommenu);
                }
                else{
                    counter += 1;
                }
            }
            updateDisplay(photostring, albumfromgallery);
        }
    }
}


