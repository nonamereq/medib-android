package com.example.abel.lib;

import android.content.Context;
import android.util.Log;

public class Authenticator {
    private static Authenticator ourInstance = null;

    String token;
    Boolean isAdmin;
    DatabaseHelper helper;
    Context context;

    static public Authenticator getInstance(Context context){
        if(ourInstance == null){
            ourInstance = new Authenticator(context.getApplicationContext());
        }
        return ourInstance;
    }

    private Authenticator(Context context){
        this.context = context;
        token = null;
        isAdmin = null;
        helper = new DatabaseHelper(context);
    }

    public String getToken(){
        if(token == null){
            token = helper.get("token");
        }
        return token;
    }

    public boolean isAdmin(){
        if(isAdmin == null){
            isAdmin = Boolean.parseBoolean(helper.get("isAdmin"));
        }
        return isAdmin;
    }

    public void removeToken(){
        Log.d("execute ", "Logging out user.");
        helper.delete("token");
        helper.delete("isAdmin");
        token = null;
        isAdmin = null;
    }

    public void storeToken(String token, boolean isAdmin){
        this.token = token;
        this.isAdmin = isAdmin;
        helper.store("token", token);
        helper.store("isAdmin", Boolean.toString(isAdmin));
    }
}
