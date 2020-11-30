package com.example.android67;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView albumlistview;
    private String[] albumnames;
    private Button addAlbum, deleteAlbum, renameAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumnames = getResources().getStringArray(R.array.album_array);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.album, albumnames);

        albumlistview = findViewById(R.id.albumList);
        albumlistview.setAdapter(adapter);
        addAlbum = findViewById(R.id.addAlbum);
        deleteAlbum = findViewById(R.id.deleteAlbum);
        renameAlbum = findViewById(R.id.renameAlbum);

        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMenu();
            }
        });
    }
    private void showAddMenu(){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, AddButton.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}