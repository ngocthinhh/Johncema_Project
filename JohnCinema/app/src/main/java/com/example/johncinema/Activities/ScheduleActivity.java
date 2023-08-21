package com.example.johncinema.Activities;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.HandleData;
import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.ScheduleClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    HandleData handleData;
    TextView textViewBack;
    TextView textViewTitlePoster;
    TextView textViewDirectorName;
//    Spinner spinnerCity;
    Spinner spinnerTheater;

//    String[] cities = {"TP-HCM", "Hà Nội"};
    String[] theaters = {"Rap 1", "Rap 2"};

//    ArrayAdapter<String> arrayAdapterCity;
    ArrayAdapter<String> arrayAdapterTheater;

    // Date And Week
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    TextView textViewDay1;
    TextView textViewDay2;
    TextView textViewDay3;
    TextView textViewDay4;
    TextView textViewDay5;
    TextView textViewDay6;
    TextView textViewDay7;
    TextView textViewWeek1;
    TextView textViewWeek2;
    TextView textViewWeek3;
    TextView textViewWeek4;
    TextView textViewWeek5;
    TextView textViewWeek6;
    TextView textViewWeek7;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layout6;
    LinearLayout layout7;
    String dateSelect;
    String theaterSelect;

    // GridView Hour
    GridView gridViewHour;
    ArrayList<String> arrayListHour;
    ArrayAdapter<String> arrayAdapterHour;
    String id_movie;

    // Test SQLite
    SQLHandle sqlHandle;
    ArrayList<ScheduleClass> listSchedule;
    ArrayList<ScheduleClass> listScheduleSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

//        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerTheater = findViewById(R.id.spinnerTheater);
        textViewBack = findViewById(R.id.textViewBack);
        textViewTitlePoster = findViewById(R.id.textViewTitlePoster);
        textViewDirectorName = findViewById(R.id.textViewDirectorName);

        textViewDay1 = findViewById(R.id.textViewDay1);
        textViewDay2 = findViewById(R.id.textViewDay2);
        textViewDay3 = findViewById(R.id.textViewDay3);
        textViewDay4 = findViewById(R.id.textViewDay4);
        textViewDay5 = findViewById(R.id.textViewDay5);
        textViewDay6 = findViewById(R.id.textViewDay6);
        textViewDay7 = findViewById(R.id.textViewDay7);

        textViewWeek1 = findViewById(R.id.textViewWeek1);
        textViewWeek2 = findViewById(R.id.textViewWeek2);
        textViewWeek3 = findViewById(R.id.textViewWeek3);
        textViewWeek4 = findViewById(R.id.textViewWeek4);
        textViewWeek5 = findViewById(R.id.textViewWeek5);
        textViewWeek6 = findViewById(R.id.textViewWeek6);
        textViewWeek7 = findViewById(R.id.textViewWeek7);

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        layout6 = findViewById(R.id.layout6);
        layout7 = findViewById(R.id.layout7);

        handleData = new HandleData(this);

        // GridView Hour
        sqlHandle = new SQLHandle(this);
        listSchedule = new ArrayList<>();
        listScheduleSelect = new ArrayList<>();
        gridViewHour = findViewById(R.id.gridViewHour);
        arrayListHour = new ArrayList<>();
        arrayAdapterHour = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListHour);
        gridViewHour.setAdapter(arrayAdapterHour);

//        setAdapterCity();
        setAdapterTheater();

        // Set Up List Date
        setDate(textViewDay1, textViewWeek1, 0);
        setDate(textViewDay2, textViewWeek2, 1);
        setDate(textViewDay3, textViewWeek3, 2);
        setDate(textViewDay4, textViewWeek4, 3);
        setDate(textViewDay5, textViewWeek5, 4);
        setDate(textViewDay6, textViewWeek6, 5);
        setDate(textViewDay7, textViewWeek7, 6);
        dateSelect = textViewDay1.getText().toString();
        setClickDate();

        loadData();
        close();
        setTitle();
        select();
    }

    void setTitle()
    {
        textViewTitlePoster.setText(handleData.getDataString("title"));
    }

    void close()
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
        gridViewHour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleData.saveData("id_schedule", listScheduleSelect.get(position).getId());
                startActivity(new Intent(ScheduleActivity.this, new ChairActivity().getClass()));
            }
        });
    }

    void loadData()
    {
        arrayListHour.clear();
        listSchedule.clear();
        listScheduleSelect.clear();
        listSchedule = sqlHandle.ScheduleRead();
        id_movie = handleData.getDataString("id_movie");

        ArrayList<MovieClass> listMovie = new ArrayList<>();
        MovieClass infoMovie = new MovieClass(null, null, null, null, null, null,null);
        listMovie = sqlHandle.MovieRead();
        for (int i = 0; i < listMovie.size(); i++)
        {
            if (id_movie.equals(listMovie.get(i).getId()))
            {
                infoMovie = listMovie.get(i);
            }
        }
        textViewDirectorName.setText(infoMovie.getDirector());


        Collections.sort(listSchedule, new Comparator<ScheduleClass>() {
            @Override
            public int compare(ScheduleClass o1, ScheduleClass o2) {
                String s1 = o1.getStart_hour().replace(":", "");
                String s2 = o2.getStart_hour().replace(":", "");

                Integer i1 = Integer.valueOf(s1);
                Integer i2 = Integer.valueOf(s2);

                if (i1.compareTo(i2) < 0)
                {
                    return -1;
                }
                else if (i1.compareTo(i2) > 0)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        });

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatHaveSec = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatDate.format(Calendar.getInstance().getTime());
        Date date;
        String dateString = "non";

        SimpleDateFormat formatHour = new SimpleDateFormat("HHmm");
        SimpleDateFormat formatHaveTwoDot = new SimpleDateFormat("HH:mm");
        String presentHour = formatHour.format((Calendar.getInstance().getTime()));
        Date hour;
        String hourString = "non";

        for (int i = 0; i < listSchedule.size(); i++)
        {
            try
            {
                date = formatHaveSec.parse(listSchedule.get(i).getDate());
                dateString = formatDate.format(date);

                hour = formatHaveTwoDot.parse(listSchedule.get(i).getStart_hour());
                hourString = formatHour.format(hour);
            }
            catch(Exception ex)
            {

            }

            if (listSchedule.get(i).getTheater().equals(theaterSelect)
                    && listSchedule.get(i).getDate().equals(dateSelect)
                    && listSchedule.get(i).getId_movie().equals(id_movie))
            {
                if (Integer.parseInt(dateString) == Integer.parseInt(today))
                {
                       if (Integer.parseInt(hourString) > Integer.parseInt(presentHour))
                       {
                           // list for show
                           arrayListHour.add(listSchedule.get(i).getStart_hour());

                           // list for select by position
                           listScheduleSelect.add(new ScheduleClass(listSchedule.get(i).getId(), listSchedule.get(i).getId_movie(),
                                   listSchedule.get(i).getStart_hour(), listSchedule.get(i).getTheater(), listSchedule.get(i).getDate()));
                       }
                }
                else
                {
                    // list for show
                    arrayListHour.add(listSchedule.get(i).getStart_hour());

                    // list for select by position
                    listScheduleSelect.add(new ScheduleClass(listSchedule.get(i).getId(), listSchedule.get(i).getId_movie(),
                            listSchedule.get(i).getStart_hour(), listSchedule.get(i).getTheater(), listSchedule.get(i).getDate()));
                }
            }
        }

        arrayAdapterHour.notifyDataSetChanged();
    }

    void setClickDate()
    {
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpColorDate(layout1, textViewDay1, textViewWeek1);
                dateSelect = textViewDay1.getText().toString();
                loadData();
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpColorDate(layout2, textViewDay2, textViewWeek2);
                dateSelect = textViewDay2.getText().toString();
                loadData();
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpColorDate(layout3, textViewDay3, textViewWeek3);
                dateSelect = textViewDay3.getText().toString();
                loadData();
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpColorDate(layout4, textViewDay4, textViewWeek4);
                dateSelect = textViewDay4.getText().toString();
                loadData();
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpColorDate(layout5, textViewDay5, textViewWeek5);
                dateSelect = textViewDay5.getText().toString();
                loadData();
            }
        });

        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpColorDate(layout6, textViewDay6, textViewWeek6);
                dateSelect = textViewDay6.getText().toString();
                loadData();
            }
        });

        layout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpColorDate(layout7, textViewDay7, textViewWeek7);
                dateSelect = textViewDay7.getText().toString();
                loadData();
            }
        });
    }

    void setUpColorDate(LinearLayout layoutSelected, TextView textViewDaySelected, TextView textViewWeekSelected)
    {
        layout1.setBackgroundColor(getColor(R.color.white));
        layout2.setBackgroundColor(getColor(R.color.white));
        layout3.setBackgroundColor(getColor(R.color.white));
        layout4.setBackgroundColor(getColor(R.color.white));
        layout5.setBackgroundColor(getColor(R.color.white));
        layout6.setBackgroundColor(getColor(R.color.white));
        layout7.setBackgroundColor(getColor(R.color.white));
        layoutSelected.setBackgroundColor(getColor(R.color.green));

        textViewDay1.setTextColor(getColor(R.color.green));
        textViewDay2.setTextColor(getColor(R.color.green));
        textViewDay3.setTextColor(getColor(R.color.green));
        textViewDay4.setTextColor(getColor(R.color.green));
        textViewDay5.setTextColor(getColor(R.color.green));
        textViewDay6.setTextColor(getColor(R.color.green));
        textViewDay7.setTextColor(getColor(R.color.green));
        textViewDaySelected.setTextColor(getColor(R.color.white));

        textViewWeek1.setTextColor(getColor(R.color.green));
        textViewWeek2.setTextColor(getColor(R.color.green));
        textViewWeek3.setTextColor(getColor(R.color.green));
        textViewWeek4.setTextColor(getColor(R.color.green));
        textViewWeek5.setTextColor(getColor(R.color.green));
        textViewWeek6.setTextColor(getColor(R.color.green));
        textViewWeek7.setTextColor(getColor(R.color.green));
        textViewWeekSelected.setTextColor(getColor(R.color.white));
    }

    void setDate(TextView textViewDate, TextView textViewWeek, int days)
    {
        String date;
        String week;
        calendar = Calendar.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar.add(Calendar.DAY_OF_YEAR, days);
        date = simpleDateFormat.format(calendar.getTime());
        simpleDateFormat = new SimpleDateFormat("EEE");
        week = simpleDateFormat.format(calendar.getTime());
        textViewDate.setText(date);
        textViewWeek.setText(week);
    }

//    void setAdapterCity()
//    {
//        arrayAdapterCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
//        spinnerCity.setAdapter(arrayAdapterCity);
//        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    void setAdapterTheater()
    {
        arrayAdapterTheater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, theaters);
        spinnerTheater.setAdapter(arrayAdapterTheater);
        spinnerTheater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                theaterSelect = arrayAdapterTheater.getItem(position);
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}