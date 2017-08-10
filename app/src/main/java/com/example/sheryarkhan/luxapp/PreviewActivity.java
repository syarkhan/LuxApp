package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class PreviewActivity extends AppCompatActivity {

    //ConstraintLayout layout;
    ImageView previewImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        //layout = (ConstraintLayout)findViewById(R.id.previewLayout);
        previewImage = (ImageView)findViewById(R.id.previewImage);
        SampleActivity.drawable.setBounds(0, 0, SampleActivity.w, SampleActivity.h);
        SampleActivity.drawable.draw(SampleActivity.canvas);

        Drawable draw = new BitmapDrawable(getResources(),SampleActivity.newImage);



        //layout.setBackground(draw);
        previewImage.setBackground(draw);
        //SampleActivity.newImage.recycle();
        //SampleActivity.newImage = null;


    }

    public void goToPermission(View view)
    {

//        SampleActivity.newImage.recycle();
//        SampleActivity.newImage = null;
        Intent intent = new Intent(getBaseContext(),PermissionActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToTakeSelfieAgain(View view)
    {
        SampleActivity.newImage.recycle();
        SampleActivity.newImage = null;
        Intent intent = new Intent(getBaseContext(),SampleActivity.class);
        startActivity(intent);
        finish();
    }

}
