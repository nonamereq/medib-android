package com.example.abel.medib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.NetworkErrorAlert;
import com.example.abel.lib.Request.BetRequest;
import com.example.abel.medib2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BetActivity extends AppCompatActivity {
    private TextView mTeamName1,mTeamName2, mTeamOdd1,mTeamOdd2,mProfit;
    private EditText mBetAmount;
    private CheckBox mBoxTeam1, mBoxTeam2;
    private TextView checked = null;

    private String eventId;

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
                if(request.whatHappened == BetRequest.RESPONSE_OR_ERROR.RESPONSE) {
                    boolean success = request.success();
                    if (success) {
                        Toast.makeText(getApplicationContext(), "SUCCESSFUL BET", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
                    }
                } else{
                    int status = request.status();
                    if(status == 500){
                        NetworkErrorAlert.createDialog(request.getContext(), "Server error. Please try again.", request, constructRequest()).show();
                    }
                    else if(status == 401){
                        Authenticator.getInstance(request.getContext()).removeToken();
                        Toast.makeText(request.getContext(), "You have to login first ", Toast.LENGTH_LONG).show();
                        checkLoggedIn();
                    }
                    else{
                        NetworkErrorAlert.createDialog(request.getContext(), "Network connection error. Please try again", request, constructRequest()).show();
                    }
                }
            }
        }
    }

    public void checkLoggedIn(){
        Authenticator auth = Authenticator.getInstance(this);
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
        mBoxTeam1=findViewById(R.id.team_1_button);
        mBoxTeam2=findViewById(R.id.team_2_button);

        final Button mButton=findViewById(R.id.submit_bet_button);

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

        eventId  = items.get(4);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checked != null) {
                    request.execute(constructRequest());
                }
                else {
                    Toast.makeText(getApplicationContext(), "You have to select a team", Toast.LENGTH_LONG).show();
                }
            }
        });


        mBoxTeam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBoxTeam2.isChecked())
                    mBoxTeam2.setChecked(false);

                checked = mTeamName1;
            }
        });

        mBoxTeam2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mBoxTeam1.isChecked())
                    mBoxTeam1.setChecked(false);

                checked = mTeamName2;
            }
        });


    }


    public JSONObject constructRequest(){
        Double betAmount = Double.parseDouble(mBetAmount.getText().toString());
        String selectedTeam  = checked.getText().toString();
        String jsonString = "{'id':" + eventId + " , 'team_name':" + selectedTeam +" , 'amount':" + betAmount +"}";
        Log.d("execute ", jsonString);

        JSONObject requestJson = null;
        try {
            requestJson = new JSONObject(jsonString );

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("json error " , e.getMessage());
        }

        return requestJson;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
