package com.example.abel.lib.Request;

import android.content.Context;

import org.json.JSONObject;

import com.android.volley.Request;

import com.example.abel.lib.Constants;


public class LoginRequest  extends MedibRequest<JSONObject>{
//    private static int requestMethod = Request.Method.POST;

    public LoginRequest(Context context){
        super(context);
        requestMethod = Request.Method.POST;
    }

    @Override
    public String getUrl(){ return Constants.LOGIN_URL; }

    @Override
    public boolean authNedded() {
        return false;
    }
}
