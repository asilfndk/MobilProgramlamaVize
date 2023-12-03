package com.example.mobilprogramlamavize;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView Baslik, AltBaslik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaslatViews();
        TextAnimation();

        ButtonAnimation(R.id.converter, 0, 800, ConverterActivity.class);
        ButtonAnimation(R.id.random, 0, 900, RandomActivity.class);
        ButtonAnimation(R.id.sms, 0, 1000, SmsActivity.class);
    }
    private void BaslatViews() {
        Baslik = findViewById(R.id.textView);
        AltBaslik = findViewById(R.id.textView2);
    }
    private void TextAnimation() {
        FadeAnimation(Baslik, 5000);
        FadeAnimation(AltBaslik, 6000);
    }
    private void FadeAnimation(View view, long duration) {
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeIn.setDuration(duration);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.start();
    }
    private void ButtonAnimation(int buttonId, long startDelay, long duration, final Class<?> cls) {
        Button button = findViewById(buttonId);
        ObjectAnimator translateX = ObjectAnimator.ofFloat(button, "translationX", 0f, 350f);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(button, "translationY", 0f, 1100f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateX, translateY);
        animatorSet.setStartDelay(startDelay);
        animatorSet.setDuration(duration);
        animatorSet.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
            }
        });
    }
}