package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FlowersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowers);
    }

    public void goToTakeSelfie(View view)
    {
        if(view.getTag().toString().equals("1")) {
            ColorsActivity.questionsList.qList.add(1);
        }
        else if(view.getTag().toString().equals("2")) {
            ColorsActivity.questionsList.qList.add(2);
        }
        else if(view.getTag().toString().equals("3")) {
            ColorsActivity.questionsList.qList.add(3);
        }
        String text= ColorsActivity.questionsList.getqList().toString();
        Toast.makeText(getBaseContext(),text,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(),TakeSelfieActivity.class);
        startActivity(intent);
        finish();
    }
}
