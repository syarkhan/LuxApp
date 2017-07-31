package com.example.sheryarkhan.luxapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;


public class DiscoverActivity extends Activity {


    View parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        parentLayout = findViewById(android.R.id.content);


        try {
            int PERMISSIONS_ALL = 1;
            String[] Permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (!hasPermissions(this, Permissions)) {
                ActivityCompat.requestPermissions(this, Permissions, PERMISSIONS_ALL);
            }
        }catch(Exception ex)
        {
            Log.d("permissionError",ex.toString());
        }
    }

    public static boolean hasPermissions(Context context, String... permissions){

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
        }catch(Exception ex)
        {
            Log.d("dadabhoy",ex.toString());
        }

        return true;
    }


    public void goToColors(View view)
    {

//        ArrayList<Integer> arrayList = new ArrayList<>();
//        arrayList.add(1);

        //questionsList.setqList(arrayList);

        //Snackbar.make(parentLayout,"GO TO COLORS!"+questionsList.getqList().toString(),Snackbar.LENGTH_SHORT).show();

//        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//
//        String emailaddress = "sheheryaarkhan97@yahoo.com";
//        String message = "hello";
//        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
//        emailIntent.setType("image/png");
//
//        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.ironman);
//
//        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
//
//        startActivity(emailIntent);

        Intent intent = new Intent(getBaseContext(),ColorsActivity.class);
        startActivity(intent);
        finish();

        //Snackbar.make(parentLayout,"GO TO COLORS!",Snackbar.LENGTH_SHORT).show();
    }

}
