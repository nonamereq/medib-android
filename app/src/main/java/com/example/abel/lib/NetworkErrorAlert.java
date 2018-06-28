package com.example.abel.lib;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.abel.lib.Request.MedibRequest;

import org.json.JSONObject;

public class NetworkErrorAlert {
    public static AlertDialog createDialog(final Context context, String message, final MedibRequest request, final JSONObject jsonObject){
        AlertDialog alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Network Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                request.execute(jsonObject);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });
        return alertDialog;
    }
}
