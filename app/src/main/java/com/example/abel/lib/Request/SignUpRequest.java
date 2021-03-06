package com.example.abel.lib.Request;

import android.content.Context;

import org.json.JSONObject;

import com.android.volley.Request;

import com.example.abel.lib.Constants;

public class SignUpRequest extends MedibRequest<JSONObject>{
    public SignUpRequest(Context context){
        super(context);
        requestMethod = Request.Method.POST;
    }

    public String getUrl(){
        return Constants.SIGNUP_URL;
    }

    @Override
    public boolean authNedded() {
        return false;
    }
}
