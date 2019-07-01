package com.example.hp.pallysteve;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail;
    EditText userPassword;
    Button signInBtn;
    TextView createAcct;
    TextView forgotPassword;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener stateListener;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        signInBtn = findViewById(R.id.sign_btn);



        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = userEmail.getText().toString().trim();
                password = userPassword.getText().toString().trim();

                if(email.matches("admin@pallysteve.com") && password.matches("admin@pallysteve.com")){
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    setUpFirebaseAuth();
                    verifyCredentials(email, password);
                }
            }
        });


        createAcct = findViewById(R.id.create_account_here);
        createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        forgotPassword = findViewById(R.id.forgot_password_txt);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);






    }

    private void setUpFirebaseAuth() {
        stateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    mAuth.signOut();
                    Toast.makeText(LoginActivity.this, "User signed in already", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "User is curently inactive", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void verifyCredentials(String email, String password) {
        progressDialog.setMessage("Verifying User Inputs");
        progressDialog.show();
        Boolean verifiedEmail = false;
        Boolean verifiedPassword = false;

        //check if email emailField is empty
        if(TextUtils.isEmpty(email)){
            progressDialog.dismiss();
            Toast.makeText(this, "Input email", Toast.LENGTH_SHORT).show();
        }else{
            // check if email ends with @gmail.com or @yahoo.com
            if(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")){
                verifiedEmail = true;
            }else{
                progressDialog.dismiss();
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
            }

        }
        // check if passwordField is empty
        if(TextUtils.isEmpty(password)){
            progressDialog.dismiss();
            Toast.makeText(this, "Input password", Toast.LENGTH_SHORT).show();
        }else{
            // check if password is long enough
            if(password.length() >= 6){
                verifiedPassword = true;

            }else {
                progressDialog.dismiss();
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                verifiedPassword = false;
            }
        }



        // proceed to the firebase authentication
        if(verifiedEmail == true && verifiedPassword == true){
            progressDialog.setMessage("email status: valid \npassword status: valid");
            FirebaseUser user = mAuth.getCurrentUser();

//            AsyncBackground work = new AsyncBackground();
//            work.execute(email);
            signInUser(email, password);
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(stateListener);
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(stateListener != null){
//            mAuth.removeAuthStateListener(stateListener);
//        }
//    }

    public void signInUser(final String email, String password){
        progressDialog.setMessage("Signing you in...");

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()){
                                progressDialog.setMessage("email verified!");
                                Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                progressDialog.setMessage("Email hasn't been verified");
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Verify your email", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(LoginActivity.this, "Account doesnt exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ).addOnFailureListener
                (new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                     Toast.makeText(LoginActivity.this, "An error occured! \nPoor Internet connection", Toast.LENGTH_LONG).show();
                    }
        });
    }



    public class AsyncBackground extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Verifying... please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage("Almost done...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.setMessage("Verified!!!");
            Intent intent = new Intent();

        }
    }



}
