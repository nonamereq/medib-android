package com.example.abel.lib.Request;

import android.content.Context;

import com.example.abel.lib.Constants;

import org.json.JSONObject;

public class UserInfoRequest extends MedibRequest<JSONObject> {

    public UserInfoRequest(Context context){
        super(context);
    }

    @Override
    public String getUrl() {
        return Constants.USERINFO_URL;
    }

    @Override
    public boolean authNedded() {
        return true;
    }

    public void execute(){
        super.execute(null);
    }
}
