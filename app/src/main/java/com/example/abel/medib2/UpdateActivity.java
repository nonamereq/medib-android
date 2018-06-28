package com.example.abel.medib2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.NetworkErrorAlert;
import com.example.abel.lib.Request.UpdateBalanceRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class UpdateActivity extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEditText;

    private UpdateBalanceRequest request;
    private UpdateBalanceObserver observer;

    private class UpdateBalanceObserver implements Observer {
        private UpdateBalanceRequest request;

        public UpdateBalanceObserver(UpdateBalanceRequest request){
            this.request = request;
        }

        @Override
        public void update(Observable o, Object arg) {
            if(request.equals(o)){
                if(request.whatHappened == UpdateBalanceRequest.RESPONSE_OR_ERROR.RESPONSE) {
                    boolean status = request.success();
                    if (status) {
                        Toast.makeText(getApplicationContext(), "Successful update.", Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error updating your balance.", Toast.LENGTH_LONG);
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

    public void checkLoggedIn() {
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
        setContentView(R.layout.activity_update);

        checkLoggedIn();

        final Button mButton = (Button) findViewById(R.id.update_button);

        mEditText=(EditText) findViewById(R.id.update_amount);
        mTextView=(TextView) findViewById(R.id.current_balance_update);

        request = new UpdateBalanceRequest(this);
        observer = new UpdateBalanceObserver(request);
        request.addObserver(observer);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.execute(constructRequest());
            }


        });
    }

    public JSONObject constructRequest(){
        final Double updateAmount = Double.parseDouble(mEditText.getText().toString());
        String jsonString = "{'amount':" + updateAmount  + "}";
        JSONObject requestJson = null ;

        try {
            requestJson = new JSONObject(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestJson;
    }
}
