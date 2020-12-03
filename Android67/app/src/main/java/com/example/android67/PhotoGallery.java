package com.example.android67;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class PhotoGallery extends AppCompatActivity {

    private Button add, delete, display;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add = findViewById(R.id.addButton);
        delete = findViewById(R.id.delButton);
        display = findViewById(R.id.displayButton);
        relativeLayout = findViewById(R.id.rel_layout);


    }
}