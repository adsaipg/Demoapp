package com.example.adarsh.demoapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageActivity extends AppCompatActivity {

    private RecyclerView mRecycle;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        getSupportActionBar().setHomeButtonEnabled(true);

        mRecycle = (RecyclerView) findViewById(R.id.listofimages);
        Gson gson = new GsonBuilder().setLenient().create();
        mDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://adarshsingh061295.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ImageInterface imageInterface = retrofit.create(ImageInterface.class);
        Call<List<ImageList>> imageListCall = imageInterface.loadlist();
        imageListCall.enqueue(new Callback<List<ImageList>>() {
            @Override
            public void onResponse(Call<List<ImageList>> call, Response<List<ImageList>> response) {
                Log.d("image", "success");
                List<ImageList> list=response.body();
                Log.d("image", list.get(0).getPid());
                mRecycle.setLayoutManager(new LinearLayoutManager(ImageActivity.this));
                mRecycle.setAdapter(new ImageAdapter(ImageActivity.this, list));
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<ImageList>> call, Throwable t) {

                Log.d("image", "fail:"+t.getMessage());
                mDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
