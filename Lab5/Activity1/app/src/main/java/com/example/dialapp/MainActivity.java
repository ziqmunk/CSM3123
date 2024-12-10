package com.example.dialapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText phonNumberEditText = findViewById(R.id.phone_number);
        Button dialButton = findViewById(R.id.dial_button);

        dialButton.setOnClickListener(v ->{
            String phoneNumber = phonNumberEditText.getText().toString();
            if(!phoneNumber.isEmpty()){
                //Create an Intent to open the dialer
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
            }else{
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
            }
        });
    }
}