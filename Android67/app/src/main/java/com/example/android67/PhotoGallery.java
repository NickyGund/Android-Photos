package com.example.android67;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class PhotoGallery extends AppCompatActivity {

    private Button add, delete, display, move;
    private LinearLayout linearLayout;
    private static final int PHOTOPICKCODE = 1;
    private static final int DISPLAYPHOTOCODE = 2;
    private Album album_from_main;
    private ArrayList<Photo> photoalb;
    private LinearLayout.LayoutParams lp;
    private ArrayList<ImageView> imgviewlist;
    private String selected_img_path;
    private ImageView selectedimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle_from_main = getIntent().getExtras();
        album_from_main = (Album) bundle_from_main.getSerializable("album");
        photoalb = album_from_main.getPhotolist();
        imgviewlist = new ArrayList<>();

        add = findViewById(R.id.addPhoto);
        delete = findViewById(R.id.delPhoto);
        display = findViewById(R.id.displayPhoto);
        move = findViewById(R.id.movPhoto);
        linearLayout = findViewById(R.id.lin_layout);

        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,30,0,30);
        updateScreen(photoalb);

        Log.d("debugtag", album_from_main.getName());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTOPICKCODE);
            }
        }) ;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    for (ImageView imgview : imgviewlist){
                        if (imgview.getColorFilter() != null){
                            imgviewlist.remove(imgview);
                            Log.d("debugtag", "this happens");
                            for (Photo photo : photoalb){
                                if(photo.getPath().equals(selected_img_path)){
                                    Log.d("debugtag", "i guess im wondering if this happens");
                                    photoalb.remove(photo);
                                    album_from_main.setPhotolist(photoalb);
                                    linearLayout.removeAllViewsInLayout();
                                    updateScreen(photoalb);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        display.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    for(Photo photo :photoalb){
                        if(photo.getPath().equals(selected_img_path)){
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent(PhotoGallery.this, DisplayPhoto.class);
                            bundle.putString("photopath",selected_img_path);
                            bundle.putSerializable("album", album_from_main);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, DISPLAYPHOTOCODE);
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
                Intent intent = new Intent();
                intent.putExtra("album", album_from_main);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void updateScreen(ArrayList<Photo> photoalb) {
        for(Photo photo : photoalb){
            Log.d("debugtag", photo.getPath());
            String photopath = photo.getPath();
            Uri myuri = Uri.parse(photopath);
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(myuri);
            imageView.setLayoutParams(lp);
            imgviewlist.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_img_path = photo.getPath();
                    imageView.setColorFilter(Color.BLUE, PorterDuff.Mode.LIGHTEN);
                    Log.d("debugtag", "img is selected");

                    for (ImageView imgview : imgviewlist){
                        if(imgview != imageView){
                            if(imgview.getColorFilter() != null){
                                imgview.setColorFilter(null);
                            }
                        }
                    }
                }
            });
            linearLayout.addView(imageView, lp);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTOPICKCODE && data != null){
            String uri = data.getData().toString();
            Uri myURI = Uri.parse(uri);
            ArrayList<String> persontags = new ArrayList<>();
            ArrayList<String> locationtags = new ArrayList<>();
            Photo newphoto = new Photo(uri, persontags, locationtags);
            photoalb.add(newphoto);
            album_from_main.setPhotolist(photoalb);
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(myURI);
            imageView.setLayoutParams(lp);
            imgviewlist.add(imageView);
            linearLayout.addView(imageView, lp);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected_img_path = uri;
                    imageView.setColorFilter(Color.BLUE, PorterDuff.Mode.LIGHTEN);
                    Log.d("debugtag", "img is selected");

                    for (ImageView imgview : imgviewlist){
                        if(imgview != imageView){
                            if(imgview.getColorFilter() != null){
                                imgview.setColorFilter(null);
                            }
                        }
                    }
                }
            });

        }
        else if(requestCode == DISPLAYPHOTOCODE){
            Album albumfromdisplay = (Album) data.getSerializableExtra("album");
            this.album_from_main = albumfromdisplay;
            linearLayout.removeAllViewsInLayout();
            updateScreen(albumfromdisplay.getPhotolist());
        }
        }


}