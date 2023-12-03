package com.example.mobilprogramlamavize;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmsActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String numara, mesaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        // Arayüzdeki bileşenleri tanımlar
        Button gonderButton = findViewById(R.id.smsbutton);
        EditText telText = findViewById(R.id.texttel);
        EditText mesajText = findViewById(R.id.textmesaj);
        // Gönderme butonuna tıklanma durumunu dinler
        gonderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // İzin verilmediyse SMS gönderme izni talep eder
                if (ContextCompat.checkSelfPermission(SmsActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SmsActivity.this,
                            new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                } else {
                    // Telefon numarası ve mesajı alır
                    numara = telText.getText().toString();
                    mesaj = mesajText.getText().toString();
                    // Eğer mesaj boşsa, özlü bir söz getirir ve gönderir
                    if (mesaj == null || mesaj.equals("")) {
                        new GetQuoteTask().execute();
                        showToast("Ozlu Soz Gonderildi!");
                    } else {
                        gonderSms(mesaj);
                        showToast("SMS Gonderildi!");
                    }
                }
            }
        });
    }
    // SMS gönderme metodu
    private void gonderSms(String sms) {
        try {
            // SmsManager aracılığıyla SMS gönderir
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numara, null, sms, null, null);
            showToast("SMS Gonderildi!");
        } catch (Exception e) {
            // Hata durumunda log kaydı oluşturur
            Log.e("SmsActivity", "HATA: " + e.getMessage());
        }
    }
    // Toast mesajı gösterme metodu
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    // Alıntı alma görevini yürüten AsyncTask
    private class GetQuoteTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String apiUrl = "https://api.quotable.io/random";
            try {
                // Alıntıyı çekmek için API'ye bağlanır ve okur
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                inputStream.close();
                urlConnection.disconnect();
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (result != null) {
                    // Alıntıyı JSON formatından çıkarır
                    JSONObject jsonObject = new JSONObject(result);
                    String quote = jsonObject.getString("content");
                    // Alıntıyı gönderir
                    gonderSms(quote);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}