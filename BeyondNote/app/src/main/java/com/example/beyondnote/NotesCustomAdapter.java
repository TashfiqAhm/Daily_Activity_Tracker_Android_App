package com.example.beyondnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class NotesCustomAdapter extends ArrayAdapter {
    public NotesCustomAdapter(Context context, ArrayList<Notes> note)
    {
        super(context, 0, note);
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        Notes note = (Notes) getItem(position);
        if(convertView ==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_sample_layout, parent, false);
        }
        TextView titleName = (TextView) convertView.findViewById(R.id.note_list_item_title_id);
        titleName.setSelected(note.isItemSelected());
        titleName.setText(note.getTitle());
        return convertView;
    }


}
