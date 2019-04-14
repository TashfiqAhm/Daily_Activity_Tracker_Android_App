package com.example.beyondnote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class NotesModel implements Serializable {
    private String title;
    private String description;
    private boolean item_selected;

    public NotesModel(String title, String description)
    {
        this.title = title;
        this.description = description;
        item_selected = false;
    }



    public void setTitle(String title)
    {
        this.title = title;
    }

    String getTitle()
    {
        return title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
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
            obj.put("title",title);
            obj.put("description",description);
        }
        catch (Exception e)
        {

        }
        return obj;

    }

}
