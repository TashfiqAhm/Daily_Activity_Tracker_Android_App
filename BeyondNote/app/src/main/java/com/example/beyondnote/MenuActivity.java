package com.example.beyondnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends AppCompatActivity implements  View.OnClickListener{

    private Toolbar toolbar;
    private TextView date_text;
    private TextView time_text;
    private ImageButton notes_button;
    private ImageButton checklist_button;
    private ImageButton reminder_button;
    private ImageButton money_button;
    private Timer timer;
    private int day,month,year,min,hour;
    private String timeZone;
    private String mon[] = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "Jule",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    Calendar cal = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar =findViewById(R.id.menu_toolbar_id);
        setSupportActionBar(toolbar);

        date_text = findViewById(R.id.date_text_id);
        time_text = findViewById(R.id.time_text_id);

        notes_button = findViewById(R.id.note_button_id);
        checklist_button = findViewById(R.id.checklist_button_id);
        reminder_button = findViewById(R.id.reminder_button_id);
        money_button = findViewById(R.id.money_button_id);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update_date_and_time();
            }
        },0,60000);

        notes_button.setOnClickListener(this);
        checklist_button.setOnClickListener(this);
        reminder_button.setOnClickListener(this);
        money_button.setOnClickListener(this);

    }

    void update_date_and_time()
    {
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        if(hour>=12)
            timeZone=" P.M.";
        else timeZone = " A.M.";
        if(hour==0)
            hour=12;
        else if(hour>12)
            hour=hour-12;

        date_text.setText("Date  :  "+ day +" "+ mon[month] +", " + year);
        time_text.setText("Time :  "+hour +" : " + min + timeZone);
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.note_button_id)
        {
            Intent intent = new Intent(MenuActivity.this, NotesActivity.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.checklist_button_id)
        {
            Intent intent = new Intent(MenuActivity.this, ChecklistActivity.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.reminder_button_id)
        {
            Intent intent = new Intent(MenuActivity.this, ReminderActivity.class);
            startActivity(intent);
        }

        else
        {
            Intent intent = new Intent(MenuActivity.this, MoneyActivity.class);
            startActivity(intent);
        }
    }
}
