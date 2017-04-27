package com.example.adarsh.demoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageDetail;
    private TextView descView,NameView,DateView,idView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setHomeButtonEnabled(true);
        imageDetail= (ImageView) findViewById(R.id.showImageDetail);
        descView= (TextView) findViewById(R.id.descView);
        NameView= (TextView) findViewById(R.id.nameView);
        DateView= (TextView) findViewById(R.id.DateView);
        idView= (TextView) findViewById(R.id.idView);
        Intent intent=getIntent();
        Picasso.with(this).load(intent.getStringExtra("url")).placeholder(R.drawable.loading).into(imageDetail);
        idView.setText("ID : "+intent.getStringExtra("id"));
        descView.setText("DESC : "+intent.getStringExtra("desc"));
        NameView.setText("NAME : "+intent.getStringExtra("name"));
        DateView.setText("DATE & TIME : "+intent.getStringExtra("date"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
