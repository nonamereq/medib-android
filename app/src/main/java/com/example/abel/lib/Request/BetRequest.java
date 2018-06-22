package com.example.abel.lib.Request;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONObject;

import com.example.abel.lib.Constants;

public class BetRequest extends MedibRequest<JSONObject> {
    public BetRequest(Context context){
        super(context);
        requestMethod = Request.Method.POST;
    }

    @Override
    public String getUrl() {
        return Constants.BET_URL;
    }

    @Override
    public boolean authNedded() {
        return true;
    }
}
