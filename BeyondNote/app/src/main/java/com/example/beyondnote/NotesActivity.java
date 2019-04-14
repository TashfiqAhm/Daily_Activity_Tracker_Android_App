package com.example.beyondnote;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class NotesActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ListView listview;
    private Toolbar toolbar;
    private FloatingActionButton fButton;
    private TextView tvDate;
    private ArrayList<NotesModel> note = new ArrayList<NotesModel>();
    private NotesCustomAdapter adapter;
    private ArrayList<NotesModel> delete_item_list = new ArrayList<NotesModel>();
    private int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        toolbar =findViewById(R.id.note_list_toolbar_id);
        setSupportActionBar(toolbar);

        listview = findViewById(R.id.note_listView_id);
        fButton = findViewById(R.id.note_fab_button_id);
        tvDate = findViewById(R.id.note_list_date_text_id);

        Calendar cal = Calendar.getInstance();
        tvDate.setText(cal.get(Calendar.DAY_OF_MONTH) + "/" +  (cal.get(Calendar.MONTH) +1)+ "/"+ cal.get(Calendar.YEAR));




        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        fButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                add_new_note();
            }
        });
        listview_all_listener();

    }

    void change_date(View v)
    {
        DialogFragment date_picker_fragment = new DatePickerFragment();
        date_picker_fragment.show(getSupportFragmentManager(), "date picker");
    }



    @Override
    protected void onStart() {
        super.onStart();
        load_data();
        adapter = new NotesCustomAdapter(NotesActivity.this,note);
        listview.setAdapter(adapter);
    }



    @Override
    protected void onPause() {
        super.onPause();
        store_on_file();
    }

    private void add_new_note()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NotesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.new_note_add_dialog,null);
        final EditText mEditText = (EditText) mView.findViewById(R.id.new_note_promt_editText_id);
        final Button mButton = (Button) mView.findViewById(R.id.new_note_promt_button_id);

        mBuilder.setView(mView);
        final AlertDialog mDialog = mBuilder.create();
        mDialog.show();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditText.getText().toString().trim().isEmpty())
                {
                    NotesModel _note = new NotesModel(mEditText.getText().toString().trim(),"");
                    note.add(_note);
                    adapter.notifyDataSetChanged();

                    Intent intent = new Intent(NotesActivity.this, DescriptionNotes.class);
                    intent.putExtra("list",note);
                    intent.putExtra("position", note.size()-1);
                    startActivity(intent);
                    mDialog.dismiss();
                }
                else
                {
                    Toast.makeText(NotesActivity.this,"Please enter a title to your note",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void store_on_file()
    {
        JSONArray json_array = new JSONArray();
        for(int i=0;i<note.size();i++)
            json_array.put(note.get(i).getJsonObject());

        String temp_st = json_array.toString();

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("tempnote.json", Context.MODE_PRIVATE);
            fos.write(temp_st.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void load_data()
    {
        String line;
        StringBuffer sb = new StringBuffer();
        note.clear();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getFilesDir() + "/tempnote.json")));
            while( (line= br.readLine()) != null )
            {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONArray jarray =null ;
        try {
                jarray = new JSONArray(sb.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jarray!=null)
        {
            for(int i=0;i<jarray.length();i++)
            {
                try {
                    JSONObject jobject = jarray.getJSONObject(i);
                    NotesModel temp_note = new NotesModel(jobject.get("title").toString(),jobject.get("description").toString());
                    note.add(temp_note);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void listview_all_listener()
    {
        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if(checked)
                {
                    count++;
                    mode.setTitle(count + " items selected");
                    delete_item_list.add(note.get(position));
                    NotesModel temp_note = note.get(position);
                    temp_note.setItemSelected(true);
                    note.set(position,temp_note);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    count--;
                    mode.setTitle(count + " items selected");
                    delete_item_list.remove(note.get(position));
                    NotesModel nt = note.get(position);
                    nt.setItemSelected(false);
                    note.set(position,nt);
                    adapter.notifyDataSetChanged();
                }

            }


            @SuppressLint("RestrictedApi")
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.listview_item_delete_menu,menu);

                fButton.setVisibility(View.GONE);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch(item.getItemId())
                {
                    case  R.id.delete_icon_id:

                        for(NotesModel temp_note : delete_item_list)
                        {
                            note.remove(temp_note);
                            adapter.notifyDataSetChanged();
                        }
                        delete_item_list.clear();
                        Toast.makeText(NotesActivity.this,count + " items deleted",Toast.LENGTH_SHORT).show();
                        count=0;
                        mode.finish();

                        return true;

                    default:
                        return false;
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for(NotesModel temp_note: delete_item_list)
                {
                    int position = adapter.getPosition(temp_note);
                    temp_note.setItemSelected(false);
                    note.set(position,temp_note);
                    adapter.notifyDataSetChanged();
                }
                count=0;
                delete_item_list.clear();
                fButton.setVisibility(View.VISIBLE);

            }
        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotesActivity.this, DescriptionNotes.class);
                intent.putExtra("list",note);
                intent.putExtra("position", position);
                startActivity(intent);


            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        tvDate.setText(dayOfMonth + "/" + (month+1) + "/"+ year);

    }
}
