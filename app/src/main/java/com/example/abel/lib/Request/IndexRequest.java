package com.example.abel.lib.Request;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONArray;

import com.example.abel.lib.Constants;

public class IndexRequest extends MedibRequest<JSONArray> {
    public IndexRequest(Context context){
        super(context);
        requestMethod = Request.Method.GET;
    }

    @Override
    public String getUrl() {
        return Constants.INDEX_URL;
    }

    @Override
    public boolean authNedded() {
        return false;
    }
}
