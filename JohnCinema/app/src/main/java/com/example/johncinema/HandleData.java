package com.example.johncinema;

import android.content.ContentProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class HandleData extends AppCompatActivity {

    SharedPreferences myPref;
    SharedPreferences.Editor myEdit;

    public HandleData(Context context)
    {
        myPref = context.getSharedPreferences("data",  MODE_PRIVATE);
        myEdit = myPref.edit();
    }

    public void saveData(String key, Integer data)
    {
        myEdit.putInt(key, data);
        myEdit.commit();
    }

    public void saveData(String key, String data)
    {
        myEdit.putString(key, data);
        myEdit.commit();
    }

    public Integer getDataInt(String key)
    {
        return myPref.getInt(key, 0);
    }

    public String getDataString(String key)
    {
        return myPref.getString(key, "");
    }
}
