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
        progressDialog.setCancelable(false);

        newEmail = findViewById(R.id.new_email);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        registerBtn = findViewById(R.id.register_btn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = newEmail.getText().toString().trim();
                final String password = newPassword.getText().toString().trim();
                final String confirmPass = confirmPassword.getText().toString().trim();

                verifyCredentials(email, password, confirmPass);
            }
        });



    }

    private void verifyCredentials(String email, String password, String confirmPass) {
        progressDialog.setMessage("Verifying Details");
        progressDialog.show();
        View view = null;
        Boolean emailVerified = false;
        Boolean passwordVerified = false;

        // check if our emailField is empty
        if(TextUtils.isEmpty(email)){
            view = newEmail;
            view.setFocusable(true);
            progressDialog.dismiss();
            Toast.makeText(this, "Input your email", Toast.LENGTH_SHORT).show();
        }else{
            // check if the email is valid i.e having '@gmail.com'
            if(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")){
                Toast.makeText(this, "Email is valid", Toast.LENGTH_SHORT).show();
                emailVerified = true;
            }else{
                progressDialog.dismiss();
                Toast.makeText(this, "Invalid email! \nOnly gmail users have access", Toast.LENGTH_SHORT).show();
            }
        }
        // check if our passwordField is empty
        if(TextUtils.isEmpty(password)){
            progressDialog.dismiss();
            view = newPassword;
            view.setFocusable(true);
            Toast.makeText(this, "Input your password", Toast.LENGTH_SHORT).show();
        }else{
            // check password length
            if(password.length() >= 6){
                passwordVerified = true;
                // check if password and confirmPassword match
                if(password.matches(confirmPass)){
                    passwordVerified = true;
                }else{
                    progressDialog.dismiss();
                    passwordVerified = false;
                    Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }else{
                progressDialog.dismiss();
                passwordVerified = false;
                Toast.makeText(this, "Password is too short. \nMinimum of 6 characters required", Toast.LENGTH_SHORT).show();
            }
        }
        // check if the confirmPasswordField is empty
        if(confirmPass.isEmpty()){
            progressDialog.dismiss();
            view = confirmPassword;
            view.setFocusable(true);
            Toast.makeText(this, "Confirm your password", Toast.LENGTH_SHORT).show();
        }

        // If the email and password are valid, proceed to this part
        if(emailVerified == true && passwordVerified == true){
            progressDialog.setMessage("Creating you account... \nPlease wait");
            FirebaseUser user = mAuth.getCurrentUser();

            if(user != null){

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.setMessage("Sending message...");
                            sendEmailVerification();
                        }

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Registration failed! " +
                                "\n"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                progressDialog.dismiss();
            }
        }


    }


    private void sendEmailVerification(){
        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mAuth.signOut();
                            progressDialog.dismiss();
                            Toast.makeText(RegistrationActivity.this, "Verification Message sent! " +
                                    "\n Proceed to your email to complete registration", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }




}
