package com.example.beyondnote;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DescriptionNotes extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText descrption_text;
    private EditText title_text;
    private ArrayList<NotesModel> note = new ArrayList<NotesModel>();
    private int position;
    private JSONArray json_array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_notes);


        toolbar =findViewById(R.id.note_description_toolbar_id);
        setSupportActionBar(toolbar);



        descrption_text = findViewById(R.id.description_notes_body_id);
        title_text = findViewById(R.id.description_notes_title_id);


        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            note = (ArrayList<NotesModel>) getIntent().getSerializableExtra("list");
            position = (int) getIntent().getIntExtra("position",0);

            title_text.setText(note.get(position).getTitle()) ;
            descrption_text.setText(note.get(position).getDescription());
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        store_on_file();
    }



    private void store_on_file()
    {
        if( !(note.get(position).getTitle().equals(title_text.getText().toString()) && note.get(position).getDescription().equals(descrption_text.getText().toString())) )
        {
            json_array = new JSONArray();
            note.get(position).setTitle(title_text.getText().toString());
            note.get(position).setDescription(descrption_text.getText().toString());
            for(int i=0;i<note.size();i++)
                json_array.put(note.get(i).getJsonObject());

            String temp_string = json_array.toString();


            try {
                FileOutputStream fos = getApplicationContext().openFileOutput("tempnote.json", Context.MODE_PRIVATE);
                fos.write(temp_string.getBytes());
                fos.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }
}
