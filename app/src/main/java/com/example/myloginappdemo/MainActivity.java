package com.example.myloginappdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText nameText,emailText,passwordText;
    Button signupButton;
    TextView loginLink;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("SignUp Activity");

        mAuth = FirebaseAuth.getInstance();

        nameText=findViewById(R.id.input_name);
        emailText=findViewById(R.id.input_email);
        passwordText=findViewById(R.id.input_password);
        signupButton=findViewById(R.id.btn_signup);
        loginLink=findViewById(R.id.link_login);
        progressBar=findViewById(R.id.progressBarId);

        signupButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_signup:
                userRegister();
                break;

            case R.id.link_login:
                Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                break;


        }


    }

    private void userRegister() {
        String name=nameText.getText().toString();
        String email=emailText.getText().toString().trim();
        String password=passwordText.getText().toString().trim();


        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            nameText.requestFocus();
            return;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            emailText.requestFocus();
            return;
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            passwordText.setError("between 6 and 10 alphanumeric characters");
            passwordText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            finish();
                            Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FAILED>>>>>>>>>>>", "createUserWithEmail:failure", task.getException());

                            Toast.makeText(getApplicationContext(),"Not Successful: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });


    }
}
