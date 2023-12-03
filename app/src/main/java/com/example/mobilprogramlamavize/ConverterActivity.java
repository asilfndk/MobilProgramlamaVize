package com.example.mobilprogramlamavize;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class ConverterActivity extends AppCompatActivity {

    private String[] decimalSpinnerSec = {"Binary", "Octal", "Hexadecimal"};
    private String[] byteSpinnerSec = {"Kilobyte", "Byte", "KiB", "Bit"};
    private ArrayAdapter<String> decimalSpinnerAdapter, byteSpinnerAdapter;

    private EditText decimalNumber, byteNumber, dereceNumber;
    private Spinner decimalSpinner, byteSpinner;
    private TextView decimalSonuc, byteSonuc, dereceSonuc;
    private RadioButton dereceFah, dereceKel;
    private Button hesaplaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        DecimalBolum();
        ByteBolum();
        SicaklikBolum();
        HesaplamaButton();
    }
    private void DecimalBolum() {
        decimalNumber = findViewById(R.id.decimalnumber);
        decimalSpinner = findViewById(R.id.decimalspinner);
        decimalSonuc = findViewById(R.id.decimalsonuc);

        decimalSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, decimalSpinnerSec);
        decimalSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        decimalSpinner.setAdapter(decimalSpinnerAdapter);
    }
    private void ByteBolum() {
        byteNumber = findViewById(R.id.bytenumber);
        byteSpinner = findViewById(R.id.bytespinner);
        byteSonuc = findViewById(R.id.bytesonuc);

        byteSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, byteSpinnerSec);
        byteSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byteSpinner.setAdapter(byteSpinnerAdapter);
    }
    private void SicaklikBolum() {
        dereceNumber = findViewById(R.id.celsiusnumber);
        dereceFah = findViewById(R.id.radioFah);
        dereceKel = findViewById(R.id.radioKel);
        dereceSonuc = findViewById(R.id.celsiussonuc);
    }
    private void HesaplamaButton() {
        hesaplaButton = findViewById(R.id.hesapla);
        hesaplaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalHesap();
                ByteHesap();
                SicaklikHesap();
            }
        });
    }
    private void DecimalHesap() {
        if (decimalNumber.getText().toString().isEmpty()) {
            Toast.makeText(ConverterActivity.this, "Deger", Toast.LENGTH_SHORT).show();
            return;
        }

        int inputNumber = Integer.parseInt(decimalNumber.getText().toString());
        int selectedDecimalSpinner = decimalSpinner.getSelectedItemPosition();

        String sonuc;
        switch (selectedDecimalSpinner) {
            case 0:
                sonuc = Integer.toBinaryString(inputNumber);
                break;
            case 1:
                sonuc = Integer.toOctalString(inputNumber);
                break;
            case 2:
                sonuc = Integer.toHexString(inputNumber);
                break;
            default:
                sonuc = "Hata";
                break;
        }
        decimalSonuc.setText("Sonuc: " + sonuc);
    }

    private void ByteHesap() {
        if (byteNumber.getText().toString().isEmpty()) {
            Toast.makeText(ConverterActivity.this, "Deger", Toast.LENGTH_SHORT).show();
            return;
        }

        int inputNumber = Integer.parseInt(byteNumber.getText().toString());
        int selectedByteSpinner = byteSpinner.getSelectedItemPosition();

        String sonuc;
        switch (selectedByteSpinner) {
            case 0:
            case 2:
            case 3:
                sonuc = String.valueOf(inputNumber * 8);
                break;
            case 1:
                sonuc = String.valueOf(inputNumber / 8);
                break;
            default:
                sonuc = "Hata";
                break;
        }
        byteSonuc.setText("Sonuc: " + sonuc);
    }
    private void SicaklikHesap() {
        if (dereceNumber.getText().toString().isEmpty()) {
            Toast.makeText(ConverterActivity.this, "Deger", Toast.LENGTH_SHORT).show();
            return;
        }

        int inputNumber = Integer.parseInt(dereceNumber.getText().toString());

        if (dereceFah.isChecked()) {
            double fahrenheit = (inputNumber * 9.0 / 5.0) + 32;
            dereceSonuc.setText("Sonuc: " + fahrenheit);
        } else if (dereceKel.isChecked()) {
            double kelvin = inputNumber + 273.15;
            dereceSonuc.setText("Sonuc: " + kelvin);
        }
    }
}