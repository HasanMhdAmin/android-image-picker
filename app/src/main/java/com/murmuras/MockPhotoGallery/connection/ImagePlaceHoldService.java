package com.murmuras.MockPhotoGallery.connection;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Hasan Mhd Amin on 04.01.21
 */
public class ImagePlaceHoldService {

    public static final String API_BASE_URL = "https://placehold.it/";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL);

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
