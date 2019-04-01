package com.example.beyondnote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.List;


public class ChecklistActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private ArrayList<Check_Boxes>boxes = new ArrayList<Check_Boxes>();
    private ArrayList<ItemToDelete>delete_item_list = new ArrayList<ItemToDelete>();
    private FloatingActionButton fButton;
    private CheckListCustomAdapter adapter;
    private int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        toolbar = findViewById(R.id.check_list_toolbar_id);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.check_list_listView_id);
        fButton = findViewById(R.id.checl_list_fab_button_id);

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_check_box();

            }
        });
        load_data();


        adapter = new CheckListCustomAdapter(ChecklistActivity.this,boxes);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listview_all_listener();


    }

    @Override
    protected void onPause() {
        super.onPause();
        store_data();
    }

    class  ItemToDelete
    {
        Check_Boxes item;
        int position;
        public ItemToDelete(Check_Boxes item,int position)
        {
            this.item = item;
            this.position = position;
        }
    }


    private void load_data()
    {
        /*boxes.add(new Check_Boxes(false,"tasin"));
        boxes.add(new Check_Boxes(false,"tasin2"));
        boxes.add(new Check_Boxes(false,"tasin3"));
        boxes.add(new Check_Boxes(false,"tasin4"));
        boxes.add(new Check_Boxes(false,"tasin5"));
        boxes.add(new Check_Boxes(false,"tasin6"));
        boxes.add(new Check_Boxes(false,"tasin7"));*/


        String line;
        StringBuffer sb = new StringBuffer();
        boxes.clear();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getFilesDir() + "/tempcheckbox.json")));
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
                    Check_Boxes temp_box = new Check_Boxes(jobject.getBoolean("isSelected"),jobject.get("box_name").toString());
                    boxes.add(temp_box);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void store_data()
    {
        JSONArray json_array = new JSONArray();
        for(int i=0;i<boxes.size();i++)
            json_array.put(boxes.get(i).getJsonObject());

        String temp_st = json_array.toString();
        //Log.d("ami dekhi", temp_st);

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("tempcheckbox.json", Context.MODE_PRIVATE);
            fos.write(temp_st.getBytes());
            fos.close();
            //Toast.makeText(ChecklistActivity.this, "Saved from list", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void add_new_check_box()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChecklistActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.new_check_box_add_dialog,null);
        final EditText mEditText = (EditText) mView.findViewById(R.id.new_check_box_promot_edit_text);
        final Button mButton = (Button) mView.findViewById(R.id.new_check_box_promt_button_id);

        mBuilder.setView(mView);
        final AlertDialog mDialog = mBuilder.create();
        mDialog.show();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEditText.getText().toString().trim().isEmpty())
                {
                    Check_Boxes _box = new Check_Boxes(false,mEditText.getText().toString().trim());
                    boxes.add(_box);
                    adapter.notifyDataSetChanged();
                    mDialog.dismiss();
                    store_data();
                }
                else
                {
                    Toast.makeText(ChecklistActivity.this,"Please enter a name for your item",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void listview_all_listener()
    {
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                if(checked)
                {
                    count++;
                    mode.setTitle(count + " items selected");
                    //delete_item_list.add(boxes.get(position));
                    delete_item_list.add(new ItemToDelete(boxes.get(position),position));
                    //Log.d("onItem", "onItemCheckedStateChanged: " + position);
                    Check_Boxes nt = boxes.get(position);
                    nt.setItemSelected(true);
                    boxes.set(position,nt);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    count--;
                    mode.setTitle(count + " items selected");
                    delete_item_list.remove(new ItemToDelete(boxes.get(position),position));
                    Check_Boxes nt = boxes.get(position);
                    nt.setItemSelected(false);
                    boxes.set(position,nt);
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

                        for(ItemToDelete i : delete_item_list)
                        {
                            boxes.remove(i.item);
                            adapter.notifyDataSetChanged();
                        }
                        delete_item_list.clear();
                        Toast.makeText(ChecklistActivity.this,count + " items deleted",Toast.LENGTH_SHORT).show();
                        count=0;
                        mode.finish();
                        store_data();

                        return true;

                    default:
                        return false;
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for(ItemToDelete nm: delete_item_list)
                {
                    nm.item.setItemSelected(false);
                    boxes.set(nm.position,nm.item);
                    adapter.notifyDataSetChanged();
                }
                count=0;
                delete_item_list.clear();
                fButton.setVisibility(View.VISIBLE);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Check_Boxes box = boxes.get(position);

                if(box.isSealected())
                    box.setSealected(false);
                else box.setSealected(true);
                boxes.set(position,box);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
