package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
    }

    public void goToEmojis(View view)
    {
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        arrayList.add(2);


        if(view.getTag().toString().equals("1")) {
            ColorsActivity.questionsList.qList.add(1);
        }
        else if(view.getTag().toString().equals("2")) {
            ColorsActivity.questionsList.qList.add(2);
        }
        else if(view.getTag().toString().equals("3")) {
            ColorsActivity.questionsList.qList.add(3);
        }
        //String text= ColorsActivity.questionsList.getqList().toString();
        //Toast.makeText(getBaseContext(),text,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(),EmojisActivity.class);
        startActivity(intent);
        finish();

    }
}
