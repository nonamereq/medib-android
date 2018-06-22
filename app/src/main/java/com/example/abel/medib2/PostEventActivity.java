package com.example.abel.medib2;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abel.lib.Request.PostEventRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public class PostEventActivity extends AppCompatActivity {

    private EditText mTeamName1, mTeamName2, mTeamOdd1, mTeamOdd2;
    private Button mButton;
    
    private PostEventRequest request;
    private PostEventObserver observer;
    
    private class PostEventObserver implements Observer {

        private PostEventRequest request;

        PostEventObserver(PostEventRequest request){
            this.request = request;
        }
        @Override
        public void update(Observable o, Object arg) {
            if(request.equals(o)){
                boolean success = request.success();
                if(success){
                    Toast.makeText(getApplicationContext() , "SUCCESFULL POST" ,Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                else {
                    Toast.makeText(getApplicationContext() , "POST FAILED" ,Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public static Calendar date;

//    public static int hour;
//    public static int minute;
//    public static int day;
//    public static int month;
//    public static int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);

        request = new PostEventRequest(this);
        observer = new PostEventObserver(request);
        request.addObserver(observer);

        date = Calendar.getInstance();


        mButton = findViewById(R.id.post_event_button);
        mTeamName1 = findViewById(R.id.teamName1);
        mTeamName2 = findViewById(R.id.teamName2);
        mTeamOdd1 = findViewById(R.id.teamOdd1);
        mTeamOdd2 = findViewById(R.id.teamOdd2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                request.execute(requestJson);
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
}

