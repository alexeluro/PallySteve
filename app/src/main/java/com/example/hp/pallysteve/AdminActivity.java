package com.example.hp.pallysteve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.InputMismatchException;

public class AdminActivity extends AppCompatActivity {

    EditText adminCom, adminLoc, adminRole, adminSal;
    Button uploadBtn;
    FirebaseDatabase database;
    DatabaseReference dataReff;
    UserInfo userdata;
    private String comp;
    private String role;
    private String loc;
    private Integer sal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userdata = new UserInfo();
        database = FirebaseDatabase.getInstance();
        dataReff = database.getReference();//.child("UserInfo")

        adminCom = findViewById(R.id.admin_company_name);
        adminRole = findViewById(R.id.admin_role_name);
        adminLoc = findViewById(R.id.admin_location_name);
        adminSal = findViewById(R.id.admin_salary_range);
        uploadBtn = findViewById(R.id.upload_btn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    comp = adminCom.getText().toString().trim();
                    role = adminRole.getText().toString().trim();
                    loc = adminLoc.getText().toString().trim();

                    sal = Integer.parseInt(adminSal.getText().toString().trim());
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
                    if (sal == null) {
                        sal = 0;
                    }
                }
                userdata.setCompanyName(comp);
                userdata.setJobRole(role);
                userdata.setLocation(loc);
                userdata.setSalary(sal);

                dataReff.push().setValue(userdata);
                    Toast.makeText(AdminActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
