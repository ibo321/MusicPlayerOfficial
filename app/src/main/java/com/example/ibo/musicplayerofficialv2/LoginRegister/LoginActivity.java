package com.example.ibo.musicplayerofficialv2.LoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibo.musicplayerofficialv2.MainActivity;
import com.example.ibo.musicplayerofficialv2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Declare variables
    private FirebaseAuth mAuth;
    EditText edtLogin_email, edtLogin_pass;
    ProgressDialog progressDialog;
    Button btn_Login;
    CheckBox rememberMeCB;
    Boolean checkStatus;

    //SharedPreference variables to save user login
    SharedPreferences sharedPref;
    public static final String PREF_FILE_NAME = "loginPref";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //get methods
        findViews();
        checkedBox();
        checkLoginStatusBeforeLogin();

        mAuth = FirebaseAuth.getInstance();
    }

    //Perform login if types values are correct
    private void checkLoginStatusBeforeLogin() {

        //listens on the login button
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call this method to check first if the fields are empty or not

                checkTextFieldsIfCorrectOrNot();

                //if the fields are all correct then perform login
                if (checkStatus) {
                    onClickLogin();
                } else {
                    //else notify user to fill all forms
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Remember me function that save user login
    private void checkedBox() {

        //assign email and password variables to sharedPreference strings
        String email = sharedPref.getString(EMAIL, null);
        String password = sharedPref.getString(PASSWORD, null);

        if (sharedPref.contains("checked") && sharedPref.getBoolean("checked", false)) {
            rememberMeCB.setChecked(true);
        } else {
            rememberMeCB.setChecked(false);
        }
        //if mail and pass is not null then get user email and password
        if (email != null && password != null) {
            edtLogin_email.setText(email);
            edtLogin_pass.setText(password);
        }
    }

    //Check if user entered correct informations
    private void checkTextFieldsIfCorrectOrNot() {
        String email = edtLogin_email.getText().toString().trim();
        final String password = edtLogin_pass.getText().toString().trim();

        //if email and password field is empty
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            checkStatus = false;
            edtLogin_email.setError("Fill empty field!");
            edtLogin_pass.setError("Fill empty field!");
        } else if (TextUtils.isEmpty(email)) {
            checkStatus = false;
            edtLogin_email.setError("Fill empty field!");
        } else if (TextUtils.isEmpty(password)) {
            checkStatus = false;
            edtLogin_pass.setError("Fill empty field!");
        } else if (password.length() < 6) {
            checkStatus = false;
            edtLogin_pass.setError("Password must be more than 5 characters!");
        } else if (!email.contains("@")) {
            checkStatus = false;
            edtLogin_email.setError("Email doesn't exist!");
        } else {
            checkStatus = true;
        }
    }

    //find views
    private void findViews() {
        edtLogin_email = (EditText) findViewById(R.id.edtLogin_email);
        edtLogin_pass = (EditText) findViewById(R.id.edtLogin_pass);
        progressDialog = new ProgressDialog(LoginActivity.this);
        btn_Login = (Button) findViewById(R.id.btn_login);
        sharedPref = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        rememberMeCB = (CheckBox) findViewById(R.id.remember_checkBox);
    }

    //Login performed using Firebase Authentication
    private void onClickLogin() {

        //assign email and password variables to my editText fields and get the typed text
        final String email = edtLogin_email.getText().toString().trim();
        final String password = edtLogin_pass.getText().toString().trim();

        //progressdialog (just for the looks)
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //her fortæller vi at hvis checkboxen er checked så skal den gemme oplysningerne når brugeren logger ud
                    SharedPreferences.Editor editor = sharedPref.edit();
                    if (rememberMeCB.isChecked()) {
                        editor.putBoolean("checked", true);
                        editor.putString(EMAIL, email);
                        editor.putString(PASSWORD, password);
                    } else {
                        editor.putBoolean("checked", false);
                        editor.remove(EMAIL);
                        editor.remove(PASSWORD);
                    }
                    editor.apply();

                    //Get a log message if user logged in
                    Log.d("login", "Succeed login: " + email + " " + password);

                    //Navigate to SongListActivity if success
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //Get a log message if user COULDN'T log in
                    Log.d("login", "Failed to login: " + email + " " + password, task.getException());

                    Toast.makeText(LoginActivity.this, "Email or password is incorrect!", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    //    @Override
    //    protected void onStart() {
    //        super.onStart();
    //        FirebaseUser currentUser = mAuth.getCurrentUser();
    //        if (currentUser != null){
    //            Toast.makeText(this, "Current user: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
    //        }else {
    //            Toast.makeText(this, "No users", Toast.LENGTH_SHORT).show();
    //        }
    //    }

    //Navigate to Register page
    public void onClickNavigatToRegisterPage(View view) {

        //Intent kan også laves sådan her:
        //       Intent intent = new Intent (LoginActivity.this, RegisterActivity.class);
        //       startActivity(intent);

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
