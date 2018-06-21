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

import org.json.JSONException;
import org.json.JSONObject;

public class CashoutActivity extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashout);
        mTextView=(TextView) findViewById(R.id.current_balance_cashout);
        mEditText=(EditText) findViewById(R.id.cashout_amount);
        mButton=(Button) findViewById(R.id.cashout_button);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String cash = b.getString("cash");

        final Double currentAmount = Double.parseDouble(cash);
        mTextView.setText(currentAmount.toString());

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String url = "http://10.21.42.253:3000/cashout";
                final String url = "http://10.42.0.1:3000/user/cashout";
                final Double  cashoutAmount  = Double.parseDouble(mEditText.getText().toString());

                String jsonString = "{'amount':" + cashoutAmount  + "}";
                JSONObject requestJson = null ;

                try {
                    requestJson = new JSONObject(jsonString);
                    Log.d("error" , String.valueOf((requestJson==null)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestQueue requestQueue = Volley.newRequestQueue(CashoutActivity.this);
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
                        if(b){ // code for successful bet
                            Toast.makeText(getApplicationContext() , "SUCCESSFUL CASHOUT" , Toast.LENGTH_LONG).show();

                            Double updated = currentAmount - cashoutAmount;
                            mTextView.setText(updated.toString());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            onBackPressed();
                        }
                        else {  // failed bet error display
                            Toast.makeText(getApplicationContext() , "ERROR CASHING OUT" ,Toast.LENGTH_LONG);


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
}



