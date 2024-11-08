package com.example.imageexperiment;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout layout;
    private boolean isBackgroundOne = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        Button switchBackgroundButton = findViewById(R.id.switchBackgroundButton);

        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        switchBackgroundButton.setOnClickListener(v -> {
            // Start fade-out animation
            layout.startAnimation(fadeOut);

            // Set new background after fade-out completes
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Switch background
                    layout.setBackgroundResource(isBackgroundOne ? R.drawable.background_image2 : R.drawable.background_image1);

                    // Start fade-in animation
                    layout.startAnimation(fadeIn);

                    // Toggle background state
                    isBackgroundOne = !isBackgroundOne;
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        });
    }
}
