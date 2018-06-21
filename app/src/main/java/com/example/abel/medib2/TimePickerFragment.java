package com.example.abel.medib2;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), timeSetListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Toast.makeText(getActivity(), "selected time is "
                                    + view.getHour() +
                                    " / " + view.getMinute()
                            , Toast.LENGTH_SHORT).show();
                    Button button=getActivity().findViewById(R.id.pickTime);
                    button.setText(view.getHour()+":"+view.getMinute());
                    if (getActivity() instanceof PostEventActivity){
                        PostEventActivity.hour=view.getHour();
                        PostEventActivity.minute=view.getMinute();
                        Log.d("AAAA", String.valueOf(PostEventActivity.hour));
                    }
                    if (getActivity() instanceof EditEventActivity){
                        EditEventActivity.hour=view.getHour();
                        EditEventActivity.minute=view.getMinute();
                        Log.d("AAAA", String.valueOf(EditEventActivity.hour));
                    }


                }
            };


}
