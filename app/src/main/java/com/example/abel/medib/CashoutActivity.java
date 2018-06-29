package com.example.abel.medib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abel.lib.Authenticator;
import com.example.abel.lib.NetworkErrorAlert;
import com.example.abel.lib.Request.CashOutRequest;
import com.example.abel.medib2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class CashoutActivity extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEditText;

    CashOutRequest request;
    CashOutObserver observer;

    private class CashOutObserver implements Observer{
        CashOutRequest request;

        CashOutObserver(CashOutRequest request){
            this.request = CashoutActivity.this.request;
        }

        @Override
        public void update(Observable o, Object arg) {
            if(request.equals(o)){
                if(request.whatHappened == CashOutRequest.RESPONSE_OR_ERROR.RESPONSE) {
                    boolean success = request.success();
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Cashout successful", Toast.LENGTH_LONG).show();
                        //                    Double updated = currentAmount - cashoutAmount;
                        //                    mTextView.setText(updated.toString());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR CASHING OUT", Toast.LENGTH_LONG);
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
        setContentView(R.layout.activity_cashout);

        checkLoggedIn();

        mTextView=(TextView) findViewById(R.id.current_balance_cashout);
        mEditText=(EditText) findViewById(R.id.cashout_amount);
        final Button mButton =(Button) findViewById(R.id.cashout_button);

        request = new CashOutRequest(this);
        observer = new CashOutObserver(request);
        request.addObserver(observer);

//        mTextView.setText(currentAmount.toString());

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                request.execute(constructRequest());
            }
        });
    }

    public JSONObject constructRequest(){
        final Double  cashoutAmount  = Double.parseDouble(mEditText.getText().toString());

        String jsonString = "{'amount':" + cashoutAmount  + "}";
        JSONObject requestJson = null ;

        try {
            requestJson = new JSONObject(jsonString);
            Log.d("error" , String.valueOf((requestJson==null)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestJson;
    }
}



