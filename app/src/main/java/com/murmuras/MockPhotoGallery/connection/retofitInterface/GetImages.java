package com.murmuras.MockPhotoGallery.connection.retofitInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Hasan Mhd Amin on 04.01.21
 */
public interface GetImages {
    @GET
    Call<ResponseBody> downloadFileWithFixedUrl(@Url String fileUrl);
}
