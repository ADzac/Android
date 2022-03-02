package com.example.celciusfahrenheit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewAnimator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.button);
        b.setOnClickListener(this);
    }
    public void onClick(View v ){
        int id_obj = v.getId();
        switch(id_obj){
            case R.id.button:
                this.convert_CtoF(v);
                break;
            case R.id.button2:
                this.convert_FtoC(v);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id_obj);
        }
    }
    public void convert_FtoC(View v) {
        EditText celsius, fahrenheit;
        celsius=(EditText) findViewById(R.id.celsius_id);
        double val;
        fahrenheit = (EditText) findViewById(R.id.fahrenheit_id);
        if (!(fahrenheit.getText()).equals("")) {
            String value = fahrenheit.getText().toString();
            val = Double.valueOf(value);
            val = (val - 32) * 5 / 9;
            celsius.setText(val + "°C");
        }
    }
    public void convert_CtoF(View v){
        EditText celsius,fahrenheit;
        double val;
        fahrenheit = (EditText) findViewById(R.id.fahrenheit_id);
        celsius=(EditText) findViewById(R.id.celsius_id);
        if (!(celsius.getText()).equals("")) {
            String value = celsius.getText().toString();
            val = Double.valueOf(value);
            val = (val * 9 / 5) + 32;
            fahrenheit.setText(val + "F");
        }
    }
}