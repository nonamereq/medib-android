package com.example.abel.medib2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.NetworkErrorAlert;
import com.example.abel.lib.Request.PostEventRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public class PostEventActivity extends AppCompatActivity {

    private EditText mTeamName1, mTeamName2, mTeamOdd1, mTeamOdd2;

    private PostEventRequest request;
    private PostEventObserver observer;
    
    private class PostEventObserver implements Observer {

        private PostEventRequest request;

        PostEventObserver(PostEventRequest request){
            this.request = request;
        }
        @Override
        public void update(Observable o, Object arg) {
            if(request.whatHappened == PostEventRequest.RESPONSE_OR_ERROR.RESPONSE) {
                if (request.equals(o)) {
                    boolean success = request.success();
                    if (success) {
                        Toast.makeText(getApplicationContext(), "SUCCESFULL POST", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "POST FAILED", Toast.LENGTH_LONG).show();
                    }
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

    public static Calendar date;

    public void checkLoggedIn(){
        Authenticator auth = Authenticator.getInstance(this);
        if(auth.getToken() != null){
            if (!auth.isAdmin()) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        setContentView(R.layout.activity_post_event);

        checkLoggedIn();

        request = new PostEventRequest(this);
        observer = new PostEventObserver(request);
        request.addObserver(observer);

        date = Calendar.getInstance();


        final Button mButton = findViewById(R.id.post_event_button);

        mTeamName1 = findViewById(R.id.teamName1);
        mTeamName2 = findViewById(R.id.teamName2);
        mTeamOdd1  = findViewById(R.id.teamOdd1);
        mTeamOdd2  = findViewById(R.id.teamOdd2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.execute(constructRequest());
            }


        });

    }


    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    public JSONObject constructRequest(){
        String team1name = mTeamName1.getText().toString();
        String team2name = mTeamName2.getText().toString();
        double team1Odd;
        try {
            team1Odd = Double.parseDouble(mTeamOdd1.getText().toString());
        }catch (Exception e){
            team1Odd = 0;
        }
        double team2Odd;
        try {
            team2Odd = Double.parseDouble(mTeamOdd2.getText().toString());
        }catch(Exception e){
            team2Odd = 0;

        }

        String jsonString = "{'team1_name':" + team1name + " , 'team2_name':" + team2name +" , 'team1_odd':" + team1Odd + " ,'team2_odd': " + team2Odd + " , 'date':" +date.getTimeInMillis()  +"}";

        JSONObject requestJson = null;
        try {
            requestJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return requestJson;
    }
}

