package com.example.abel.lib.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abel.lib.Authenticator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public abstract class MedibRequest<T> extends Observable{
    public enum RESPONSE_OR_ERROR {
        RESPONSE,
        ERROR,
    };

    protected Context context;
    protected boolean executeCalled = false;
    protected static int requestMethod;


    protected VolleyError networkError;
    public RESPONSE_OR_ERROR whatHappened;

    private int status;
    protected JSONObject response;

    MedibRequest(Context context){
        super();
        this.context = context;
        this.response = null;
        this.status = 0;
    }

    public abstract  String getUrl();
    public abstract boolean authNedded();

    public void execute(JSONObject json){
        if(authNedded()){
            Authenticator auth = Authenticator.getInstance(context);
            try {
                if(json == null)
                    json = new JSONObject();
                json.put("token", auth.getToken());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, getUrl(), json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                response = res;
                executeCalled = true;
                whatHappened = RESPONSE_OR_ERROR.RESPONSE;
                setChanged();
                notifyObservers();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("execute ", error.toString());

                networkError = error;
                status = networkError.networkResponse.statusCode;

                executeCalled = true;
                whatHappened = RESPONSE_OR_ERROR.ERROR;
                setChanged();
                notifyObservers();
            }
        });
        requestQueue.add(jsonObjectRequest);
    };

    public boolean success(){
        boolean success = false;
        try {
            success = Boolean.parseBoolean(response.getString("success"));
        }catch(JSONException except) {
            except.printStackTrace();
        }
        return success;
    }

    public int status(){
        if(executeCalled){
            return status;
        }
        else
            throw (new RuntimeException("You need to call execute first"));
    }

    public JSONObject getErrors(){
        if(executeCalled){
            if(response != null){
                try{
                    return response.getJSONObject("err");
                }
                catch(JSONException e){
                    return null;
                }
            }
            else
                return null;
        }
        else {
            throw (new RuntimeException("You need to call execute first"));
        }
    }

    public VolleyError getNetworkError(){
        return networkError;
    }

    public T getDoc(){
        if(executeCalled){
            if(response != null){
                try{
                    return (T)response.get("doc");
                }
                catch(JSONException e){
                    return null;
                }
            }
            else
                return null;
        }
        else {
            throw (new RuntimeException("You need to call execute first"));
        }
    }

    public String getString(String key) {
        String object = null;
        try {
            object = response.getString(key);
        } catch (JSONException except) {
            except.printStackTrace();
        }
        return object;
    }

    public Context getContext(){
        return context;
    }
}