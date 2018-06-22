package com.example.abel.lib.Request;

import android.content.Context;

import com.android.volley.Request;
import com.example.abel.lib.Constants;

import org.json.JSONObject;

public class CashOutRequest extends MedibRequest<JSONObject> {
    public CashOutRequest(Context context) {
        super(context);
        requestMethod = Request.Method.POST;
    }

    @Override
    public String getUrl() {
        return Constants.CASHOUT_URL;
    }

    @Override
    public boolean authNedded() {
        return true;
    }
}
