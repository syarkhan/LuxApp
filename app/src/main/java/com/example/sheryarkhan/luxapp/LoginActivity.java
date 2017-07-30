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

    public static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTxtName = (EditText)findViewById(R.id.editTextName);
        editTxtEmail = (EditText)findViewById(R.id.editTextEmail);


        //btnLogin = (Button) findViewById(R.id.btnLogin);

    }

    public void goToLogin(View view){
        if( editTxtName.getText().toString().equals("") && editTxtEmail.getText().toString().equals("") )
        {

            Toast.makeText(LoginActivity.this,"Please fill every text field!",Toast.LENGTH_SHORT).show();

        }
        else
        {
                    boolean validEmail = isValidEmail(editTxtEmail.getText().toString());

                    if(validEmail) {

                        user = new User(editTxtName.getText().toString(), editTxtEmail.getText().toString());
                        //Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), DiscoverActivity.class);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        Toast.makeText(getBaseContext(),"Please enter correct Email ID!",Toast.LENGTH_SHORT).show();
                    }

        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
