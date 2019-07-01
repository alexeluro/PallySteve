package com.example.hp.pallysteve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    EditText adminCom, adminLoc, adminRole, adminSal;
    Button uploadBtn;
    FirebaseDatabase database;
    DatabaseReference dataReff;
    UserInfo userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userdata = new UserInfo();
        database = FirebaseDatabase.getInstance();
        dataReff = database.getReference().child("UserInfo");

        adminCom = findViewById(R.id.admin_company_name);
        adminRole = findViewById(R.id.admin_role_name);
        adminLoc = findViewById(R.id.admin_location_name);
        adminSal = findViewById(R.id.admin_salary_range);
        uploadBtn = findViewById(R.id.upload_btn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comp = adminCom.getText().toString().trim();
                String role = adminRole.getText().toString().trim();
                String loc = adminLoc.getText().toString().trim();
                Integer sal = Integer.parseInt(adminSal.getText().toString().trim());

                userdata.setCompanyName(comp);
                userdata.setJobRole(role);
                userdata.setLocation(loc);
                userdata.setSalary(sal);

                dataReff.child("job").push().setValue(userdata);
                    Toast.makeText(AdminActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
