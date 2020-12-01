package com.example.android67;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddButton extends AppCompatActivity {

    private Button add;
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

        ArrayList<String> mainactalblist = getIntent().getExtras().getStringArrayList("albumlist");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dupe = false;
                String newalbname = newalbum.getText().toString().trim();
                if (newalbname.length() == 0) {
                    Toast.makeText(AddButton.this, "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        for (String albname : mainactalblist) {
                            Log.d("debugtag", albname);
                            if (newalbname.equals(albname)) {
                                dupe = true;
                                Toast.makeText(AddButton.this, "Duplicate album!", Toast.LENGTH_LONG).show();

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("debugtag", newalbname);

                    if(!dupe){
                        Intent intent = new Intent();
                        intent.putExtra("album", newalbname);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else{
                        finish();
                    }

                }
            }
        });
        };
    }

