package com.example.android67;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddAlbButton extends AppCompatActivity {

    private Button add, delete;
    private EditText newalbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add = findViewById(R.id.addButton);
        newalbum = findViewById(R.id.album_editText);
        delete = findViewById(R.id.delButton);

        ArrayList<Album> mainactalblist =
                (ArrayList<Album>) getIntent().getExtras().get("albumlist");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dupe = false;
                String newalbname = newalbum.getText().toString().trim();
                if (newalbname.length() == 0) {
                    Toast.makeText(AddAlbButton.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        for (Album alb : mainactalblist) {
                            Log.d("debugtag", alb.getName());
                            if (newalbname.equals(alb.getName())) {
                                dupe = true;
                                Toast.makeText(AddAlbButton.this, "Duplicate album!", Toast.LENGTH_LONG).show();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("debugtag", newalbname);

                    if(!dupe){
                        Album newalb = new Album(newalbname, new ArrayList<Photo>());
                        Intent intent = new Intent();
                        intent.putExtra("album", newalb);
                        intent.putExtra("buttype", "add");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        finish();
                    }

                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean found = false;
                String alb_todelete = newalbum.getText().toString().trim();
                if (alb_todelete.length() == 0) {
                    Toast.makeText(AddAlbButton.this, "Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        for(Album alb : mainactalblist){
                            if(alb_todelete.equals(alb.getName())){
                                found = true;
                                Intent intent = new Intent();
                                intent.putExtra("album", alb);
                                intent.putExtra("buttype", "delete");
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(!found){
                        Toast.makeText(AddAlbButton.this, "Album doesnt exist!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        }

    }

