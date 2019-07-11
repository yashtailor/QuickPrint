package com.android.quickjob;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VendorAddition extends AppCompatActivity {

    private EditText vendorNumber,vendorName;
    private Button addDetails;
    private VendorAddVerify vendorAddVerify=new VendorAddVerify();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_addition);

        vendorNumber=(EditText) findViewById(R.id.vendorNumber);
        vendorName=(EditText) findViewById(R.id.vendorName);
        addDetails=(Button) findViewById(R.id.addVendorDetails);
        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVendorDataToList();
            }
        });
    }
    public void addVendorDataToList() {
        String name=vendorName.getText().toString().trim();
        String no=vendorNumber.getText().toString().trim();
        vendorAddVerify.setmText1(name);
        vendorAddVerify.setmText2(no);
        DeveloperOptions.mVendorList.add(vendorAddVerify);
        vendorName.getText().clear();
        vendorNumber.getText().clear();
        Toast.makeText(VendorAddition.this,"Added to the list",Toast.LENGTH_LONG).show();
    }
}
