package com.example.adarsh.demoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FButton buttonChoose;
    private FButton buttonUpload;

    private ImageView imageView;
    private StorageReference mStorageReference;

    //private EditText editTextName,descText;
    private TextInputEditText editTextName,descText;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="https://adarshsingh061295.000webhostapp.com/upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_DESC="desc";
    private String url;
    private String name;
    private String descr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mStorageReference = FirebaseStorage.getInstance().getReference();


        buttonUpload = (FButton) findViewById(R.id.buttn1);
        buttonChoose= (FButton) findViewById(R.id.buttn2);
        editTextName = (TextInputEditText) findViewById(R.id.name);
        descText= (TextInputEditText) findViewById(R.id.descrip);
        FButton btn= (FButton) findViewById(R.id.showImage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this,ImageActivity.class));
            }
        });


        imageView  = (ImageView) findViewById(R.id.imageview);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //Showing the progress dialog
        name = editTextName.getText().toString();
        descr = descText.getText().toString();
        if (name.matches("") && descr.matches("")) {
            Toast.makeText(getApplicationContext(), "All field should be filled.", Toast.LENGTH_SHORT);
        }
        else {

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(HomeActivity.this, s, Toast.LENGTH_LONG).show();
                        Log.d("image", s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(HomeActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.d("image", volleyError.getLocalizedMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = url;

                //Getting Image Name
                name = editTextName.getText().toString().trim();
                descr = descText.getText().toString().trim();

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put(KEY_NAME, name);
                params.put(KEY_DESC, descr);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST) {
                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);

                StorageReference filepath = mStorageReference.child("Profile image").child(selectedImageUri.getLastPathSegment());
                filepath.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                         url = String.valueOf(taskSnapshot.getDownloadUrl());
                        Log.d("image","url:"+url);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Fail to uplaod image", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        }
    }


    @Override
    public void onClick(View v) {

        if(v == buttonChoose){
            showFileChooser();
        }

        if(v == buttonUpload){
            uploadImage();
        }
    }
}