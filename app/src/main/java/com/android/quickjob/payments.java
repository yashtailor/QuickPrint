package com.android.quickjob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class payments extends AppCompatActivity {
    EditText moneyRetrive;
    Button btnPay;
    int cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        moneyRetrive = (EditText)findViewById(R.id.moneyValue);
        btnPay = (Button)findViewById(R.id.buttonEnter);


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(moneyRetrive.getText().toString()))
                    cash = Integer.parseInt(moneyRetrive.getText().toString());
                Toast.makeText(getApplicationContext(), " amount of money paid is "+cash, Toast.LENGTH_LONG).show();
            }
        });
    }
}
