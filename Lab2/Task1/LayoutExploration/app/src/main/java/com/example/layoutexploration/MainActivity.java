package com.example.layoutexploration;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button linearLayoutButton =
                findViewById(R.id.linearLayoutButton);
        linearLayoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    LinearLayoutActivity.class);
            startActivity(intent);
        });
        Button relativeLayoutButton =
                findViewById(R.id.relativeLayoutButton);
        relativeLayoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    RelativeLayoutActivity.class);
            startActivity(intent);
        });
        Button constraintLayoutButton =
                findViewById(R.id.constraintLayoutButton);
        constraintLayoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    ConstraintLayoutActivity.class);
            startActivity(intent);
        });
        Button gridLayoutButton =
                findViewById(R.id.gridLayoutButton);
        gridLayoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    GridLayoutActivity.class);
            startActivity(intent);
        });
    }}