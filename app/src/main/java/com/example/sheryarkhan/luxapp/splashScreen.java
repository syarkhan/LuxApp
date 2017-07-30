package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import gr.net.maroulis.library.EasySplashScreen;

public class splashScreen extends AppCompatActivity {

    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        layout = (ConstraintLayout)findViewById(R.id.splashScreenLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        EasySplashScreen config =  new EasySplashScreen(splashScreen.this)
//                .withFullScreen()
//                .withTargetActivity(LoginActivity.class)
//                .withSplashTimeOut(5000)
//                .withBackgroundColor(Color.parseColor("#000000"))
//                .withLogo(R.drawable.loginbg);

        //View view = config.create();


    }
}
