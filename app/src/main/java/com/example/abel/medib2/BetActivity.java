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
import com.example.abel.lib.Authenticator;
import com.example.abel.lib.Request.BetRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BetActivity extends AppCompatActivity {
    private TextView mTeamName1,mTeamName2, mTeamOdd1,mTeamOdd2,mProfit;
    private EditText mBetAmount;
    private Button mButton;
    private CheckBox mBoxTeam1, mBoxTeam2;
    private boolean checked=false;

    Authenticator auth;
    BetRequest request;
    BetObserver observer;

    private  class BetObserver implements Observer {
        BetRequest request;

        public BetObserver(BetRequest request){
            this.request = request;
        }
        @Override
        public void update(Observable o, Object arg) {
            if(request.equals(o)){
                boolean success = request.success();
                if(success){
                    Toast.makeText(getApplicationContext() , "SUCCESSFUL BET" , Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                else {
                    Toast.makeText(getApplicationContext() , "ERROR" ,Toast.LENGTH_LONG);
                }
            }
        }
    }

    public void checkLoggedIn(){
        auth = Authenticator.getInstance(this);
        if(auth.getToken() != null){
            if (auth.isAdmin()) {
                Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                startActivity(intent);
            }
        }
        else{
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        checkLoggedIn();

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

        request = new BetRequest(this);
        observer = new BetObserver(request);
        request.addObserver(observer);

        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("Match");
        ArrayList<String> items=bundle.getStringArrayList("Key");
        mTeamName1.setText(items.get(0));
        mTeamName2.setText(items.get(1));
        mTeamOdd1.setText(items.get(2));
        mTeamOdd2.setText(items.get(3));

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

                    String jsonString = "{'id':" + eventId + " , 'team_name':" + selectedTeam +" , 'amount':" + betAmount +"}";

                    JSONObject requestJson = null;
                    try {
                        requestJson = new JSONObject(jsonString );

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("json error " , e.getMessage());
                    }

                    request.execute(requestJson);
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
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
