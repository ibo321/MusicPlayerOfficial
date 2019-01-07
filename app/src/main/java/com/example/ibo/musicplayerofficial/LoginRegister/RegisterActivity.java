package com.example.ibo.musicplayerofficial.LoginRegister;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText edtRegister_email, edtRegister_pass;
    Button btn_registerNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        findViews();
    }

    private void findViews() {
        edtRegister_email = (EditText) findViewById(R.id.edtRegister_email);
        edtRegister_pass = (EditText) findViewById(R.id.edtRegister_pass);
        btn_registerNow = (Button) findViewById(R.id.btn_registerNow);
    }

    //Register performed by using Firebase Authentication
    public void onClickRegisterNow(View view) {
        final String email = edtRegister_email.getText().toString().trim();
        final String password = edtRegister_pass.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("register", "Successfully registered account: " + email.toString() + " " + password.toString());
                            Toast.makeText(RegisterActivity.this, "Successfully registered account!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("register", "Failed to register", task.getException());
                            Toast.makeText(RegisterActivity.this, "Failed to register.. Check email or password.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
