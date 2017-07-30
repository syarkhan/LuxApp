package com.example.sheryarkhan.luxapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class SampleActivity extends Activity implements SurfaceHolder.Callback
{
    private Camera camera = null;
    private SurfaceView cameraSurfaceView = null;
    private SurfaceHolder cameraSurfaceHolder = null;
    private boolean previewing = false;
    RelativeLayout relativeLayout;
    public static Drawable drawable = null;
    public static Canvas canvas = null;
    public static Bitmap newImage = null;

    private int cameraId;

    private Button btnCapture = null;
    private ImageView overlayImage;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);



        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sample);

        overlayImage = (ImageView)findViewById(R.id.imageView1);

        if(TakeSelfieActivity.maxIndex == 1)
        {
            overlayImage.setImageResource(R.drawable.mahira_selfie_frame);
        }
        else if(TakeSelfieActivity.maxIndex == 2)
        {
            overlayImage.setImageResource(R.drawable.mawra_selfie_frame);
        }
        else if(TakeSelfieActivity.maxIndex == 3)
        {
            overlayImage.setImageResource(R.drawable.maya_selfie_frame);
        }

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
//
//        ActivityCompat.requestPermissions(SampleActivity.this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
//                new Integer[]{1,2});

//        ActivityCompat.requestPermissions(SampleActivity.this,
//                new String[]{Manifest.permission.CAMERA},
//                1);




        try
        {
        relativeLayout=(RelativeLayout) findViewById(R.id.containerImg);
        relativeLayout.setDrawingCacheEnabled(true);
        cameraSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        //  cameraSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(640, 480));
        cameraSurfaceHolder = cameraSurfaceView.getHolder();
        cameraSurfaceHolder.addCallback(this);
        //    cameraSurfaceHolder.setType(SurfaceHolder.
        //                                               SURFACE_TYPE_PUSH_BUFFERS);

            cameraId = getFrontCameraId();



        btnCapture = (Button)findViewById(R.id.button1);
        btnCapture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                camera.takePicture(cameraShutterCallback,
                        cameraPictureCallbackRaw,
                        cameraPictureCallbackJpeg);
            }
        });

        }catch (Exception ex)
        {
            Log.d("dada",ex.toString());
        }
    }


    public static boolean hasPermissions(Context context,String... permissions){

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

//    private void checkAndroidVersion() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkPermission();
//
//        } else {
//            // write your logic here
//        }
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(SampleActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
//            case 2: {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(SampleActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
//                }
//                return;
//            }
////
////             other 'case' lines to check for other
////             permissions this app might request
//        }
//    }

    private int getFrontCameraId(){
        int camId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo ci = new Camera.CameraInfo();

        for(int i = 0;i < numberOfCameras;i++){
            Camera.getCameraInfo(i,ci);
            if(ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                camId = i;
            }
        }

        return camId;
    }

    Camera.ShutterCallback cameraShutterCallback = new Camera.ShutterCallback()
    {
        @Override
        public void onShutter()
        {
            // TODO Auto-generated method stub
        }
    };

    Camera.PictureCallback cameraPictureCallbackRaw = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            // TODO Auto-generated method stub
        }
    };

    Camera.PictureCallback cameraPictureCallbackJpeg = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            // TODO Auto-generated method stub

            try {
                Bitmap cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                cameraBitmap = rotateBitmap(cameraBitmap);

                int wid = cameraBitmap.getWidth();
                int hgt = cameraBitmap.getHeight();

                //  Toast.makeText(getApplicationContext(), wid+""+hgt, Toast.LENGTH_SHORT).show();
                newImage = Bitmap.createBitmap
                        (wid, hgt, Bitmap.Config.ARGB_8888);
//
//            float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1};
//            Matrix matrix = new Matrix();
//            Matrix matrixMirrorY = new Matrix();
//            matrixMirrorY.setValues(mirrorY);
//
//            matrix.postConcat(matrixMirrorY);
//
//            image = Bitmap.createBitmap(mBitmap, 0, 0, frame.getWidth(), frame.getHeight(), matrix, true);

                canvas = new Canvas(newImage);

                canvas.drawBitmap(cameraBitmap, 0f, 0f, null);



                if(TakeSelfieActivity.maxIndex == 1)
                {
                    drawable = getResources().getDrawable
                            (R.drawable.mahira_selfie_frame);
                }
                else if(TakeSelfieActivity.maxIndex == 2)
                {
                    drawable = getResources().getDrawable
                            (R.drawable.mawra_selfie_frame);
                }
                else if(TakeSelfieActivity.maxIndex == 3)
                {
                    drawable = getResources().getDrawable
                            (R.drawable.maya_selfie_frame);
                }


//                Drawable drawable = getResources().getDrawable
//                        (R.drawable.mahira_selfie_frame);

                drawable.setBounds(0, 0, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());

                //drawable.setBounds(0, 0, drawable.getIntrinsicWidth()+20, drawable.getIntrinsicHeight()+30);
                //drawable.draw(canvas);
                Intent intent = new Intent(getBaseContext(),PreviewActivity.class);
                startActivity(intent);
                finish();

                //overlayImage.setBackground(drawable);
            }
            catch (Exception ex)
            {
                Log.d("dada5",ex.toString());
            }


//                File storagePath = new File(Environment.
//                        getExternalStorageDirectory() + "/LuxSelfies/");
//                storagePath.mkdirs();
//
//                File myImage = new File(storagePath,"/"+LoginActivity.user.getName()+"_"+LoginActivity.user.getEmail()+".jpg");
//
//
//            try
//            {
//                FileOutputStream out = new FileOutputStream(myImage);
//                newImage.compress(Bitmap.CompressFormat.JPEG, 80, out);
//
//
//                out.flush();
//                out.close();
//            }
//            catch(FileNotFoundException e)
//            {
//                Log.d("In Saving File", e + "");
//            }
//            catch(IOException e)
//            {
//                Log.d("In Saving File", e + "");
//            }

            //camera.startPreview();



//            newImage.recycle();
//            newImage = null;
//
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//
//            intent.setDataAndType(Uri.parse("file://" + myImage.getAbsolutePath()), "image/*");
//            startActivity(intent);

        }
    };

    private Bitmap rotateBitmap(Bitmap bitmap)
    {
        Matrix rotateRight = new Matrix();
        rotateRight.preRotate(90);

//        if(android.os.Build.VERSION.SDK_INT > 13 && Camera.frontCamera)
//        {
            float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1};
            Matrix matrix = new Matrix();
            rotateRight = new Matrix();
            Matrix matrixMirrorY = new Matrix();
            matrixMirrorY.setValues(mirrorY);

            matrix.postConcat(matrixMirrorY);

            rotateRight.preRotate(270);
        //}

        final Bitmap rImg= Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rImg;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width, int height)
    {
        // TODO Auto-generated method stub

        if(previewing)
        {
            camera.stopPreview();
            previewing = false;
        }
        try
        {
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

            for (Camera.Size size : previewSizes) {
                // 640 480
                // 960 720
                // 1024 768
                // 1280 720
                // 1600 1200
                // 2560 1920
                // 3264 2448
                // 2048 1536
                // 3264 1836
                // 2048 1152
                // 3264 2176
                if (800 == size.width) {
                    parameters.setPreviewSize(size.width, size.height);
                    parameters.setPictureSize(size.width, size.height);
                    break;
                }
            }

            //Camera.Size previewSize = previewSizes.get(0);
            //parameters.setPreviewSize(previewSize.width, previewSize.height);
            //parameters.setPictureSize(previewSize.width, previewSize.height);
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                camera.setDisplayOrientation(90);

            }
//            Matrix matrix = new Matrix();
//            Camera.CameraInfo info = new Camera.CameraInfo();
//            android.hardware.Camera.getCameraInfo(cameraId, info);
//
//            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
//            {
//                float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1};
//                Matrix matrixMirrorY = new Matrix();
//                matrixMirrorY.setValues(mirrorY);
//
//                matrix.postConcat(matrixMirrorY);
//            }
//
//            matrix.postRotate(90);

            // parameters.setRotation(90);
            camera.setParameters(parameters);

            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
            previewing = true;
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block

            e.printStackTrace();
            Log.d("dada7",e.toString());
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        // TODO Auto-generated method stub
        try
        {
            camera = Camera.open(cameraId);
        }
        catch(RuntimeException e)
        {
            Toast.makeText(getApplicationContext(), "Device camera  is not working properly, please try after sometime.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        // TODO Auto-generated method stub
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }
}




//public class SampleActivity extends Activity implements SurfaceHolder.Callback{
//
//
//    private Camera camera = null;
//    private SurfaceView cameraSurfaceView = null;
//    private SurfaceHolder cameraSurfaceHolder = null;
//    private boolean previewing = false;
//    RelativeLayout relativeLayout;
//
//    private Button btnCapture = null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.activity_sample);
//
//
//        try {
//            relativeLayout = (RelativeLayout) findViewById(R.id.containerImg);
//            relativeLayout.setDrawingCacheEnabled(true);
//            cameraSurfaceView = (SurfaceView)
//                    findViewById(R.id.surfaceView1);
//            //cameraSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(640, 480));
//            cameraSurfaceHolder = cameraSurfaceView.getHolder();
//            cameraSurfaceHolder.addCallback(this);
//
//
//            //    cameraSurfaceHolder.setType(SurfaceHolder.
//            //                                               SURFACE_TYPE_PUSH_BUFFERS);
//
//
//            btnCapture = (Button) findViewById(R.id.button1);
//            btnCapture.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    camera.takePicture(cameraShutterCallback,
//                            cameraPictureCallbackRaw,
//                            cameraPictureCallbackJpeg);
//                }
//            });
//        }catch (Exception ex)
//        {
//            Log.d("dada",ex.toString());
//        }
//    }
//
//    Camera.ShutterCallback cameraShutterCallback = new Camera.ShutterCallback()
//    {
//        @Override
//        public void onShutter()
//        {
//            // TODO Auto-generated method stub
//        }
//    };
//
//    Camera.PictureCallback cameraPictureCallbackRaw = new Camera.PictureCallback()
//    {
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera)
//        {
//            // TODO Auto-generated method stub
//        }
//    };
//
//    Camera.PictureCallback cameraPictureCallbackJpeg = new Camera.PictureCallback()
//    {
//        @Override
//        public void onPictureTaken(byte[] data, Camera camera)
//        {
//            // TODO Auto-generated method stub
//            Bitmap cameraBitmap = BitmapFactory.decodeByteArray
//                    (data, 0, data.length);
//
//            int   wid = cameraBitmap.getWidth();
//            int  hgt = cameraBitmap.getHeight();
//
//            //  Toast.makeText(getApplicationContext(), wid+""+hgt, Toast.LENGTH_SHORT).show();
//            Bitmap newImage = Bitmap.createBitmap
//                    (wid, hgt, Bitmap.Config.ARGB_8888);
//
//            Canvas canvas = new Canvas(newImage);
//
//            canvas.drawBitmap(cameraBitmap, 0f, 0f, null);
//
//            Drawable drawable = getResources().getDrawable
//                    (R.drawable.ironman);
//            drawable.setBounds(20, 30, drawable.getIntrinsicWidth()+20, drawable.getIntrinsicHeight()+30);
//            drawable.draw(canvas);
//
//
//
//            File storagePath = new File(Environment.
//                    getExternalStorageDirectory() + "/PhotoAR/");
//            storagePath.mkdirs();
//
//            File myImage = new File(storagePath,
//                    Long.toString(System.currentTimeMillis()) + ".jpg");
//
//            try
//            {
//                FileOutputStream out = new FileOutputStream(myImage);
//                newImage.compress(Bitmap.CompressFormat.JPEG, 80, out);
//
//
//                out.flush();
//                out.close();
//            }
//            catch(FileNotFoundException e)
//            {
//                Log.d("In Saving File", e + "");
//            }
//            catch(IOException e)
//            {
//                Log.d("In Saving File", e + "");
//            }
//
//            camera.startPreview();
//
//
//
//            newImage.recycle();
//            newImage = null;
//
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//
//            intent.setDataAndType(Uri.parse("file://" + myImage.getAbsolutePath()), "image/*");
//            startActivity(intent);
//
//        }
//    };
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder,
//                               int format, int width, int height)
//    {
//        // TODO Auto-generated method stub
//
//        if(previewing)
//        {
//            camera.stopPreview();
//            previewing = false;
//        }
//        try
//        {
//            Camera.Parameters parameters = camera.getParameters();
//            parameters.setPreviewSize(640, 480);
//            parameters.setPictureSize(640, 480);
//            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
//                camera.setDisplayOrientation(90);
//
//            }
//
//            // parameters.setRotation(90);
//            camera.setParameters(parameters);
//
//            camera.setPreviewDisplay(cameraSurfaceHolder);
//            camera.startPreview();
//            previewing = true;
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder)
//    {
//        // TODO Auto-generated method stub
//        try
//        {
//            camera = Camera.open();
//        }
//        catch(RuntimeException e)
//        {
//            Toast.makeText(getApplicationContext(), "Device camera  is not working properly, please try after sometime.", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder)
//    {
//        // TODO Auto-generated method stub
//        camera.stopPreview();
//        camera.release();
//        camera = null;
//        previewing = false;
//    }
//}