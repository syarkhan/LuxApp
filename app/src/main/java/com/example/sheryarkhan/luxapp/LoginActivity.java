package com.example.sheryarkhan.luxapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText editTxtName,editTxtEmail;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTxtName = (EditText)findViewById(R.id.editTextName);
        editTxtEmail = (EditText)findViewById(R.id.editTextEmail);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    public void goToLogin(View view){
        if( editTxtName.getText().toString().equals("") && editTxtEmail.getText().toString().equals("") )
        {

            Toast.makeText(LoginActivity.this,"Please fill every text field!",Toast.LENGTH_SHORT).show();

        }
        else
        {
                    //Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);

                    startActivity(intent);

        }
    }
}
