package com.example.adarsh.demoapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by adaRSH on 27-Apr-17.
 */

public interface ImageInterface {
    @GET("getData.php")
    Call<List<ImageList>> loadlist();
}
