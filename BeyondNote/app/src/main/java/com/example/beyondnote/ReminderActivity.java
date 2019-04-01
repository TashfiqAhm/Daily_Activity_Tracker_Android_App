package com.example.beyondnote;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Button time_picker,date_picker;
    private int time_hour,time_minute,time_month,time_year,time_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        time_picker = findViewById(R.id.set_time_button);
        date_picker = findViewById(R.id.set_date_button);

        time_picker.setOnClickListener(this);
        date_picker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.set_time_button)
        {
            DialogFragment time_picker_fragment = new TimePickerFragment();
            time_picker_fragment.show(getSupportFragmentManager(),"time picker");
        }

        else if (v.getId()==R.id.set_date_button)
        {
            DialogFragment date_picker_fragment = new DatePickerFragment();
            date_picker_fragment.show(getSupportFragmentManager(),"date picker");
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time_hour = hourOfDay;
        time_minute = minute;
        String time_info,time_status;
        if(hourOfDay<12)
            time_status = " A.M";
        else {
            time_status = " P.M";
            if(hourOfDay>12)
                hourOfDay-=12;
        }
        if(hourOfDay<9)
            time_info = "0" +hourOfDay + " : ";
        else time_info = "" + hourOfDay + " : ";

        if(minute<9)
            time_info += "0" + minute + time_status;
        else time_info += minute + time_status;
        time_picker.setText(time_info);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        time_year = year;
        time_month = month;
        time_day = dayOfMonth;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,month);
        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String date_info = DateFormat.getDateInstance().format(cal.getTime());
        date_picker.setText(date_info);

    }
}




