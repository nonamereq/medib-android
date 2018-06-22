package com.example.abel.lib.Request;

import android.content.Context;

import com.android.volley.Request;
import com.example.abel.lib.Constants;

import org.json.JSONObject;

public class UpdateBalanceRequest extends MedibRequest<JSONObject>{
    public UpdateBalanceRequest(Context context){
        super(context);
        requestMethod = Request.Method.POST;
    }

    @Override
    public String getUrl() {
        return Constants.UPDATEBALANCE_URL;
    }

    @Override
    public boolean authNedded() {
        return true;
    }
}
