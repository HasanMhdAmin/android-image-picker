package com.murmuras.photogallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.murmuras.photogallery.model.ImageItem;

import java.util.ArrayList;

/**
 * Drawable: new Image("android.resource://com.murmuras.MockPhotoGallery/drawable/murmuras")
 * Asset: new Image("file:///android_asset/images/murmuras.png")
 * External urls: new Image("https://upload.wikimedia.org/wikipedia/commons/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg")
 */
public class GalleryActivity extends AppCompatActivity {
    public static final String IMAGES = "images";
    public static final String ALLOW_MULTI_SELECTION = "allow_multi_selection";
    public static final String SELECTED_IMAGE = "selected_image";
    public static final int GALLERY_REQUEST_CODE = 1277;

    ArrayList<ImageItem> images = new ArrayList<>();
    private RecyclerView imagesRecyclerView;
    private ImageAdapter imageAdapter;

    private void findViews() {
        imagesRecyclerView = findViewById(R.id.imagesRecyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        findViews();

        if (getIntent().hasExtra(IMAGES))
            images = getIntent().getParcelableArrayListExtra(IMAGES);

        boolean allowMultiSelection = false;
        if (getIntent().hasExtra(ALLOW_MULTI_SELECTION))
            allowMultiSelection = getIntent().getBooleanExtra(ALLOW_MULTI_SELECTION, false);


        imagesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageAdapter = new ImageAdapter(images, allowMultiSelection);
        imagesRecyclerView.setAdapter(imageAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            ArrayList<ImageItem> selectedImages = imageAdapter.getSelectedImage();
            if (selectedImages != null) {
                Intent returnIntent = new Intent();
                returnIntent.putParcelableArrayListExtra(SELECTED_IMAGE, selectedImages);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}