package com.example.abel.medib2;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class SignupActivity extends AppCompatActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

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
                            Toast.makeText(SignupActivity.this , "on click" , Toast.LENGTH_LONG).show();

                            final String url = "http://10.42.0.1:3000/api/signup";
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

                            RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String status = response.getString("success");

                                        boolean b = Boolean.parseBoolean(status);
                                        if (b) {
                                            Log.d("Medib", Boolean.toString(b));
                                            Toast.makeText(getApplicationContext() , "status true " , Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(i);
                                        } else {

                                            Toast.makeText(getApplicationContext(), "sign up failed", Toast.LENGTH_LONG).show();
                                        }
                                        Log.d("inside response" , "response");


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //Log.d("inside error response" , error.getMessage());

                                }
                            });

                            requestQueue.add(jsonObjectRequest);

                        }
                        else{
                            confirmPassField.setError("Passwords Don't Match");
                        }
                    }

                }
            });


        }
    public void goToLogin(View v){
        Toast.makeText(getApplicationContext() , "LOGIN clicked" , Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext() , LoginActivity.class);
        //setContentView(R.layout.activity_login);
        startActivity(i);

    }

    }


