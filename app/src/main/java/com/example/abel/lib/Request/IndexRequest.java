package com.example.abel.lib.Request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abel.lib.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

public class IndexRequest extends MedibRequest<JSONArray> {
    private static int requestMethod = Request.Method.GET;

    public IndexRequest(Context context){
        super(context);
    }

    @Override
    public String getUrl() {
        return Constants.INDEX_URL;
    }

    @Override
    public void execute(JSONObject json) {
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, Constants.INDEX_URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                response = res;
                executeCalled = true;
                setChanged();
                notifyObservers();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                executeCalled = true;
                setChanged();
                notifyObservers();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}