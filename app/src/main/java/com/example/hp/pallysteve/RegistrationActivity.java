package com.example.hp.pallysteve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    EditText newEmail;
    EditText newPassword;
    EditText confirmPassword;
    Button registerBtn;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        newEmail = findViewById(R.id.new_email);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        registerBtn = findViewById(R.id.register_btn);

        String email = newEmail.getText().toString().trim();
        String password = newPassword.getText().toString().trim();
        String confirmPass = confirmPassword.getText().toString().trim();

        verifyCredentials(email, password, confirmPass);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RegistrationActivity.this, "Congrats! You've been registered!", Toast.LENGTH_SHORT).show();
            }
        });






    }

    private void verifyCredentials(String email, String password, String confirmPass) {
        View view = null;
        Boolean emailVerified = false;
        Boolean passwordVerified = false;

        // check if our emailField is empty
        if(TextUtils.isEmpty(email)){
            view = newEmail;
            view.setFocusable(true);
            Toast.makeText(this, "Input your email", Toast.LENGTH_SHORT).show();
        }
        // check if our passwordField is empty
        if(TextUtils.isEmpty(password)){
            view = newPassword;
            view.setFocusable(true);
            Toast.makeText(this, "Input your password", Toast.LENGTH_SHORT).show();
        }
        // check if the confirmPasswordField is empty
        if(TextUtils.isEmpty(confirmPass)){
            view = confirmPassword;
            view.setFocusable(true);
            Toast.makeText(this, "Confirm your password", Toast.LENGTH_SHORT).show();
        }

        // check if the email is valid i.e having '@gmail.com'
        if(email.endsWith("@gmail.com")){
            Toast.makeText(this, "Email is valid", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Invalid email! \nOnly gmail users have access", Toast.LENGTH_SHORT).show();
        }

        // check password length
        if(password.length() >= 6){
            passwordVerified = true;
            // check if password and confirmPassword match
            if(password.matches(confirmPass)){
                passwordVerified = true;
            }else{
                passwordVerified = false;
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }
        }else{
            passwordVerified = false;
            Toast.makeText(this, "Password is too short. \nMinimum of 6 characters required", Toast.LENGTH_SHORT).show();
        }

        if(emailVerified == true && passwordVerified == true){
            progressDialog.setMessage("Creating you account... \nPlease wait");
            progressDialog.show();
            FirebaseUser user = mAuth.getCurrentUser();

            if(user != null){
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        mAuth.signOut();
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Account Created! " +
                                "\n Proceed to your email to complete registration", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Registration failed! " +
                                "\n"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }






    }

}
