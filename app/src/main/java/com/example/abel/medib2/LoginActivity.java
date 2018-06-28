package com.example.abel.medib2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

import com.example.abel.lib.NetworkErrorAlert;
import com.example.abel.lib.Request.LoginRequest;
import com.example.abel.lib.Authenticator;
import com.example.abel.lib.Request.MedibRequest;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText usernameField;
    private EditText passwordField;

    //private variables
    private Authenticator auth;
    private LoginRequest request;
    private LoginObserver observer;

    private class LoginObserver implements Observer {
        LoginRequest request;

        public LoginObserver(LoginRequest request) {
            this.request = request;
        }

        @Override
        public void update(Observable o, Object arg) {
            Log.d("execute ", "update called");
            if (request.equals(o)) {
                if(request.whatHappened == LoginRequest.RESPONSE_OR_ERROR.RESPONSE) {
                    try {
                        boolean success = request.success();
                        Log.d("execute", "equal");
                        if (success) {
                            JSONObject userObject = request.getDoc();
                            Double currentAmount = Double.parseDouble(userObject.getString("amount"));
                            Log.d("value", "amount is" + currentAmount.toString());
                            String token = request.getString("token");
                            Boolean isAdmin = userObject.getBoolean("isAdmin");

                            auth.storeToken(token, isAdmin);

                            Toast.makeText(getApplicationContext(), " Logged in", Toast.LENGTH_LONG).show();
                            Log.d("admin", isAdmin.toString());
                            Intent intent;
                            if (isAdmin) {
                                intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                            } else {
                                intent = new Intent(getApplicationContext(), MainActivity.class);
                            }

                            startActivity(intent);
                        } else {

                            Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else{
                    if(request.status() == 500){

                    }
                    else{
                        if(request.status() == 500){
                            NetworkErrorAlert.createDialog(request.getContext(), "Server error. Please try again.", request, constructRequest()).show();
                        }
                        else{
                            NetworkErrorAlert.createDialog(request.getContext(), "Network connection error. Please try again", request, constructRequest()).show();
                        }
                    }
                }
            } else {
                Log.d("execute", "not equal");
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
        setContentView(R.layout.activity_login);

        // Set up the login form.
        usernameField = (EditText) findViewById(R.id.userName);
        passwordField = (EditText) findViewById(R.id.login_password);
        Button loginButton = (Button) findViewById(R.id.loginBtn);

        checkLoggedIn();

        request = new LoginRequest(LoginActivity.this);
        observer = new LoginObserver(request);
        request.addObserver(observer);

        request.getUrl();

        CheckBox showPassword = (CheckBox) findViewById(R.id.show_hide_password);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameField.getText().toString().trim().equals("")) {
                    usernameField.setError("User Name Required!");
                }

                if (passwordField.getText().toString().trim().equals("")) {
                    passwordField.setError("Password Required");
                } else {


                    request.execute(constructRequest());
                }
            }

        });
    }

    public JSONObject constructRequest(){
        String name = usernameField.getText().toString();
        String pass = passwordField.getText().toString();
        String json = "{'name':" + name + ", 'password': " + pass + "}";

        JSONObject requestJson = null;
        try {
            requestJson = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestJson;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void goToSignup(View v) {
        onBackPressed();
    }
}
