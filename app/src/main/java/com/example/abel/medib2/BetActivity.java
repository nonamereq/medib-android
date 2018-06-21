package com.example.abel.medib2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BetActivity extends AppCompatActivity {
    private TextView mTeamName1,mTeamName2, mTeamOdd1,mTeamOdd2,mProfit;
    private EditText mBetAmount;
    private Button mButton;
    private CheckBox mBoxTeam1, mBoxTeam2;
    private boolean checked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        mTeamName1=findViewById(R.id.team_name_1_bet);
        mTeamName2=findViewById(R.id.team_name_2_bet);
        mTeamOdd1=findViewById(R.id.team_odd_1_bet);
        mTeamOdd2=findViewById(R.id.team_odd_2_bet);
        mProfit=findViewById(R.id.bet_profit);
        mBetAmount=findViewById(R.id.bet_amount);
        mButton=findViewById(R.id.submit_bet_button);
        mBoxTeam1=findViewById(R.id.team_1_button);
        mBoxTeam2=findViewById(R.id.team_2_button);
        Toolbar toolbar=findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("Match");
        ArrayList<String> items=bundle.getStringArrayList("Key");
        mTeamName1.setText(items.get(0));
        mTeamName2.setText(items.get(1));
        mTeamOdd1.setText(items.get(2));
        mTeamOdd2.setText(items.get(3));
        final String token = items.get(5);
        Log.d("aaa" , token);
//        Log.d("aaa" , items.get(4));

        final String eventId  = items.get(4);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "http://10.42.0.1:3000/user/bet";
                Double betAmount = Double.parseDouble(mBetAmount.getText().toString());
                String selectedTeam  = "";
                Boolean validToBet = null;

                if (mBoxTeam1.isChecked()){
                     selectedTeam = mTeamName1.getText().toString();
                    Log.d("inside team1_Selected" , selectedTeam + "team1_selected");
                     validToBet = true;
                }
                else if (mBoxTeam2.isChecked()){
                    selectedTeam = mTeamName2.getText().toString();
                    Log.d("inside team2_Selected" , selectedTeam +"team2_selected");
                    validToBet = true;
                }
                else {
                    validToBet = false;
                }
                if(validToBet){

                    String jsonString = "{'id':" + eventId + " , 'team_name':" + selectedTeam +" , 'amount':" + betAmount + " ,'token': " + token +"}";
                    Log.d("aaa", jsonString);

                    JSONObject requestJson = null;
                    try {
                        requestJson = new JSONObject(jsonString );

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("json error " , e.getMessage());
                    }


                    RequestQueue requestQueue = Volley.newRequestQueue(BetActivity.this);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status = response.getString("success");
                                Boolean b = new Boolean(status);
                                if(b){ // code for successful bet
                                    Toast.makeText(getApplicationContext() , "SUCCESSFUL BET" , Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                    //Intent i = new Intent(getApplicationContext() , MainActivity.class);
                                    //setContentView(R.layout.activity_main);
                                    //Bundle bund = new Bundle();
                                    //bund.putString("tok" , token);
                                    //i.putExtras(i);
                                    //startActivity(i);


                                }
                                else {  // failed bet error display
                                    Toast.makeText(getApplicationContext() , "ERROR" ,Toast.LENGTH_LONG);


                                }

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
                else {

                }


            }
        });


        mBoxTeam2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mBoxTeam1.isChecked())
                    mBoxTeam1.setChecked(false);
            }
        });

        mBoxTeam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBoxTeam2.isChecked())
                    mBoxTeam2.setChecked(false);
            }
        });
        mBetAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double d;
                if (mBoxTeam1.isChecked()){
                    d=Double.parseDouble((String) mTeamOdd1.getText());
                }
                else if (mBoxTeam2.isChecked())d=Double.parseDouble((String)mTeamOdd2.getText());
                else d=1;
//                if(!charSequence.equals("") ) {
//                    mProfit.setText(Double.toString(Double.parseDouble(charSequence.toString())*d));
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
