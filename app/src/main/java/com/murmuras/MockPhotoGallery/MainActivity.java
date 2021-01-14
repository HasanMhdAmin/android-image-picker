package com.murmuras.MockPhotoGallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.murmuras.MockPhotoGallery.connection.ImagePlaceHoldService;
import com.murmuras.MockPhotoGallery.connection.Utils;
import com.murmuras.MockPhotoGallery.connection.retofitInterface.GetImages;
import com.murmuras.photogallery.GalleryActivity;
import com.murmuras.photogallery.model.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.murmuras.photogallery.GalleryActivity.ALLOW_MULTI_SELECTION;
import static com.murmuras.photogallery.GalleryActivity.GALLERY_REQUEST_CODE;
import static com.murmuras.photogallery.GalleryActivity.IMAGES;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button selectButton;
    ImageView preview;
    ProgressBar progressBar;
    SwitchCompat switchMood;
    int downloadPercentage = 0;

    private void findViews() {
        selectButton = findViewById(R.id.selectButton);
        preview = findViewById(R.id.preview);
        progressBar = findViewById(R.id.progress_bar);
        switchMood = findViewById(R.id.switch_mood);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        progressBar.setMax(50);
        for (int i = 0; i < 50; i++) {
            downloadImages("120x120&text=" + i);
        }

        selectButton.setOnClickListener(view -> {
            ArrayList<ImageItem> images = Utils.getImagesFromLocalStorage(this, "NutriDiary");

            Intent gallery = new Intent(MainActivity.this, GalleryActivity.class);
            gallery.putParcelableArrayListExtra(IMAGES, images);
            gallery.putExtra(ALLOW_MULTI_SELECTION, switchMood.isChecked());
            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
        });

    }

    private void downloadImages(String url) {
        GetImages apiInterface = ImagePlaceHoldService.createService(GetImages.class);

        Call<ResponseBody> call = apiInterface.downloadFileWithFixedUrl(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    boolean writtenToDisk = Utils.writeResponseBodyToDisk(MainActivity.this, url, response.body());
                    progressBar.setProgress(++downloadPercentage);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                List<ImageItem> selectedImages = data.getParcelableArrayListExtra(GalleryActivity.SELECTED_IMAGE);

                if (!selectedImages.isEmpty())
                    Picasso.get().load(selectedImages.get(0).getUrl()).into(preview);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: ");
            }
        }
    }
}