package com.example.beyondnote;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckListCustomAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Check_Boxes> boxes;
    LayoutInflater inflater;


    public CheckListCustomAdapter(Activity activity)
    {
        this.activity = activity;
    }

    public CheckListCustomAdapter(Activity activity, ArrayList<Check_Boxes>boxes )
    {
        this.activity = activity;
        this.boxes = boxes;
        inflater = activity.getLayoutInflater();
    }

    class ViewHolder{
        TextView tvUserName;
        ImageView ivCheckBox;
    }

    @Override
    public int getCount() {
        return boxes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.check_box_sample_layout,parent,false);
            holder = new ViewHolder();
            holder.tvUserName = (TextView)convertView.findViewById(R.id.check_box_item_text_id);
            holder.ivCheckBox = (ImageView)convertView.findViewById(R.id.check_box_item_image_id);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        Check_Boxes box = boxes.get(position);

        holder.tvUserName.setText(box.getBox_name());
        holder.tvUserName.setSelected(box.isItemSelected());

        if(box.isSealected())
        {
            holder.ivCheckBox.setBackgroundResource(R.drawable.ticked_box);
        }
        else
        {
            holder.ivCheckBox.setBackgroundResource(R.drawable.blank_box);
        }

        return convertView;

    }





}
