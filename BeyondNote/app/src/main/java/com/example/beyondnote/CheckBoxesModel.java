package com.example.beyondnote;

import org.json.JSONObject;

public class CheckBoxesModel {


    private boolean isSealected;
    private String box_name;
    private boolean item_selected;

    public CheckBoxesModel(boolean isSealected, String box_name)
    {
        this.isSealected = isSealected;
        this.box_name = box_name;
    }

    public boolean isSealected()
    {
        return isSealected;
    }

    public void setSealected(boolean selected)
    {
        isSealected = selected;
    }

    public String getBox_name()
    {
        return box_name;
    }
    public void setBox_name(String box_name)
    {
        this.box_name = box_name;
    }

    public void setItemSelected(boolean item_selected)
    {
        this.item_selected = item_selected;
    }
    public boolean isItemSelected()
    {
        return item_selected;
    }


    public JSONObject getJsonObject()
    {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("box_name",box_name);
            obj.put("isSelected",isSealected);
        }
        catch (Exception e)
        {

        }
        return obj;

    }
}
