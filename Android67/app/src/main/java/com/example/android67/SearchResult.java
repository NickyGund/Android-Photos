package com.example.android67;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private LinearLayout linlayout;
    private ArrayList<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linlayout = findViewById(R.id.linear_layout);

       photos = (ArrayList<Photo>) getIntent().getExtras().get("photolist");

       for (Photo photo : photos){
           Log.d("debugtag", "are photos passed?");
           String photostring = photo.getPath();
           Uri myuri = Uri.parse(photostring);
           ImageView imageView = new ImageView(this);
           imageView.setImageURI(myuri);
           linlayout.addView(imageView);
       }

       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

}