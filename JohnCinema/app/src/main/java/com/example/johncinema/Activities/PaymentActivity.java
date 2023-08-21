package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.HandleData;
import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.ScheduleClass;
import com.example.johncinema.Models.Ticket;
import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    TextView textViewBack;
    TextView textViewTitleImage;
    ImageView imageView;
    TextView textViewTheaterName;
    TextView textViewTime;
    TextView textViewDay;
    TextView textViewChair;
    TextView textViewIDTicket;
    TextView textViewStar;
    TextView textViewMoney;
    TextView textViewMoneyAccept;

    HandleData handleData;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<Ticket> listTicket;
    ArrayList<ChairClass> listChair;
    ArrayList<ScheduleClass> listSchedule;
    ArrayList<UserClass> listUser;
    ArrayList<MovieClass> listMovie;

    String time_buy = "non";
    String id_schedule = "non";
    String id_user = "non";
    String id_movie = "non";

    UserClass info_user;
    ArrayList<ChairClass> list_info_chair;
    ScheduleClass info_schedule;
    MovieClass info_movie;
    String info_chair;

    // Payment
    Button buttonPay;
    Integer money = 0;
    Integer stars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        textViewBack = findViewById(R.id.textViewBack);

        // Payment
        buttonPay = findViewById(R.id.buttonPay);

        // Infomation
        textViewTitleImage = findViewById(R.id.textViewTitleImage);
        imageView = findViewById(R.id.imageView);
        textViewTheaterName = findViewById(R.id.textViewTheaterName);
        textViewTime = findViewById(R.id.textViewTime);
        textViewDay = findViewById(R.id.textViewDay);
        textViewChair = findViewById(R.id.textViewChair);
        textViewIDTicket = findViewById(R.id.textViewIDTicket);
        textViewStar = findViewById(R.id.textViewStar);
        textViewMoney = findViewById(R.id.textViewMoney);
        textViewMoneyAccept = findViewById(R.id.textViewMoneyAccept);

        // Handle Data
        handleData = new HandleData(this);
        sqlHandle = new SQLHandle(this);
        listTicket = new ArrayList<>();
        listChair = new ArrayList<>();
        listSchedule = new ArrayList<>();
        listUser = new ArrayList<>();
        listMovie = new ArrayList<>();
        list_info_chair = new ArrayList<>();

        getDataForLists();
        showInfo();
        setFinish();
        close();
    }

    void close()
    {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ChairClass> listChairBought = new ArrayList<>();
                listChairBought = sqlHandle.ChairRead();
                String time_bought = handleData.getDataString("time_buy");
                ArrayList<ChairClass> listChairToDelete = new ArrayList<>();

                for (int i = 0; i < listChairBought.size(); i++)
                {
                    if (listChairBought.get(i).getId_chair().contains(time_bought))
                    {
                        listChairToDelete.add(listChairBought.get(i));
                    }
                }

                String msg = "non";
                for (int i = 0; i < listChairToDelete.size(); i++)
                {
                    msg = sqlHandle.ChairDelete(listChairToDelete.get(i).getId_chair());
                }

                finish();
            }
        });
    }

    void showInfo()
    {
        textViewTitleImage.setText(info_movie.getName());
        imageView.setImageURI(Uri.parse(info_movie.getUrlPoster()));
        textViewTheaterName.setText(info_schedule.getTheater());
        textViewTime.setText(info_schedule.getStart_hour());
        textViewDay.setText(info_schedule.getDate());

        info_chair = list_info_chair.get(0).getRow().toString() + "-" + list_info_chair.get(0).getColumn().toString();
        money += 50000;
        stars += 1;
        for (int i  = 1; i < list_info_chair.size(); i++)
        {
            info_chair += ", " + list_info_chair.get(i).getRow().toString() + "-" + list_info_chair.get(i).getColumn().toString();
            money += 50000;
            stars += 1;
        }
        textViewChair.setText(info_chair);

        textViewIDTicket.setText(id_user + "-" + time_buy);
        textViewStar.setText(stars.toString());
        textViewMoney.setText(money.toString());
        textViewMoneyAccept.setText(money.toString());
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

        // Get info Chair
        time_buy = handleData.getDataString("time_buy");
        listChair.clear();
        listChair = sqlHandle.ChairRead();
        for (int i = 0 ;i < listChair.size(); i++)
        {
            if (listChair.get(i).getId_chair().contains(time_buy))
            {
                list_info_chair.add(listChair.get(i));
            }
        }

        // Get info Schedule
        id_schedule = list_info_chair.get(0).getId_schedule();
        listSchedule.clear();
        listSchedule = sqlHandle.ScheduleRead();
        for (int i = 0; i < listSchedule.size(); i++)
        {
            if (listSchedule.get(i).getId().equals(id_schedule))
            {
                info_schedule = listSchedule.get(i);
                break;
            }
        }

        // Get info Movie
        id_movie = info_schedule.getId_movie();
        listMovie.clear();
        listMovie = sqlHandle.MovieRead();
        for (int i = 0; i < listMovie.size(); i++)
        {
            if (listMovie.get(i).getId().equals(id_movie))
            {
                info_movie = listMovie.get(i);
                break;
            }
        }
    }

    void setFinish()
    {
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                msg = sqlHandle.TicketInsert(id_user + "-" + time_buy, id_user, info_chair, time_buy, info_movie.getName(),
                        info_movie.getUrlPoster(), info_schedule.getTheater(), info_schedule.getStart_hour(), info_schedule.getDate(),
                        money.toString(), "5");
                Toast.makeText(PaymentActivity.this, msg, Toast.LENGTH_SHORT).show();

                Integer countStar = Integer.parseInt(info_user.getStar()) + stars;
                msg = sqlHandle.UserUpdate(info_user.getId(), info_user.getName(), info_user.getPhone(),
                        info_user.getPassword(), countStar.toString(), info_user.getRole(), info_user.getUrl_avatar());

                startActivity(new Intent(PaymentActivity.this, new MainActivity().getClass()));
            }
        });
    }
}