package com.example.hp.pallysteve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView detailsCompanyName, detailsJobDescription, detailsJobRole, detailsLocation, detailsSalary;
    Button applyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        detailsCompanyName = findViewById(R.id.display_name);
        detailsJobDescription = findViewById(R.id.details_desc);
        detailsJobRole = findViewById(R.id.details_job_role);
        detailsLocation = findViewById(R.id.details_location);
        detailsSalary = findViewById(R.id.details_salary_range);
        applyBtn = findViewById(R.id.apply_btn);

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("");
                intent.setAction(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_SUBJECT, "alexeluro@gmail.com");
                startActivity(intent);

            }
        });


    }
}
