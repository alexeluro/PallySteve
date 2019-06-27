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
                setUpFirebaseAuth();
            }
        });


        createAcct = findViewById(R.id.create_account_here);
        createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forgotPassword = findViewById(R.id.forgot_password_txt);
        progressDialog = new ProgressDialog(this);

        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        verifyCredentials(email, password);



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
            Boolean verifiedEmail = false;
            Boolean verifiedPassword = false;

        //check if email emailField is empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Input email", Toast.LENGTH_SHORT).show();
        }
        // check if passwordField is empty
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Input password", Toast.LENGTH_SHORT).show();
        }
        // check if email ends with @gmail.com or @yahoo.com
        if(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")){
            verifiedEmail = true;
        }else{
            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
        }
        // check if password is long enough
        if(password.length() >= 6){
            verifiedPassword = true;
        }else {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        }

        // proceed to the firebase authentication
        if(verifiedEmail == true && verifiedPassword == true){

//            AsyncBackground work = new AsyncBackground();
//            work.execute(email);
            progressDialog.show();
            signInUser(email, password);

        }
    }

    public void signInUser(String email, String password){
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
        ).addOnFailureListener
                (new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                     Toast.makeText(LoginActivity.this, "An error occured! \n" +
                             e.getMessage(), Toast.LENGTH_LONG).show();
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
