package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.ScheduleClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class HandleChairActivity extends AppCompatActivity {

    TextView textViewBack;

    // Test SQLite
    SQLHandle sqlHandle;
    ArrayList<ChairClass> listChair;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_chair);

        textViewBack = findViewById(R.id.textViewBack);
        listView = findViewById(R.id.listViewInfo);

        sqlHandle = new SQLHandle(this);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        listChair = new ArrayList<>();

        listView.setAdapter(myadapter);

        close();
        loadData();
    }

    void loadData()
    {
        listChair.clear();
        mylist.clear();
        listChair = sqlHandle.ChairRead();

        for (int i = 0; i < listChair.size(); i++)
        {
            mylist.add(listChair.get(i).getId_chair() + " "
                    + listChair.get(i).getRow() + " "
                    + listChair.get(i).getColumn() + " "
                    + listChair.get(i).getId_schedule() + " "
                    + listChair.get(i).getState());

        }

        myadapter.notifyDataSetChanged();
    }

    void close()
    {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HandleChairActivity.this, new ChairActivity().getClass()));
            }
        });
    }
}