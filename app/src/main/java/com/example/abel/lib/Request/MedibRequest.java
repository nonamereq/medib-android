package com.example.abel.lib.Request;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public abstract class MedibRequest<T> extends Observable{
    protected Context context;
    protected JSONObject response;
    private int status;
    protected boolean executeCalled = false;

    MedibRequest(Context context){
        super();
        this.context = context;
        this.response = null;
        this.status = 0;
    }

    public abstract  String getUrl();

    public abstract  void execute(JSONObject json);

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
}