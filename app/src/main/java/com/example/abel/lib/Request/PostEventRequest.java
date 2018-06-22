package com.example.abel.lib.Request;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONObject;

import com.example.abel.lib.Constants;

public class PostEventRequest extends MedibRequest<JSONObject> {

//    private static int requestMethod = Request.Method.POST;

    public PostEventRequest(Context context){
        super(context);
        requestMethod = Request.Method.POST;
    }

    @Override
    public String getUrl() {
        return Constants.POST_EVENT;
    }

    @Override
    public boolean authNedded() {
        return true;
    }
}
