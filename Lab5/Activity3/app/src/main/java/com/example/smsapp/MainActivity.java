package com.example.smsapp;

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

        EditText phoneNumberEditText = findViewById(R.id.phone_number);
        EditText messageEditText = findViewById(R.id.message_text);
        Button sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString();
            String message = messageEditText.getText().toString();

            if (!phoneNumber.isEmpty()) {
                //Use ACTION_SENDTO intent to send SMS
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:" + phoneNumber));//set SMS URI
                startActivity(smsIntent);
            }else{
                Toast.makeText(this, "Please enter a phone number and message", Toast.LENGTH_SHORT).show();
            }
        });
    }
}