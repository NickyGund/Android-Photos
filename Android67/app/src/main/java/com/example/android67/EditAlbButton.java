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

public class EditAlbButton extends AppCompatActivity {

    private Button edit;
    private EditText old_alb, new_alb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alb_button);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit = findViewById(R.id.renButton);
        old_alb = findViewById(R.id.oldalb_te);
        new_alb = findViewById(R.id.newalb_te);

        ArrayList<Album> mainactalblist = (ArrayList<Album>) getIntent().getExtras().get("albumlist");

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean dupe = false;
                String old_alb_name = old_alb.getText().toString().trim();
                String new_alb_name = new_alb.getText().toString().trim();
                if (old_alb_name.length() == 0 || new_alb_name.length() == 0) {
                    Toast.makeText(EditAlbButton.this, "Empty", Toast.LENGTH_SHORT).show();
                }
                try{
                    for (Album alb : mainactalblist){
                        if(old_alb_name.equals(alb.getName())){
                            Log.d("debugtag", "The old album exists");
                            for (Album albs : mainactalblist){
                                Log.d("debugtag", "for loop");
                                if(new_alb_name.equals(albs.getName())){
                                    dupe = true;
                                    Toast.makeText(EditAlbButton.this, "New album name already exists", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                            if(!dupe){
                                Log.d("debugtag", "do i get here?");
                                alb.setName(new_alb_name);
                                Log.d("debugtag", alb.getName());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("albumlist", mainactalblist);
                                Intent intent = new Intent(EditAlbButton.this, MainActivity.class);
                                intent.putExtras(bundle);
                                setResult(RESULT_OK, intent);
                                finish();
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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