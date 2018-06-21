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

public class EditEventActivity extends AppCompatActivity {

    EditText mTeamName1,mTeamName2,mTeamOdd1,mTeamOdd2;
    private Button mButton;
    public static String date;
    public static Calendar datee;
    public ArrayList<String> EVENT=new ArrayList<>();

    public static int hour;
    public static int minute;
    public static int day;
    public static int month;
    public static int year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        mButton=findViewById(R.id.edit_event_button);

        Bundle bundle=getIntent().getBundleExtra("Match");
        ArrayList<String> array=bundle.getStringArrayList("Key");

        mTeamName1=findViewById(R.id.teamname1);
        mTeamName2=findViewById(R.id.teamname2);
        mTeamOdd1=findViewById(R.id.teamodd1);
        mTeamOdd2=findViewById(R.id.teamodd2);

        mTeamName1.setText(array.get(0));
        mTeamName2.setText(array.get(1));
        mTeamOdd1.setText(array.get(2));
        mTeamOdd2.setText(array.get(3));
        final String eventId = array.get(4);
        Toast.makeText(getApplicationContext() , "id " + eventId , Toast.LENGTH_LONG ).show();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EVENT.add(String.valueOf(mTeamName1.getText()));
                EVENT.add(String.valueOf(mTeamName2.getText()));
                EVENT.add(String.valueOf(mTeamOdd1.getText()));
                EVENT.add(String.valueOf(mTeamOdd2.getText()));
                EVENT.add(date);
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                String url = "http://10.42.0.1:3000/admin/editEvent";

                Double team1Odd = Double.parseDouble(mTeamOdd1.getText().toString());
                Double team2Odd = Double.parseDouble(mTeamOdd2.getText().toString());



                String jsonString = "{'team1_odd':" + team1Odd + " , 'team2_odd':" + team2Odd +" , 'date':" + setTime() + " ,'id': " + eventId  +"}";


                RequestQueue requestQueue= Volley.newRequestQueue(EditEventActivity.this);
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
                            Toast.makeText(getApplicationContext() , "SUCCESFULL EDIT" ,Toast.LENGTH_LONG).show();
                            onBackPressed();

                        }
                        else {

                            Toast.makeText(getApplicationContext() , "EDIT FAILED" ,Toast.LENGTH_LONG).show();

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