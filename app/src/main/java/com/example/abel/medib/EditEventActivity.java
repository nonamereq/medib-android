package com.example.abel.medib;

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
import com.example.abel.lib.Request.EditEventRequest;
import com.example.abel.medib2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public class EditEventActivity extends AppCompatActivity {

    EditText mTeamName1,mTeamName2,mTeamOdd1,mTeamOdd2;
    String eventId;

    private EditEventRequest request;
    private EditEventObserver observer;

    private class EditEventObserver implements Observer {

        private EditEventRequest request;

        EditEventObserver(EditEventRequest request){
            this.request = request;
        }

        @Override
        public void update(Observable o, Object arg) {
            if(request.whatHappened == EditEventRequest.RESPONSE_OR_ERROR.RESPONSE) {
                if (request.equals(o)) {
                    boolean success = request.success();
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Event saved successfully.", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    } else {

                        Toast.makeText(getApplicationContext(), "Event is not saved.", Toast.LENGTH_LONG).show();

                    }
                }
            }
            else{
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
        setContentView(R.layout.activity_edit_event);

        checkLoggedIn();

        date = Calendar.getInstance();

        Bundle bundle=getIntent().getBundleExtra("Match");
        ArrayList<String> array=bundle.getStringArrayList("Key");

        final Button mButton= findViewById(R.id.edit_event_button);

        mTeamName1 = findViewById(R.id.teamname1);
        mTeamName2 = findViewById(R.id.teamname2);
        mTeamOdd1  = findViewById(R.id.teamodd1);
        mTeamOdd2  = findViewById(R.id.teamodd2);

        mTeamName1.setText(array.get(0));
        mTeamName2.setText(array.get(1));
        mTeamOdd1.setText(array.get(2));
        mTeamOdd2.setText(array.get(3));

        eventId = array.get(4);

        request = new EditEventRequest(this);
        observer = new EditEventObserver(request);
        request.addObserver(observer);

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
        Double team1Odd = Double.parseDouble(mTeamOdd1.getText().toString());
        Double team2Odd = Double.parseDouble(mTeamOdd2.getText().toString());

        String jsonString = "{'team1_odd':" + team1Odd + " , 'team2_odd':" + team2Odd + " , 'date':" + date.getTimeInMillis() + " ,'id': " + eventId + "}";

        JSONObject requestJson = null;
        try {
            requestJson = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestJson;
    }
}