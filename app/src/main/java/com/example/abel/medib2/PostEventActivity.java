package com.example.abel.medib2;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostEventActivity extends AppCompatActivity {

    private EditText mTeamName1, mTeamName2, mTeamOdd1, mTeamOdd2;
    private Button mButton;
    public static String date;
    public static Calendar datee;
    public ArrayList<String> EVENT = new ArrayList<>();

    public static int hour;
    public static int minute;
    public static int day;
    public static int month;
    public static int year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_event);


        mButton = findViewById(R.id.post_event_button);
        mTeamName1 = findViewById(R.id.teamName1);
        mTeamName2 = findViewById(R.id.teamName2);
        mTeamOdd1 = findViewById(R.id.teamOdd1);
        mTeamOdd2 = findViewById(R.id.teamOdd2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EVENT.add(String.valueOf(mTeamName1.getText()));
                EVENT.add(String.valueOf(mTeamName2.getText()));
                EVENT.add(String.valueOf(mTeamOdd1.getText()));
                EVENT.add(String.valueOf(mTeamOdd2.getText()));
                EVENT.add(date);
                String url = "http://10.42.0.1:3000/admin/postEvent";
                String team1name = mTeamName1.getText().toString();
                String team2name = mTeamName2.getText().toString();
                Double team1Odd = Double.parseDouble(mTeamOdd1.getText().toString());
                Double team2Odd = Double.parseDouble(mTeamOdd2.getText().toString());
                Date d = new Date(123);
                String date = "dsfs";
                //Date must be changed to abel's format


                String jsonString = "{'team1_name':" + team1name + " , 'team2_name':" + team2name +" , 'team1_odd':" + team1Odd + " ,'team2_odd': " + team1Odd + " , 'date':" +setTime()   +"}";

                RequestQueue  requestQueue= Volley.newRequestQueue(PostEventActivity.this);
                JSONObject requestJson = null;
                try {
                    requestJson = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestJson, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = null;
                        try {
                            status = response.getString("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Boolean b = new Boolean(status);
                        if(b){
                            Toast.makeText(getApplicationContext() , "SUCCESFULL POST" ,Toast.LENGTH_LONG).show();
                            onBackPressed();


                        }
                        else {

                            Toast.makeText(getApplicationContext() , "POST FAILED" ,Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

    requestQueue.add(jsonObjectRequest);
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

    public static long setTime(){
        datee= Calendar.getInstance();
        datee.set(Calendar.MINUTE,minute);
        datee.set(Calendar.HOUR,hour);
        datee.set(Calendar.DAY_OF_MONTH,day);
        datee.set(Calendar.MONTH,month);
        datee.set(Calendar.YEAR,year);

        return datee.getTimeInMillis();

    }

}

