package com.example.android67;

import androidx.appcompat.app.AppCompatActivity;
//import com.example.savingstate.AndroidSaveState;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
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
    public ArrayList<Photo> getPhotolist(){
        return photolist;
    }
    public void setPhotolist(ArrayList<Photo> photolist){
        this.photolist = photolist;
    }
    public String toString(){ //used by listview
        return name;
    }

}
class Photo implements Serializable {
    String path;
    ArrayList<String> location_tags;
    ArrayList<String> person_tags;
    Photo(String path, ArrayList<String> location_tags, ArrayList<String> person_tags){
        this.path = path;
        this.location_tags = location_tags;
        this.person_tags = person_tags;
    }
    public String getPath(){
        return path;
    }
    public ArrayList<String> getPersonTags(){
        return person_tags;
    }
    public void setPersonTags(ArrayList<String> person_tags){
        this.person_tags = person_tags;
    }
    public ArrayList<String> getLocationTags(){
        return location_tags;
    }
    public void setLocationTags(ArrayList<String> location_tags){
        this.location_tags = location_tags;
    }
}

public class MainActivity extends AppCompatActivity {

    //private AndroidSaveState saveState;
    private ListView albumlistview;
   // private String[] albumnames;
    private Button addAlbum, searchPhotos, renameAlbum;
    private ArrayAdapter<Album> adapter;
    private ArrayList<Album> alblist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       try
        {
            FileInputStream fis = getApplicationContext().openFileInput("save.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            if(ois !=null) {
                alblist = (ArrayList<Album>) ois.readObject();
            }
            else{
                alblist = new ArrayList<>();
            }
            ois.close();
            fis.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
            return;
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
        //alblist = new ArrayList<>();
        //albumnames = getResources().getStringArray(R.array.album_array);
        albumlistview = findViewById(R.id.albumList);
        adapter = new ArrayAdapter<Album>(this, R.layout.album, alblist);
        albumlistview.setAdapter(adapter);
        addAlbum = findViewById(R.id.addAlbum);
        renameAlbum = findViewById(R.id.renAlbum);
        searchPhotos = findViewById(R.id.searchPhotos);


        albumlistview.setOnItemClickListener((p, V, pos, id) ->{
            Log.d("debugtag", "click test");
            showGallery(pos);
        });
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
        searchPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alblist.isEmpty()){
                    Toast.makeText(MainActivity.this, "There are no albums to search through", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, SearchMenu.class);
                    intent.putExtra("albumlist", alblist);
                    startActivity(intent);
                }
            }
        });
    }
    /*private void loadAlblist(){
        if(saveState.getAlblist()!=null){
            alblist=(ArrayList<Album>) saveState.getAlblist();
        }
        else{
            alblist = new ArrayList<>();
        }
    }*/
   /* private void setUpListener(){

    }*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current album list state
        savedInstanceState.putSerializable("alblist", alblist);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
    private void showGallery(int pos) {
        int requestcode = 3;
        Bundle bundle = new Bundle();
        Album alb = alblist.get(pos);
        Log.d("debugtag", "name is " + alb.getName());
        bundle.putInt("index", pos);
        bundle.putSerializable("album", alb);
        bundle.putSerializable("alblist_from_main", alblist);
        Log.d("debugtag",alblist.toString());
        Intent intent = new Intent(this, PhotoGallery.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestcode);
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
                        adapter = null;
                        alblist.add(newalbum);
                        adapter = new ArrayAdapter<Album>(this, R.layout.album, alblist);
                        adapter.notifyDataSetChanged();
                        albumlistview.setAdapter(adapter);

                    }
                    if (buttype.equals("delete")) {
                        Log.d("debugtag", "just a test");
                        Album albtodelete = (Album) data.getSerializableExtra("album");
                        adapter = null;
                        for(Album albs : alblist){
                            Log.d("debugtag", albs.getName());
                            if(albtodelete.getName().equals(albs.getName())){
                                alblist.remove(albs);
                                adapter = new ArrayAdapter<Album>(this, R.layout.album, alblist);
                                adapter.notifyDataSetChanged();
                                albumlistview.setAdapter(adapter);
                                break;
                            }
                        }

                    }
                }
            }
        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Log.d("debugtag", "What are the chances I get here?");
                Bundle bundle = data.getExtras();
                ArrayList<Album> updatelist = (ArrayList<Album>) bundle.getSerializable("albumlist");
                adapter = null;
                alblist.clear();
                for(Album alb : updatelist){
                    Log.d("debugtag", alb.getName());
                    alblist.add(alb);
                }
                adapter = new ArrayAdapter<Album>(this, R.layout.album, alblist);
                adapter.notifyDataSetChanged();
                albumlistview.setAdapter(adapter);
            }
        }
        if(requestCode == 3){
            if(resultCode == RESULT_OK){
                Bundle bundle = data.getExtras();
                ArrayList<Album> updatelist = (ArrayList<Album>) bundle.getSerializable("alblist");
                adapter = null;
                alblist.clear();
                for(Album alb : updatelist){
                    Log.d("debugtag", alb.getName());
                    alblist.add(alb);
                }
                adapter = new ArrayAdapter<Album>(this, R.layout.album, alblist);
                adapter.notifyDataSetChanged();
                albumlistview.setAdapter(adapter);

            }
        }
    }
    @Override
    protected void onStop(){
        super.onStop();

        try{
            String fileName = "save.txt";
            FileOutputStream fos= getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(alblist);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}