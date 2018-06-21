package com.example.abel.medib2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.Request.SignUpRequest;


public class SignupActivity extends AppCompatActivity {

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    //private variables
    private Authenticator auth;
    private SignUpRequest request;
    private SignUpObserver observer;

    private class SignUpObserver implements Observer{
        private  SignUpRequest request;

        public SignUpObserver(SignUpRequest request){
            this.request = request;
        }

        @Override
        public void update(Observable o, Object arg) {
            Log.d("execute ", "on update");
            if(request.equals(o)){
                boolean success = request.success();
                if (success) {
                    Toast.makeText(getApplicationContext() , "Registered Successfully." , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                } else {

                    Toast.makeText(getApplicationContext(), "Not registered.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void checkLoggedIn(){
        auth = Authenticator.getInstance(this);
        if(auth.getToken() != null){
            Intent intent;
            if (auth.isAdmin()) {
                intent = new Intent(getApplicationContext(), AdminMainActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }

            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
         //Set up the login form.
            final EditText unameField = (EditText) findViewById(R.id.userName);
            final EditText passField = (EditText) findViewById(R.id.password);
            final EditText emailField = (EditText) findViewById(R.id.userEmailId);
            final EditText confirmPassField = (EditText) findViewById(R.id.confirmPassword);
            final Button signUpButton = (Button) findViewById(R.id.signUpBtn);

            checkLoggedIn();

            request = new SignUpRequest(this);
            observer = new SignUpObserver(request);
            request.addObserver(observer);

            signUpButton.setEnabled(false);
            CheckBox terms = (CheckBox) findViewById(R.id.terms_conditions);
            terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        signUpButton.setEnabled(true);
                    }
                    else{
                        signUpButton.setEnabled(false);
                    }
                }
            });
            signUpButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(unameField.getText().toString().trim().equals("")){
                        unameField.setError("User Name Required");
                    }
                    if (emailField.getText().toString().trim().equals("")){
                        emailField.setError("Email Required");
                    }
                    if (passField.getText().toString().trim().equals("")){
                        passField.setError("Password Required");
                    }
                    else{
                        if(passField.getText().toString().trim().equals(confirmPassField.getText().toString().trim())){
                            String name = unameField.getText().toString();
                            String pass = passField.getText().toString();
                            String email = emailField.getText().toString();

                            String json = "{'name':" + name + ", 'password': " + pass + ", 'email': " + email + "}";
                            JSONObject requestJson = null;

                            try {
                                requestJson = new JSONObject(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            request.execute(requestJson);
                        }
                        else{
                            confirmPassField.setError("Passwords Don't Match");
                        }
                    }

                }
            });


        }

        public void goToLogin(View v){
            Intent i = new Intent(this.getApplicationContext() , LoginActivity.class);
            startActivity(i);
        }
    }
