package com.example.android67;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView albumlistview;
    private String[] albumnames;
    private Button addAlbum, deleteAlbum, renameAlbum;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> alblist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> alblist = new ArrayList<>();
        albumnames = getResources().getStringArray(R.array.album_array);
        adapter = new ArrayAdapter<>(this, R.layout.album, alblist);

        albumlistview = findViewById(R.id.albumList);
        albumlistview.setAdapter(adapter);
        addAlbum = findViewById(R.id.addAlbum);
        deleteAlbum = findViewById(R.id.deleteAlbum);
        renameAlbum = findViewById(R.id.renameAlbum);

        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddButton.class);
                intent.putStringArrayListExtra("albumlist", alblist);
                int requestCode = 1;
                startActivityForResult(intent, requestCode);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Log.d("degugtag", "do i get here");
        }
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String newalbum = data.getStringExtra("album");
                Log.d("debugtag", newalbum);
                adapter.add(newalbum);
                adapter.notifyDataSetChanged();

            }
        }

    }




}