package com.example.android67;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

class Album implements Serializable {

    String name;
    ArrayList<Photo> photolist;
    Album(String name, ArrayList<Photo> photolist){
        this.name = name;
        this.photolist = photolist;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String toString(){ //used by listview
        return name;
    }

}

class Photo{
    String path;
    Photo(String path){
        this.path = path;
    }
}
public class MainActivity extends AppCompatActivity {

    private ListView albumlistview;
   // private String[] albumnames;
    private Button addAlbum, deleteAlbum, renameAlbum;
    private ArrayAdapter<Album> adapter;
    private ArrayList<Album> alblist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Album> alblist = new ArrayList<>();
        //albumnames = getResources().getStringArray(R.array.album_array);
        adapter = new ArrayAdapter<Album>(this, R.layout.album, alblist);

        albumlistview = findViewById(R.id.albumList);
        albumlistview.setAdapter(adapter);
        addAlbum = findViewById(R.id.addAlbum);
        renameAlbum = findViewById(R.id.renameAlbum);

        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAlbButton.class);
                intent.putExtra("albumlist", alblist);
                int requestCode = 1;
                startActivityForResult(intent, requestCode);
            }
        });
        renameAlbum.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditAlbButton.class);
                intent.putExtra("albumlist", alblist);
                int requestcode = 2;
                startActivityForResult(intent, requestcode);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Log.d("degugtag", "do i get here");
        }
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String buttype = data.getStringExtra("buttype");
                    if (buttype.equals("add")) {
                        Album newalbum = (Album) data.getSerializableExtra("album");
                        Log.d("debugtag", newalbum.getName());
                        adapter.add(newalbum);
                        adapter.notifyDataSetChanged();
                    }
                    if (buttype.equals("delete")) {
                        Log.d("debugtag", "just a test");
                        Album albtodelete = (Album) data.getSerializableExtra("album");
                        ;
                        int position = adapter.getPosition(albtodelete);
                        adapter.remove(adapter.getItem(position));
                        adapter.notifyDataSetChanged();

                    }
                }
            }

        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Log.d("debugtag", "What are the chances I get here?");
                Bundle bundle = data.getExtras();
                ArrayList<Album> updatelist = (ArrayList<Album>) bundle.getSerializable("albumlist");
                for(Album alb : updatelist){
                    Log.d("debugtag", alb.getName());
                    adapter.add(alb);
                }
                adapter.notifyDataSetChanged();



            }
        }
    }

}