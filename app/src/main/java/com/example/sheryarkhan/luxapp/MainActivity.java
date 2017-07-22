package com.example.sheryarkhan.luxapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button btnCamera;
    ImageView imgView;
    public static Bitmap finalBitmapImage = null;
    Date curentTime;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = (ImageView)findViewById(R.id.imageView);
        verifyStoragePermissions(this);


//        CameraManager cameraManager = (CameraManager)getSystemService(CAMERA_SERVICE);
//        try{
//
//            for(String cameraId: cameraManager.getCameraIdList())
//            {
//                CameraCharacteristics chars = cameraManager.getCameraCharacteristics(cameraId);
//                // Does the camera have a forwards facing lens?
//                Integer facing = chars.get(CameraCharacteristics.LENS_FACING);
//            }
//        }catch (CameraAccessException ex)
//        {
//            ex.printStackTrace();
//        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private File getFile()
    {
        curentTime = new Date();
        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root + "/Lux");
        if (!folder.mkdirs()) {
            Log.e("dir", "Directory not created");
        }

        File image_file = new File(folder,"selfie_cam"+curentTime+".jpg");
        return image_file;
    }

    public void dispatchTakePictureIntent(View view) {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }



        imgView = (ImageView)findViewById(R.id.imageView);
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera_intent,REQUEST_IMAGE_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                //Bundle bundle = new Bundle();
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            //imgView.setImageBitmap(imageBitmap);
//            finalBitmapImage = imageBitmap;
//
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
//            byte[] image = stream.toByteArray();
//
//            Intent intent = new Intent(this, ConsentActivity.class);
//            intent.putExtra("photo", image);
//            startActivity(intent);
                //Intent intent = new Intent(getBaseContext(),ConsentActivity.class);
                //startActivity(intent);


                setAndSaveImageWithOverlay(getBitmapOfSnappedImage());
            }
        }
        catch (Exception ex)
        {
            Log.d("dada2",ex.toString());
        }

    }


    public void setAndSaveImageWithOverlay(Bitmap snappedImage){
        Bitmap b = Bitmap.createBitmap(snappedImage.getWidth(), snappedImage.getHeight(), Bitmap.Config.ARGB_8888);

        //the overlay png file from drawable folder
        Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.camera);
        overlay = Bitmap.createScaledBitmap(overlay,snappedImage.getWidth(),snappedImage.getHeight(),false);

        //create canvas with a clean bitmap
        Canvas canvas = new Canvas(b);
        //draw the snappedImage on the canvas
        canvas.drawBitmap(snappedImage, 0, 0, new Paint());
        //draw the overlay on the canvas
        canvas.drawBitmap(overlay, 0, 0, new Paint());

        imgView.setImageBitmap(b);

        //SaveImage(b);
    }



    public Bitmap getBitmapOfSnappedImage(){


        String root = Environment.getExternalStorageDirectory().toString();
        String path = root + "/Lux"+     "/selfie_cam_"+curentTime+".jpg";

        File image = new File(path);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap =     BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        return bitmap;
    }
}
