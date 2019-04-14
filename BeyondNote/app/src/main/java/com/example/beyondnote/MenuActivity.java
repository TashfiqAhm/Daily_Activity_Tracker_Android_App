package com.example.beyondnote;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MenuActivity extends AppCompatActivity implements  View.OnClickListener {

    private Toolbar toolbar;
    private Button notes_button;
    private Button checklist_button;
    private Button reminder_button;
    private Button money_button;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar =findViewById(R.id.menu_toolbar_id);
        setSupportActionBar(toolbar);

        notes_button = findViewById(R.id.menu_note_button_id);
        checklist_button = findViewById(R.id.menu_checklist_button_id);
        reminder_button = findViewById(R.id.menu_reminder_button_id);
        money_button = findViewById(R.id.menu_money_button_id);
        tvDate = findViewById(R.id.menu_date_text_id);

        Calendar cal = Calendar.getInstance();
        tvDate.setText(cal.get(Calendar.DAY_OF_MONTH) + "/" +  (cal.get(Calendar.MONTH) +1)+ "/"+ cal.get(Calendar.YEAR));

        notes_button.setOnClickListener(this);
        checklist_button.setOnClickListener(this);
        reminder_button.setOnClickListener(this);
        money_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.menu_note_button_id)
        {
            Intent intent = new Intent(MenuActivity.this, NotesActivity.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.menu_checklist_button_id)
        {
            Intent intent = new Intent(MenuActivity.this, ChecklistActivity.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.menu_reminder_button_id)
        {
            Intent intent = new Intent(MenuActivity.this, ReminderActivity.class);
            startActivity(intent);
        }

        else if(v.getId()==R.id.menu_money_button_id)
        {
            Intent intent = new Intent(MenuActivity.this, MoneyActivity.class);
            startActivity(intent);
        }

    }

}
