package com.example.mobilprogramlamavize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {
    private int minDeger, maxDeger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        LinearLayout Sonuclar = findViewById(R.id.sonuclar);
        EditText miktar = findViewById(R.id.adet);
        EditText mindeger = findViewById(R.id.min);
        EditText maxdeger = findViewById(R.id.max);

        maxdeger.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    int adet = Integer.parseInt(miktar.getText().toString());
                    minDeger = Integer.parseInt(mindeger.getText().toString());
                    maxDeger = Integer.parseInt(maxdeger.getText().toString());

                    Sonuclar.removeAllViews();

                    for (int i = 0; i < adet; i++) {
                        addProgressBar(RandomActivity.this, Sonuclar);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void addProgressBar(Context context, LinearLayout Sonuclar) {
        int min, max, nerede;
        do {
            min = minDeger + new Random().nextInt(maxDeger - minDeger);
            max = min + new Random().nextInt(maxDeger - min + 1);
            nerede = min + new Random().nextInt(max - min + 1);
        } while (min == max || min == nerede || nerede == max);

        double yuzde = ((double) (nerede - min) / (max - min)) * 100;
        int islem = (int) yuzde;

        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView textYuzde = new TextView(context);
        textYuzde.setId(View.generateViewId());
        textYuzde.setText(nerede + " %" + islem);
        ConstraintLayout.LayoutParams paramsYuzde = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsYuzde.topToTop = ConstraintSet.PARENT_ID;
        paramsYuzde.startToStart = ConstraintSet.PARENT_ID;
        paramsYuzde.setMargins(dpToPx(180), dpToPx(30), 0, 0);
        textYuzde.setLayoutParams(paramsYuzde);
        constraintLayout.addView(textYuzde);

        TextView textMin = new TextView(context);
        textMin.setId(View.generateViewId());
        textMin.setText(String.valueOf(min));
        ConstraintLayout.LayoutParams paramsMin = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsMin.topToTop = ConstraintSet.PARENT_ID;
        paramsMin.startToStart = ConstraintSet.PARENT_ID;
        paramsMin.setMargins(dpToPx(100), dpToPx(40), 0, 0);
        textMin.setLayoutParams(paramsMin);
        constraintLayout.addView(textMin);

        TextView textMax = new TextView(context);
        textMax.setId(View.generateViewId());
        textMax.setText(String.valueOf(max));
        ConstraintLayout.LayoutParams paramsMax = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        paramsMax.topToTop = ConstraintSet.PARENT_ID;
        paramsMax.endToEnd = ConstraintSet.PARENT_ID;
        paramsMax.setMargins(0, dpToPx(40), dpToPx(100), 0);
        textMax.setLayoutParams(paramsMax);
        constraintLayout.addView(textMax);

        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setId(View.generateViewId());
        ConstraintLayout.LayoutParams paramsPB = new ConstraintLayout.LayoutParams(
                dpToPx(150),
                dpToPx(20)
        );
        paramsPB.topToTop = ConstraintSet.PARENT_ID;
        paramsPB.startToStart = textMin.getId();
        paramsPB.endToEnd = textMax.getId();
        paramsPB.setMargins(dpToPx(30), dpToPx(40), dpToPx(30), dpToPx(100));
        progressBar.setLayoutParams(paramsPB);
        progressBar.setMin(0);
        progressBar.setProgress(islem);
        progressBar.setMax(100);
        constraintLayout.addView(progressBar);

        Sonuclar.addView(constraintLayout);
    }

    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}