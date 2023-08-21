package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Adapters.AdapterClass;
import com.example.johncinema.Adapters.AdapterTicketClass;
import com.example.johncinema.HandleData;
import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.ScheduleClass;
import com.example.johncinema.Models.Ticket;
import com.example.johncinema.Models.TicketClass;
import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HistoryActivity extends AppCompatActivity {

    TextView textViewBack;

    //Grid View Ticket
    GridView gridViewTicket;
    ArrayList<TicketClass> arrayListTicket;
    AdapterTicketClass adapterTicketClass;
    HandleData handleData;
    ArrayList<Ticket> listTickeOfUser;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<Ticket> listTicket;
    ArrayList<ChairClass> listChair;
    ArrayList<ScheduleClass> listSchedule;
    ArrayList<UserClass> listUser;
    ArrayList<MovieClass> listMovie;
    String id_user = "non";
    UserClass info_user;
    ArrayList<ChairClass> list_info_chair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textViewBack = findViewById(R.id.textViewBack);
        handleData = new HandleData(this);

        // Grid View Ticket
        gridViewTicket = findViewById(R.id.gridViewTicket);
        arrayListTicket = new ArrayList<>();
        adapterTicketClass = new AdapterTicketClass(HistoryActivity.this, arrayListTicket);
        gridViewTicket.setAdapter(adapterTicketClass);

        // SQLite
        sqlHandle = new SQLHandle(this);
        listTicket = new ArrayList<>();
        listChair = new ArrayList<>();
        listSchedule = new ArrayList<>();
        listUser = new ArrayList<>();
        listMovie = new ArrayList<>();

        list_info_chair = new ArrayList<>();
        listTickeOfUser = new ArrayList<>();

        getDataForLists();
        back();
        select();

    }

    void getDataForLists()
    {
        // Get info User
        id_user = handleData.getDataInt("id_user").toString();
        listUser.clear();
        listUser = sqlHandle.UserRead();
        for (int i = 0; i < listUser.size(); i++)
        {
            if (listUser.get(i).getId().toString().equals(id_user))
            {
                info_user = listUser.get(i);
                break;
            }
        }

        // Get infos Ticket
        listTicket.clear();
        listTicket = sqlHandle.TicketRead();
        for (int i = 0; i < listTicket.size(); i++)
        {
            if (listTicket.get(i).getId_user_ticket().equals(id_user))
            {
                listTickeOfUser.add(listTicket.get(i));
            }
        }

        // Sap xep ve theo thoi diem mua ve
        Collections.sort(listTickeOfUser, Collections.reverseOrder(new Comparator<Ticket>() {
            @Override
            public int compare(Ticket o1, Ticket o2) {
                return o1.getTime_buy().compareTo(o2.getTime_buy());
            }
        }));

        if (listTickeOfUser.size() > 0)
        {
            for (int i = 0; i < listTickeOfUser.size(); i++)
            {
                arrayListTicket.add(new TicketClass(listTickeOfUser.get(i).getTitle(), listTickeOfUser.get(i).getIdImage(),
                        listTickeOfUser.get(i).getTheaterName(), listTickeOfUser.get(i).getTime(), listTickeOfUser.get(i).getDay()));
            }

            adapterTicketClass.notifyDataSetChanged();
        }
    }

    void back()
    {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    void select()
    {
        gridViewTicket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveData(position);
                startActivity(new Intent(HistoryActivity.this, new TicketActivity().getClass()));
            }
        });
    }

    void saveData(int position)
    {
        handleData.saveData("id_ticket", listTickeOfUser.get(position).getId_ticket());
    }
}