package com.example.johncinema.Activities;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.HandleData;
import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.Models.Ticket;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class TicketActivity extends AppCompatActivity {

    HandleData handleData;

    TextView textViewBack;
    TextView textViewTitleImage;
    ImageView imageView;
    TextView textViewTheaterName;
    TextView textViewTime;
    TextView textViewDay;
    TextView textViewIDTicket;
    TextView textViewStar;
    TextView textViewMoney;
    TextView textViewVote;
    TextView textViewChair;
    Button buttonClose;

    // Vote
    LinearLayout layoutVote;
    TextView textViewStar1;
    TextView textViewStar2;
    TextView textViewStar3;
    TextView textViewStar4;
    TextView textViewStar5;
    Button buttonVote;
    Integer star = 0;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<Ticket> listTicket;
    String id_ticket = "non";
    Ticket infoTicket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        handleData = new HandleData(this);

        textViewBack = findViewById(R.id.textViewBack);
        textViewTitleImage = findViewById(R.id.textViewTitleImage);
        imageView = findViewById(R.id.imageView);
        textViewTheaterName = findViewById(R.id.textViewTheaterName);
        textViewTime = findViewById(R.id.textViewTime);
        textViewDay = findViewById(R.id.textViewDay);
        textViewIDTicket = findViewById(R.id.textViewIDTicket);
        textViewStar = findViewById(R.id.textViewStar);
        textViewMoney = findViewById(R.id.textViewMoney);
        buttonClose = findViewById(R.id.buttonClose);
        textViewVote = findViewById(R.id.textViewVote);
        textViewChair = findViewById(R.id.textViewChair);

        // Vote
        layoutVote = findViewById(R.id.layoutVote);
        textViewStar1 = findViewById(R.id.textViewStar1);
        textViewStar2 = findViewById(R.id.textViewStar2);
        textViewStar3 = findViewById(R.id.textViewStar3);
        textViewStar4 = findViewById(R.id.textViewStar4);
        textViewStar5 = findViewById(R.id.textViewStar5);
        buttonVote = findViewById(R.id.buttonVote);

        // SQLite
        sqlHandle = new SQLHandle(this);
        listTicket = new ArrayList<>();


        uploadData();
        vote();
        back();
        handleVote();
    }

    void uploadData()
    {
        listTicket.clear();
        listTicket = sqlHandle.TicketRead();
        id_ticket = handleData.getDataString("id_ticket");

        for (int i = 0; i < listTicket.size(); i++)
        {
            if (listTicket.get(i).getId_ticket().equals(id_ticket))
            {
                infoTicket = listTicket.get(i);
                break;
            }
        }

        textViewTitleImage.setText(infoTicket.getTitle());
        imageView.setImageURI(Uri.parse(infoTicket.getIdImage()));
        textViewTheaterName.setText(infoTicket.getTheaterName());
        textViewTime.setText(infoTicket.getTime());
        textViewDay.setText(infoTicket.getDay());
        textViewChair.setText(infoTicket.getId_chair_ticket());
        textViewIDTicket.setText(infoTicket.getId_ticket());
        Integer star = Integer.parseInt(infoTicket.getMoney()) / 50000;
        textViewStar.setText(star.toString());
        textViewMoney.setText(infoTicket.getMoney());

        switch (Integer.parseInt(infoTicket.getRating()))
        {
            case 1:
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                break;
            case 2:
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                break;
            case 3:
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                break;
            case 4:
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                break;
            case 5:
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                break;
        }
    }

    void vote()
    {
        textViewVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutVote.setVisibility(View.VISIBLE);
                buttonVote.setVisibility(View.VISIBLE);
            }
        });
    }

    void handleVote()
    {
        textViewStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);

                star = 1;
            }
        });

        textViewStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);

                star = 2;
            }
        });

        textViewStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);

                star = 3;
            }
        });

        textViewStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_unselect_icon), null, null, null);

                star = 4;
            }
        });

        textViewStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStar1.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar2.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar3.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar4.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);
                textViewStar5.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.star_select_icon), null, null, null);

                star = 5;
            }
        });

        buttonVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHandle.TicketUpdate(infoTicket.getId_ticket(), infoTicket.getId_user_ticket(), infoTicket.getId_chair_ticket(),
                        infoTicket.getTime_buy(), infoTicket.getTitle(), infoTicket.getIdImage(), infoTicket.getTheaterName(),
                        infoTicket.getTime(), infoTicket.getDay(), infoTicket.getMoney(), star.toString());

                Toast.makeText(TicketActivity.this, "Xin cảm ơn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void back()
    {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}