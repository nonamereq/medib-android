package com.example.abel.medib2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abel.lib.Request.UpdateBalanceRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class UpdateActivity extends AppCompatActivity {
    private Button mButton;
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
                boolean status = request.success();
                if(status){
                    Toast.makeText(getApplicationContext() , "Successful update." , Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    onBackPressed();
                }
                else {
                    Toast.makeText(getApplicationContext() , "Error updating your balance." ,Toast.LENGTH_LONG);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        mEditText=(EditText) findViewById(R.id.update_amount);
        mTextView=(TextView) findViewById(R.id.current_balance_update);
        mButton = (Button) findViewById(R.id.update_button);

        request = new UpdateBalanceRequest(this);
        observer = new UpdateBalanceObserver(request);
        request.addObserver(observer);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Double updateAmount = Double.parseDouble(mEditText.getText().toString());
                String jsonString = "{'amount':" + updateAmount  + "}";
                JSONObject requestJson = null ;

                try {
                    requestJson = new JSONObject(jsonString);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                request.execute(requestJson);
            }


        });
    }
}
