package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.ScheduleClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HandleScheduleActivity extends AppCompatActivity {

    TextView textViewBack;
    Spinner spinnerTheater;
    Spinner spinnerDate;
    EditText editTextMovieID;
    EditText editTextScheduleID;
    EditText editTextStartHour;

    // Handle Spinner
    ArrayList<String> arrayListTheater;
    ArrayList<String> arrayListDate;
    ArrayAdapter<String> arrayAdapterTheater;
    ArrayAdapter<String> arrayAdapterDate;
    String day;
    String month;
    String year;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    // Button
    Button buttonAdd;
    Button buttonUpdate;
    Button buttonDelete;

    // Infomation
    ListView listView;

    // Test SQLite
    SQLHandle sqlHandle;
    ArrayList<ScheduleClass> listSchedule;
    ArrayList<ScheduleClass> listScheduleSelect;
    ArrayList<MovieClass> listMovie;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    String theater_schedule;
    String date_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_schedule);

        spinnerTheater = findViewById(R.id.spinnerTheater);
        spinnerDate = findViewById(R.id.spinnerDate);
        editTextMovieID = findViewById(R.id.editTextMovieID);
        editTextScheduleID = findViewById(R.id.editTextScheduleID);
        editTextStartHour = findViewById(R.id.editTextStartHour);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listView = findViewById(R.id.listViewInfo);
        textViewBack = findViewById(R.id.textViewBack);

        arrayListTheater = new ArrayList<>();
        arrayAdapterTheater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListTheater);
        spinnerTheater.setAdapter(arrayAdapterTheater);
        arrayListDate = new ArrayList<>();
        arrayAdapterDate = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListDate);
        spinnerDate.setAdapter(arrayAdapterDate);

        // SQLite
        sqlHandle = new SQLHandle(this);
        listSchedule = new ArrayList<>();
        listScheduleSelect  = new ArrayList<>();
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        listView.setAdapter(myadapter);
        listMovie = new ArrayList<>();

        setUpListTheater();
        setUpListDate();
        handleListTheater();
        handleListDate();
        select();

        handleButton();

        loadData();
        close();
    }

    void refeshEditText()
    {
        editTextMovieID.setText("");
        editTextScheduleID.setText("");
        editTextStartHour.setText("");
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

    String checkEmpty()
    {
        if (editTextScheduleID.getText().toString().isEmpty()
        || editTextMovieID.getText().toString().isEmpty()
        || editTextStartHour.getText().toString().isEmpty())
        {
            return "Vui lòng không để trống";
        }

        return "";
    }

    boolean checkInListMovie()
    {
        // Check movie exist (CHECK part1)
        boolean checkPart1 = false;
        listMovie.clear();
        listMovie = sqlHandle.MovieRead();
        MovieClass infoMovie = new MovieClass(null, null, null, null, null, null, null);

        for (int i = 0; i < listMovie.size(); i++)
        {
            if (editTextMovieID.getText().toString().equals(listMovie.get(i).getId()))
            {
                if (listMovie.get(i).getDebut().equals("Present"))
                {
                    checkPart1 = true;
                    infoMovie = listMovie.get(i);
                    break;
//                    return true;
                }
            }
        }

        if (checkPart1)
        {
            // Check schedule of movie exist (CHECK part2)
            listSchedule.clear();
            listSchedule = sqlHandle.ScheduleRead();

            if (listSchedule.size() > 0)
            {
                // Add to new list with id_movie
                ArrayList<ScheduleClass> listScheduleOfTheaterAndDate = new ArrayList<>();
                for (int j = 0; j < listSchedule.size(); j++)
                {
                    if (theater_schedule.equals(listSchedule.get(j).getTheater())
                            && date_schedule.equals(listSchedule.get(j).getDate()))
                    {
                        listScheduleOfTheaterAndDate.add(listSchedule.get(j));
                    }
                }

                if (listScheduleOfTheaterAndDate.size() > 0)
                {
                    // Sort by start_hour (Increase)
                    Collections.sort(listScheduleOfTheaterAndDate, new Comparator<ScheduleClass>() {
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

                    //
                    Integer indexOfListSchedule = -1;

                    String editStartHourString = editTextStartHour.getText().toString();
                    editStartHourString = editStartHourString.replace(":", "");
                    Integer editStarHourInteger = Integer.parseInt(editStartHourString);

                    for (int i = 0; i < listScheduleOfTheaterAndDate.size(); i++)
                    {
                        String startHourOfMovie = listScheduleOfTheaterAndDate.get(i).getStart_hour();
                        startHourOfMovie = startHourOfMovie.replace(":", "");
                        Integer startHourOfMovieInteger = Integer.parseInt(startHourOfMovie);

                        if (editStarHourInteger < startHourOfMovieInteger)
                        {

                            indexOfListSchedule = i;
                            break;
                        }
                    }

                    if (indexOfListSchedule == -1)
                    {
                        indexOfListSchedule = listScheduleOfTheaterAndDate.size();
                    }

                    Integer durationPresentMovie = Integer.parseInt(infoMovie.getDuration());
                    Integer minutePresentMovie = durationPresentMovie % 60;
                    Integer hourPresentMovie = durationPresentMovie / 60;

                    Integer afterPreviousAddDuration = -1;
                    if (indexOfListSchedule != 0)
                    {
                        String idOfMoviePrevious = listScheduleOfTheaterAndDate.get(indexOfListSchedule - 1).getId_movie();
                        MovieClass infoMoviePrevious = new MovieClass(null, null, null, null, null, null, null);
                        for (int i = 0; i < listMovie.size(); i++)
                        {
                            if (listMovie.get(i).getId().equals(idOfMoviePrevious))
                            {
                                infoMoviePrevious = listMovie.get(i);
                                break;
                            }
                        }
                        Integer durationPreviousMovie = Integer.parseInt(infoMoviePrevious.getDuration());
                        Integer minutePreviousMovie = durationPreviousMovie % 60;
                        Integer hourPreviousMovie = durationPreviousMovie / 60;

                        String startHourOfMoviePrevious = listScheduleOfTheaterAndDate.get(indexOfListSchedule - 1).getStart_hour();
                        startHourOfMoviePrevious = startHourOfMoviePrevious.replace(":", "");
                        Integer startHourOfMoviePreviousInteger = Integer.parseInt(startHourOfMoviePrevious);
                        afterPreviousAddDuration = startHourOfMoviePreviousInteger + (hourPreviousMovie * 100) + minutePreviousMovie;
                    }


                    Integer startHourOfMovieNextInteger = -1;
                    if(indexOfListSchedule != listScheduleOfTheaterAndDate.size())
                    {
                        String startHourOfMovieNext = listScheduleOfTheaterAndDate.get(indexOfListSchedule).getStart_hour();
                        startHourOfMovieNext = startHourOfMovieNext.replace(":", "");
                        startHourOfMovieNextInteger = Integer.parseInt(startHourOfMovieNext);
                    }

                    Integer afterEditAddDuration = editStarHourInteger + (hourPresentMovie * 100) + minutePresentMovie;

                    if (afterPreviousAddDuration != -1 && startHourOfMovieNextInteger != -1)
                    {
                        if (afterPreviousAddDuration < editStarHourInteger
                                && afterEditAddDuration < startHourOfMovieNextInteger)
                        {
//                            Toast.makeText(HandleScheduleActivity.this, "Add mid success", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                    else if (afterPreviousAddDuration != -1)
                    {
                        if (afterPreviousAddDuration < editStarHourInteger)
                        {
//                            Toast.makeText(HandleScheduleActivity.this, "Add last success", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                    else if (startHourOfMovieNextInteger != -1)
                    {
                        if (afterEditAddDuration < startHourOfMovieNextInteger)
                        {
//                            Toast.makeText(HandleScheduleActivity.this, "Add first success", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return true;
            }

        }

        return false;
    }

    void handleButton()
    {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";

                msg = checkEmpty();
                if (msg.equals(""))
                {
                    if (checkInListMovie())
                    {
                        msg = sqlHandle.ScheduleInsert(editTextScheduleID.getText().toString(),
                                editTextMovieID.getText().toString(),
                                editTextStartHour.getText().toString(),
                                theater_schedule, date_schedule);
                    }
                    else
                    {
                        msg = "Thêm không thành công";
                    }
                }

                Toast.makeText(HandleScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                refeshEditText();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Update Fail";

                if (checkInListMovie())
                {
                    msg = sqlHandle.ScheduleUpdate(editTextScheduleID.getText().toString(),
                            editTextMovieID.getText().toString(),
                            editTextStartHour.getText().toString(),
                            theater_schedule, date_schedule);
                }
                Toast.makeText(HandleScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                refeshEditText();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sqlHandle.ScheduleDelete(editTextScheduleID.getText().toString());
                Toast.makeText(HandleScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                refeshEditText();
            }
        });
    }

    void select()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTextScheduleID.setText(listScheduleSelect.get(position).getId());
                editTextMovieID.setText(listScheduleSelect.get(position).getId_movie());
                editTextStartHour.setText(listScheduleSelect.get(position).getStart_hour());
            }
        });
    }

    void loadData()
    {
        listSchedule.clear();
        listScheduleSelect.clear();
        mylist.clear();
        listSchedule = sqlHandle.ScheduleRead();

        // Get today
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
                hour = formatHaveTwoDot.parse(listSchedule.get(i).getStart_hour());
                hourString = formatHour.format(hour);

                date = formatHaveSec.parse(listSchedule.get(i).getDate());
                dateString = formatDate.format(date);
            }
            catch (Exception ex)
            {

            }

            // Delete Schedule yesterday
            if (Integer.parseInt(dateString) < Integer.parseInt(today))
            {
                // Delete Chair with Schedule yesterday
                ArrayList<ChairClass> listChair = new ArrayList<>();
                listChair = sqlHandle.ChairRead();
                for (int j = 0; j < listChair.size(); j++) {
                    if (listChair.get(j).getId_schedule().equals(listSchedule.get(i).getId()))
                    {
                        String idChair = listChair.get(j).getId_chair();
                        sqlHandle.ChairDelete(idChair);
                    }
                }
                sqlHandle.ScheduleDelete(listSchedule.get(i).getId());
            }
            // Delete Schedule today but before present time
            else if (Integer.parseInt(dateString) == Integer.parseInt(today))
            {
                if (Integer.parseInt(hourString) <= Integer.parseInt(presentHour))
                {
                    // Delete Chair with Schedule today but before present time
                    ArrayList<ChairClass> listChair = new ArrayList<>();
                    listChair = sqlHandle.ChairRead();
                    for (int j = 0; j < listChair.size(); j++) {
                        if (listChair.get(j).getId_schedule().equals(listSchedule.get(i).getId()))
                        {
                            String idChair = listChair.get(j).getId_chair();
                            sqlHandle.ChairDelete(idChair);
                        }
                    }

                    sqlHandle.ScheduleDelete(listSchedule.get(i).getId());
                }
                else
                {
                    if (listSchedule.get(i).getTheater().equals(theater_schedule)
                            && listSchedule.get(i).getDate().equals(date_schedule))
                    {
                        mylist.add(listSchedule.get(i).getId() + " - " +
                                listSchedule.get(i).getId_movie() + " - " +
                                listSchedule.get(i).getStart_hour() + " - " +
                                listSchedule.get(i).getTheater() + " - " +
                                listSchedule.get(i).getDate());

                        listScheduleSelect.add(new ScheduleClass(listSchedule.get(i).getId(), listSchedule.get(i).getId_movie(),
                                listSchedule.get(i).getStart_hour(), listSchedule.get(i).getTheater(), listSchedule.get(i).getDate()));
                    }
                }
            }
            else {
                if (listSchedule.get(i).getTheater().equals(theater_schedule)
                        && listSchedule.get(i).getDate().equals(date_schedule))
                {
                    mylist.add(listSchedule.get(i).getId() + " - " +
                            listSchedule.get(i).getId_movie() + " - " +
                            listSchedule.get(i).getStart_hour() + " - " +
                            listSchedule.get(i).getTheater() + " - " +
                            listSchedule.get(i).getDate());

                    listScheduleSelect.add(new ScheduleClass(listSchedule.get(i).getId(), listSchedule.get(i).getId_movie(),
                            listSchedule.get(i).getStart_hour(), listSchedule.get(i).getTheater(), listSchedule.get(i).getDate()));
                }
            }
        }

        myadapter.notifyDataSetChanged();
    }

    void setUpListTheater()
    {
        arrayListTheater.clear();

        arrayListTheater.add("Rap 1");
        arrayListTheater.add("Rap 2");

        arrayAdapterTheater.notifyDataSetChanged();
    }

    void setUpListDate()
    {
        arrayListDate.clear();

        for (int i = 0; i < 7; i++)
        {
            arrayListDate.add(getDate(i));
        }

        arrayAdapterDate.notifyDataSetChanged();
    }

    String getDate(int days)
    {
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        simpleDateFormat = new SimpleDateFormat("dd");
        day = simpleDateFormat.format(calendar.getTime());
        simpleDateFormat = new SimpleDateFormat("MM");
        month = simpleDateFormat.format(calendar.getTime());
        simpleDateFormat = new SimpleDateFormat("yyyy");
        year = simpleDateFormat.format(calendar.getTime());

        return day + "/" + month + "/" + year;
    }

    void handleListTheater()
    {
        spinnerTheater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                theater_schedule = arrayListTheater.get(position);
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void handleListDate()
    {
        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                date_schedule = arrayListDate.get(position);
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}