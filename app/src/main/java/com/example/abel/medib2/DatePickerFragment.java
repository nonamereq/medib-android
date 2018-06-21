package com.example.abel.medib2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c= Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int date=c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getContext(),dateSetListener,year,month,date);
    }
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                            " / " + (view.getMonth()+1) +
                            " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                    Button pick = (Button) getActivity().findViewById(R.id.pickDate);
                    pick.setText(view.getYear() + " / " + (view.getMonth()+1) + " / " + view.getDayOfMonth());
                    if (getActivity() instanceof PostEventActivity){
                        PostEventActivity.day=view.getDayOfMonth();
                        PostEventActivity.month=view.getMonth();
                        PostEventActivity.year=view.getYear();
                        Log.d("AAAA", String.valueOf(PostEventActivity.hour));
                    }
                    if (getActivity() instanceof EditEventActivity){
                        EditEventActivity.day=view.getDayOfMonth();
                        EditEventActivity.month=view.getMonth();
                        EditEventActivity.year=view.getYear();
                        Log.d("AAAA", String.valueOf(EditEventActivity.hour));
                    }

                }
            };
}
