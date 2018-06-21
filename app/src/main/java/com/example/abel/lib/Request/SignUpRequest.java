package com.example.abel.lib.Request;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abel.lib.Constants;

public class SignUpRequest extends MedibRequest<JSONObject>{
    private static int requestMethod = Request.Method.POST;

    public SignUpRequest(Context context){
        super(context);
    }

    public String getUrl(){
        return Constants.SIGNUP_URL;
    }

    public void execute(JSONObject json){
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, Constants.SIGNUP_URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                response = res;
                executeCalled = true;
                setChanged();
                notifyObservers();
                Log.d("execute ", "notify called");
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
