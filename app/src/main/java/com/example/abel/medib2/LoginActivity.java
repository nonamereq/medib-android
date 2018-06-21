package com.example.abel.medib2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abel.medib2.contents.MatchContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity{
    
    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private TextView mTextView;
    //public static  Double userCash =0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        final EditText usernameField  = (EditText) findViewById(R.id.userName);
        final EditText passwordField = (EditText) findViewById(R.id.login_password);
        Button loginButton = (Button) findViewById(R.id.loginBtn);

        CheckBox showPassword = (CheckBox) findViewById(R.id.show_hide_password);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if( usernameField.getText().toString().trim().equals("")) {
                    usernameField.setError( "User Name Required!");
                }

                if (passwordField.getText().toString().trim().equals("")){
                    passwordField.setError("Password Required");
                }
                else{
                    //String url = "http://10.21.42.230:3000/login" ;
                    final String url = "http://10.42.0.1:3000/api/login";
                    String name = usernameField.getText().toString();
                    String pass = passwordField.getText().toString();
                    String json = "{'name':" +name +", 'password': " + pass +"}";

                    JSONObject requestJson = null;
                    try {
                        requestJson = new JSONObject(json);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status = response.getString("success");
                                boolean b = Boolean.parseBoolean(status);
                                if(b) {
                                    JSONObject userObject = response.getJSONObject("doc");
                                    Double currentAmount = Double.parseDouble(userObject.getString("amount"));
                                     // userCash = currentAmount;
                                    Toast.makeText(getApplicationContext(), "user's cash is" + currentAmount, Toast.LENGTH_LONG).show();
                                    Log.d("value", "amount is" + currentAmount.toString());
                                    String token = response.getString("token");

                                    Toast.makeText(getApplicationContext(), " Loged IN", Toast.LENGTH_LONG).show();
                                    Bundle bund = new Bundle();
                                    Boolean isAdmin = new Boolean(response.getString("isAdmin"));
                                    bund.putString("tok" , token);
                                    bund.putString("cash" , currentAmount.toString());
                                    Log.d("admin" , isAdmin.toString());
                                    Intent intent ;
                                    if (isAdmin) {
                                         intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                        intent.putExtras(bund);
                                    }
                                    else {
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtras(bund);
                                    }


                                    //Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);


                                    //startActivity(intent);

                                }
                                else  {

                                    Toast.makeText(getApplicationContext() , "Login error" ,Toast.LENGTH_LONG ).show();
                                }
                               // JSONObject userInfo = response.getJSONObject("doc");
                                //String token = response.getString("token");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(jsonObjectRequest);
                }
                }

        });
    }



}


