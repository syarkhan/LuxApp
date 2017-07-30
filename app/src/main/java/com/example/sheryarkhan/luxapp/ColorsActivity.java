package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    public static QuestionsList questionsList = new QuestionsList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
    }

    public void goToPlaces(View view)
    {
        ArrayList<Integer> arrayList = new ArrayList<>();



        if(view.getTag().toString().equals("1")) {
            arrayList.add(1);
            questionsList.setqList(arrayList);
        }
        else if(view.getTag().toString().equals("2")) {
            arrayList.add(2);
            questionsList.setqList(arrayList);
        }
        else if(view.getTag().toString().equals("3")) {
            arrayList.add(3);
            questionsList.setqList(arrayList);
        }
        String text= questionsList.getqList().toString();
        Toast.makeText(getBaseContext(),text,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(),PlacesActivity.class);
        startActivity(intent);
        finish();

    }
}
