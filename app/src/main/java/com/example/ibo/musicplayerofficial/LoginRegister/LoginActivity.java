package com.example.ibo.musicplayerofficial.LoginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.Music.SongListActivity;
import com.example.ibo.musicplayerofficial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    //Declare variables
    private FirebaseAuth mAuth;
    EditText edtLogin_email, edtLogin_pass;
    ProgressDialog progressDialog;
    Button btn_Login;
    CheckBox rememberME;
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
        getSupportActionBar().hide();
        findViews();
        checkedBox();
        checkLoginStatusBeforeLogin();
        mAuth = FirebaseAuth.getInstance();
    }

    private void checkLoginStatusBeforeLogin() {

        //listens on the login button
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call this method to check first if the fields are empty or not
                checkTextFieldsIsEmptyOrNot();

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
            rememberME.setChecked(true);
        } else {
            rememberME.setChecked(false);
        }
        //if mail and pass is not null then get user email and password
        if (email != null && password != null) {
            edtLogin_email.setText(email);
            edtLogin_pass.setText(password);
        }
    }

    //Check if user entered correct informations
    private void checkTextFieldsIsEmptyOrNot() {
        String email = edtLogin_email.getText().toString().trim();
        final String password = edtLogin_pass.getText().toString().trim();

        //if email and password field is empty
        if (TextUtils.isEmpty(email)) {
            checkStatus = false;
            edtLogin_email.setError("Check your email!");
        } else if (TextUtils.isEmpty(password)) {
            edtLogin_pass.setError("Must contain atleast 6 digits!");
        } else {
            checkStatus = true;
        }
    }

    //find views
    private void findViews() {
        edtLogin_email = findViewById(R.id.edtLogin_email);
        edtLogin_pass = findViewById(R.id.edtLogin_pass);
        progressDialog = new ProgressDialog(LoginActivity.this);
        btn_Login = findViewById(R.id.btn_login);
        sharedPref = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        rememberME = findViewById(R.id.remember_checkBox);
    }


    //Login performed using Firebase Authentication
    private void onClickLogin() {

        //assign email and password variables to my editText fields and get the typed text
        final String email = edtLogin_email.getText().toString().trim();
        final String password = edtLogin_pass.getText().toString().trim();

        //progressdialog (just for the looks)
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Get a log message if user logged in
                            Log.d("login", "Succeed login: " + email.toString() +
                                    " " + password.toString());

                            //Navigate to SongListActivity if success
                            Intent intent = new Intent(LoginActivity.this, SongListActivity.class);
                            startActivity(intent);
                        } else {
                            //Get a log message if user COULDN'T log in
                            Log.w("login", "Failed to login: " + email.toString() + " " +
                                    password.toString(), task.getException());

                            Toast.makeText(LoginActivity.this, "Wrong Email or Password!",
                                    Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    //Navigate to Register page
    public void onClickNavigatToRegisterPage(View view) {

        //Intent kan også laves sådan her:
//       Intent intent = new Intent (LoginActivity.this, RegisterActivity.class);
//       startActivity(intent);

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
