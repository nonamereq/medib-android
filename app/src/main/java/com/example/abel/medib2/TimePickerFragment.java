package com.example.abel.medib2;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.abel.lib.Request.PostEventRequest;

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
//                @RequiresApi(api = Build.VERSION_CODES.M)
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String Pmam = hourOfDay > 12 ?  "PM" : "AM";
                    Button button=getActivity().findViewById(R.id.pickTime);
                    button.setText(Integer.toString(hourOfDay > 12 ? hourOfDay - 12: hourOfDay)+":"+Integer.toString(minute) + " " + Pmam);
                    if (getActivity() instanceof PostEventActivity){
                        PostEventActivity.date.set(Calendar.HOUR, hourOfDay);
                        PostEventActivity.date.set(Calendar.MINUTE, minute);
                    }
                    if (getActivity() instanceof EditEventActivity){
                        EditEventActivity.date.set(Calendar.HOUR, hourOfDay);
                        EditEventActivity.date.set(Calendar.MINUTE, minute);
                    }
                }
            };


}
