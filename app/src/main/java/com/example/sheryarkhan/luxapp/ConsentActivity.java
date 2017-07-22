package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ConsentActivity extends AppCompatActivity {


    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);

        imageView = (ImageView)findViewById(R.id.imageView3);

        try {
            Intent intent = getIntent();
            byte[] imageByte = intent.getByteArrayExtra("photo");
            //Bitmap image = new Bitmap(imageByte);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            imageView.setImageBitmap(bitmap);
        }
        catch (Exception ex)
        {
            Log.d("Tag", ex.toString());
        }



    }
}
