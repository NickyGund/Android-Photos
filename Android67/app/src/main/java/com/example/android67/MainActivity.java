package com.example.android67;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView albumlistview;
    private String[] albumnames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumnames = getResources().getStringArray(R.array.album_array);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.album, albumnames);

        albumlistview = findViewById(R.id.albumList);
        albumlistview.setAdapter(adapter);
    }
}