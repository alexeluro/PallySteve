package com.example.hp.pallysteve;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat notificationSwitch, loginSwitch;
    Boolean notificationState, loginState;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("PREF", 0);
        notificationState = pref.getBoolean("notificationSwitch", false);
        loginState = pref.getBoolean("loginSwitch", false);

        notificationSwitch = findViewById(R.id.notification_switch);
        loginSwitch = findViewById(R.id.login_status);

        notificationSwitch.setChecked(notificationState);
        loginSwitch.setChecked(loginState);

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationState = !notificationState;
                notificationSwitch.setChecked(notificationState);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("notificationSwitch", notificationState);
                editor.apply();
            }
        });

        loginSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginState = !loginState;
                loginSwitch.setChecked(loginState);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("loginSwitch", loginState);
                editor.apply();
            }
        });


    }
}
