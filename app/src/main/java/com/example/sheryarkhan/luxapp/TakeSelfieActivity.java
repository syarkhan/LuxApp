package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class TakeSelfieActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    public static int maxIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_selfie);


        int[] count = new int[4];
        for (int number : ColorsActivity.questionsList.qList)
        {
            count[number]++;

        }

        int max = 0;
        //this is what you looking for
        for(int i = 0, k = count.length; i < k; i++){
            if(count[i] > max){
                max = count[i];
                maxIndex = i;
            }
        }

        //Toast.makeText(getBaseContext(),String.valueOf(maxIndex),Toast.LENGTH_SHORT).show();

        constraintLayout = (ConstraintLayout)findViewById(R.id.takeSelfieLayout);

        if(maxIndex == 1) {
            constraintLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.mariaselfie));
        }
        else if(maxIndex == 2) {
            constraintLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.marwaselfie));
        }
        else if(maxIndex == 3) {
            constraintLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.mayaselfie));
        }


        constraintLayout.setClickable(true);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(),SampleActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }

    public void goToMahiraSelfie(View view)
    {
        String text= ColorsActivity.questionsList.getqList().toString();
        Toast.makeText(getBaseContext(),text,Toast.LENGTH_SHORT).show();
        finish();
        Intent intent = new Intent(getBaseContext(),SampleActivity.class);
        startActivity(intent);
    }
}
