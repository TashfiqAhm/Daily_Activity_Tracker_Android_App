package com.example.beyondnote;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar cal = Calendar.getInstance();

        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(),
                cal.get(Calendar.YEAR) ,cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
    }
}
