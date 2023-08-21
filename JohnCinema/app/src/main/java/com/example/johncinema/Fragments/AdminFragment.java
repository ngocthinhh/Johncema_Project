package com.example.johncinema.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.johncinema.Activities.HandleMovie;
import com.example.johncinema.Activities.HandleScheduleActivity;
import com.example.johncinema.Activities.HandleUserActivity;
import com.example.johncinema.R;

public class AdminFragment extends Fragment {

    View view;

    ImageView imageViewAddMovie;
    ImageView imageViewAddSchedule;
    ImageView imageViewAddUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin, container, false);

        imageViewAddMovie = view.findViewById(R.id.imageViewAddMovie);
        imageViewAddSchedule = view.findViewById(R.id.imageViewAddSchedule);
        imageViewAddUser = view.findViewById(R.id.imageViewAddUser);

        setAddMovie();
        setAddSchedule();
        setAddUser();

        return view;
    }

    void setAddMovie()
    {
        imageViewAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), new HandleMovie().getClass()));
            }
        });
    }

    void setAddSchedule()
    {
        imageViewAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), new HandleScheduleActivity().getClass()));
            }
        });
    }

    void setAddUser()
    {
        imageViewAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), new HandleUserActivity().getClass()));
            }
        });
    }
}