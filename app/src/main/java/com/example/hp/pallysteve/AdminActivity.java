package com.example.hp.pallysteve;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.InputMismatchException;

public class AdminActivity extends AppCompatActivity {

    EditText adminCom, adminRole, adminMinSal, adminMaxSal;
    Spinner adminLoc;
    Button uploadBtn;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    UserInfo userdata;
    private String comp;
    private String role;
    private String loc;
    private Integer minSal;
    private Integer maxSal;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userdata = new UserInfo();
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference();//.child("UserInfo")

        adminCom = findViewById(R.id.admin_company_name);
        adminRole = findViewById(R.id.admin_role_name);
        adminLoc = findViewById(R.id.admin_location_name);
        adminMinSal = findViewById(R.id.admin_salary_min_range);
        adminMaxSal = findViewById(R.id.admin_salary_max_range);
        uploadBtn = findViewById(R.id.upload_btn);


        ArrayAdapter spinnerAdapter = ArrayAdapter.
                createFromResource(this, R.array.location_options, android.R.layout.simple_spinner_dropdown_item);
        adminLoc.setAdapter(spinnerAdapter);
        adminLoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(adminLoc.getSelectedItem().toString().trim()){
                    case "Lagos":
                        loc = "Lagos";
                        adminLoc.setSelection(i);
                        return;
                    case "Ogun":
                        loc = "Ogun";
                        adminLoc.setSelection(i);
                        return;
                    case "Ibadan":
                        loc = "Ibadan";
                        adminLoc.setSelection(i);
                        return;
                    case "Abia":
                        loc = "Abia";
                        adminLoc.setSelection(i);
                        return;
                    default:
                        loc = "No location yet";
                        adminLoc.setSelection(i);
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    comp = adminCom.getText().toString().trim();
                    role = adminRole.getText().toString().trim();
//                    loc = adminLoc;

                    minSal = Integer.parseInt(adminMinSal.getText().toString().trim());
                    maxSal = Integer.parseInt(adminMaxSal.getText().toString().trim());

                    if(minSal > maxSal){
                        Toast.makeText(getApplicationContext(), "Minimum salary can't be greater than the Maximum value", Toast.LENGTH_SHORT).show();
                    }else{
                        userdata.setCompanyName(comp);
                        userdata.setJobRole(role);
                        userdata.setLocation(loc);
                        userdata.setMinSalary(minSal);
                        userdata.setMaxSalary(maxSal);
                    }

                }catch(Exception e) {
                    if (TextUtils.isEmpty(comp)) {
                        comp = "Unknown(check back later)";
                    }
                    if (TextUtils.isEmpty(role)) {
                        role = "Unknown(check back later)";
                    }
                    if (TextUtils.isEmpty(loc)) {
                        loc = "Unknown(check back later)";
                    }
                    if (minSal == null) {
                        minSal = 0;
                    }
                    if (maxSal == null){
                        maxSal = 0;
                    }
                }


                dataRef.push().setValue(userdata);

                adminCom.setText("");
                adminRole.setText("");
                adminMinSal.setText("");
                adminMaxSal.setText("");
                    Toast.makeText(AdminActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
