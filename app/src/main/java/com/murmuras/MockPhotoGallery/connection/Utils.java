package com.murmuras.MockPhotoGallery.connection;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.murmuras.photogallery.model.ImageItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;

/**
 * Created by Hasan Mhd Amin on 13.01.21
 */
public class Utils {
    private static final String TAG = "Utils";

    public static boolean writeResponseBodyToDisk(Context context, String name, ResponseBody body) {
        try {

            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("NutriDiary", Context.MODE_PRIVATE);
            if (!directory.exists()) {
                boolean rv = directory.mkdir();
                Log.d(TAG, "Folder creation " + (rv ? "success" : "failed"));
            }
            File myPath = new File(directory, name + ".png");


            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(myPath);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


    public static ArrayList<ImageItem> getImagesFromLocalStorage(Context context, String directory) {

        ArrayList<ImageItem> images = new ArrayList<>();
        ContextWrapper cw = new ContextWrapper(context);
        File dir = cw.getDir(directory, Context.MODE_PRIVATE);

        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                images.add(new ImageItem("file://" + file.getAbsolutePath()));
            }
        }

        return images;
    }
}
