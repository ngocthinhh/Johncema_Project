package com.example.johncinema.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Activities.HistoryActivity;
import com.example.johncinema.Activities.InfoActivity;
import com.example.johncinema.Activities.LoginActivity;
import com.example.johncinema.HandleData;
import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class AccountFragment extends Fragment {

    View view;

    HandleData handleData;

    TextView textViewName;
    TextView textViewStar;
    TextView textViewLogOut;
    TextView textViewInfo;
    TextView textViewHistory;

    ImageView imageViewAvatar;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<UserClass> listUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        handleData = new HandleData(getContext());

        textViewName = view.findViewById(R.id.textViewName);
        textViewStar = view.findViewById(R.id.textViewStar);
        textViewLogOut = view.findViewById(R.id.textViewLogOut);
        textViewInfo = view.findViewById(R.id.textViewInfo);
        textViewHistory = view.findViewById(R.id.textViewHistory);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        // SQLite
        sqlHandle = new SQLHandle(getContext());
        listUser = new ArrayList<>();

        openActivity();
        logOut();

        return view;
    }

    @Override
    public void onStart() {
        updateData();
        super.onStart();
    }

    void updateData()
    {
        listUser.clear();
        listUser = sqlHandle.UserRead();

        Integer id_user = handleData.getDataInt("id_user");
        String avt = "";

        for (int i = 0; i < listUser.size(); i++)
        {
            if (id_user.equals(listUser.get(i).getId()))
            {
                textViewName.setText(listUser.get(i).getName());
                textViewStar.setText(listUser.get(i).getStar());
                avt = listUser.get(i).getUrl_avatar();

                break;
            }
        }

        if (!avt.equals("no_image"))
        {
            Uri uri = Uri.parse(avt);
            imageViewAvatar.setImageURI(uri);
        }
    }

    private void replaceActivity(Activity activity)
    {
        startActivity(new Intent(getContext(), activity.getClass()));
    }

    void resetData()
    {
        handleData.saveData("id_user", -1);
    }

    void logOut()
    {
        textViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetData();

                replaceActivity(new LoginActivity());
            }
        });
    }

    void openActivity()
    {
        textViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(new InfoActivity());
            }
        });

        textViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceActivity(new HistoryActivity());
            }
        });
    }
}