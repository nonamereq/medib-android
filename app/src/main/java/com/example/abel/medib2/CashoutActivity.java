package com.example.abel.medib2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abel.lib.Request.CashOutRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;
import java.util.Observer;

public class CashoutActivity extends AppCompatActivity {
    private TextView mTextView;
    private EditText mEditText;
    private Button mButton;

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
                boolean success = request.success();
                if(success){
                    Toast.makeText(getApplicationContext() , "Cashout successful" , Toast.LENGTH_LONG).show();
//                    Double updated = currentAmount - cashoutAmount;
//                    mTextView.setText(updated.toString());
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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashout);
        mTextView=(TextView) findViewById(R.id.current_balance_cashout);
        mEditText=(EditText) findViewById(R.id.cashout_amount);
        mButton=(Button) findViewById(R.id.cashout_button);

        request = new CashOutRequest(this);
        observer = new CashOutObserver(request);
        request.addObserver(observer);

//        mTextView.setText(currentAmount.toString());

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Double  cashoutAmount  = Double.parseDouble(mEditText.getText().toString());

                String jsonString = "{'amount':" + cashoutAmount  + "}";
                JSONObject requestJson = null ;

                try {
                    requestJson = new JSONObject(jsonString);
                    Log.d("error" , String.valueOf((requestJson==null)));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                request.execute(requestJson);
            }
        });
    }
}



