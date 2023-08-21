package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Adapters.AdapterChair;
import com.example.johncinema.HandleData;
import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ChairActivity extends AppCompatActivity {

    //
    String id_schedule;

    // Close
    TextView textViewBack;

    // Button
    Button buttonContinue;

    // GridView
    GridView gridViewChair;
    ArrayList<ChairClass> arrayListChair;
    AdapterChair adapterChair;
    Integer row = 0;
    Integer column = 0;
    ArrayList<Integer> arrayListRow;
    ArrayList<Integer> arrayListColumn;

    // SQLite
    SQLHandle sqlHandle;
    HandleData handleData;
    ArrayList<ChairClass> listChairInRoom;
    ArrayList<ChairClass> listChairAll;
    ArrayList<Integer> arrayListRowChosen;
    ArrayList<Integer> arrayListColumnChosen;
    ArrayList<ChairClass> listChairChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chair);

        // Close
        textViewBack = findViewById(R.id.textViewBack);

        // Button
        buttonContinue = findViewById(R.id.buttonContinue);

        // GridView
        gridViewChair = findViewById(R.id.gridViewChair);
        arrayListChair = new ArrayList<>();
        adapterChair = new AdapterChair(this, arrayListChair);
        gridViewChair.setAdapter(adapterChair);
        arrayListRow = new ArrayList<>();
        arrayListColumn = new ArrayList<>();

        // SQLite
        sqlHandle = new SQLHandle(this);
        handleData = new HandleData(this);
        listChairInRoom = new ArrayList<>();
        listChairAll = new ArrayList<>();
        arrayListRowChosen = new ArrayList<>();
        arrayListColumnChosen = new ArrayList<>();
        listChairChosen = new ArrayList<>();
        listChairChosen.clear();

        getIdSchedule();
        close();
        setButtonContinue();
        setUpGrid();
        select();
    }

    void getIdSchedule()
    {
        id_schedule = handleData.getDataString("id_schedule");
    }

    void select()
    {
        gridViewChair.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get Color from Item
                ColorDrawable colorDrawable = (ColorDrawable) view.findViewById(R.id.imageViewBackgroundColor).getBackground();
                Integer color = colorDrawable.getColor();

                // If Blue save Data to List else Yellow remove Data else Gray ignore
                if (color == getColor(R.color.blue))
                {
                    view.findViewById(R.id.imageViewBackgroundColor).setBackground(getDrawable(R.color.yellow));

                    listChairChosen.add(new ChairClass(
                            arrayListRow.get(position).toString() + "-" + arrayListColumn.get(position).toString(),
                            arrayListRow.get(position), arrayListColumn.get(position),
                            id_schedule, "sold"));
                }
                else if (color == getColor(R.color.yellow))
                {
                    view.findViewById(R.id.imageViewBackgroundColor).setBackground(getDrawable(R.color.blue));

                    listChairChosen.removeIf(n -> (n.getId_chair().equals(
                            arrayListRow.get(position).toString() + "-" + arrayListColumn.get(position).toString())));
                }
                else if (color == getColor(R.color.gray))
                {

                }
            }
        });
    }

    void setUpGrid()
    {
        arrayListRow.clear();
        arrayListColumn.clear();

        Integer indexListChairInRoom = 0;

        listChairInRoom.clear();

        listChairAll.clear();
        listChairAll = sqlHandle.ChairRead();

        for (int i = 0; i < listChairAll.size(); i++)
        {
            if (listChairAll.get(i).getId_schedule().equals(id_schedule))
            {
                listChairInRoom.add(listChairAll.get(i));
            }
        }

        Collections.sort(listChairInRoom, new Comparator<ChairClass>() {
            @Override
            public int compare(ChairClass o1, ChairClass o2) {
                return o1.getRow().compareTo(o2.getRow());
            }
        });

        row = 10;
        column = 10;
        gridViewChair.setNumColumns(column);

        for (Integer i = 1; i <= row; i++)
        {
            for (Integer j = 1; j <= column; j++)
            {
                arrayListRow.add(i);
                arrayListColumn.add(j);

                if (listChairInRoom.isEmpty())
                {
                    arrayListChair.add(new ChairClass(i.toString() + j.toString(), i, j, id_schedule, "no_sit"));
                }
                else
                {
                    if (i.equals(listChairInRoom.get(indexListChairInRoom).getRow())
                        && j.equals(listChairInRoom.get(indexListChairInRoom).getColumn()))
                    {
                        arrayListChair.add(new ChairClass(i.toString() + j.toString(), i, j, id_schedule, "sold"));

                        if (indexListChairInRoom < listChairInRoom.size() - 1)
                        {
                            indexListChairInRoom++;
                        }
                    }
                    else
                    {
                        arrayListChair.add(new ChairClass(i.toString() + j.toString(), i, j, id_schedule, "no_sit"));
                    }
                }
            }
        }

        adapterChair.notifyDataSetChanged();
    }

    void setButtonContinue()
    {
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateString = dateFormat.format(Calendar.getInstance().getTime());
                handleData.saveData("time_buy", dateString);

                String id_user = handleData.getDataInt("id_user").toString();

                String msg = "Vui lòng chọn ghế";
                for (int i = 0; i < listChairChosen.size(); i++)
                {
                    msg = sqlHandle.ChairInsert(listChairChosen.get(i).getId_chair() + "-" + id_user + "-" + dateString,
                            listChairChosen.get(i).getRow(), listChairChosen.get(i).getColumn(),
                            listChairChosen.get(i).getId_schedule(), listChairChosen.get(i).getState());
                }


                if (msg.equals("Vui lòng chọn ghế"))
                {
                    Toast.makeText(ChairActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    startActivity(new Intent(ChairActivity.this, new PaymentActivity().getClass()));
                }

            }
        });
    }

    void close()
    {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                String id_user = handleData.getDataInt("id_user").toString();
                String time_buy = handleData.getDataString("time_buy");

                if (!time_buy.isEmpty())
                {
                    for (int i = 0; i < listChairChosen.size(); i++)
                    {
                        msg = sqlHandle.ChairDelete(listChairChosen.get(i).getId_chair() + "-" + id_user + "-" + time_buy);
                    }
                }

                finish();
            }
        });
    }
}