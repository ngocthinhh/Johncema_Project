package com.example.johncinema.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.johncinema.Activities.HandleMovie;
import com.example.johncinema.Activities.MainActivity;
import com.example.johncinema.Activities.ScheduleActivity;
import com.example.johncinema.Adapters.AdapterClass;
import com.example.johncinema.Adapters.AdapterMovieClass;
import com.example.johncinema.HandleData;
import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.PosterClass;
import com.example.johncinema.Models.ScheduleClass;
import com.example.johncinema.Models.Ticket;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;

    // Image Slider
    ImageSlider imageSlider;
    ArrayList<SlideModel> imageList;

    //Text View Time
    TextView textViewPresent;
    TextView textViewFuture;

    //Grid View Poster
    GridView gridViewPoster;
    ArrayList<MovieClass> listMovieToShow;
    AdapterMovieClass adapterMovieClass;

    // Handle Data
    HandleData handleData;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<MovieClass> listMovie;
    ArrayList<Ticket> listTicket;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        sqlHandle = new SQLHandle(getContext());
        listMovie = new ArrayList<>();
        listTicket = new ArrayList<>();

        imageList = new ArrayList<>();
        setUpImageSlide();
        imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

        // Handle Data
        handleData = new HandleData(getContext());

        // Grid View Poster
        gridViewPoster = view.findViewById(R.id.gridViewPoster);
        listMovieToShow = new ArrayList<>();
        adapterMovieClass = new AdapterMovieClass(getContext(), listMovieToShow);
        gridViewPoster.setAdapter(adapterMovieClass);
        addPresentMovies();

        resizeGridView();
        switchPoster();
        select();

        return view;
    }

    void setUpImageSlide()
    {
        imageList.add(new SlideModel(R.drawable.elemental_poster_horizontal, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.mission_poster_horizontal, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.insidious_poster_horizontal, ScaleTypes.FIT));
    }

    void getRating()
    {

    }

    private void addPresentMovies()
    {
        listMovieToShow.clear();
        listMovie.clear();
        listMovie = sqlHandle.MovieRead();

        listTicket.clear();
        listTicket = sqlHandle.TicketRead();

        Integer countTicket = 0;
        Integer countRating = 0;

        for (int i = 0; i < listMovie.size(); i++)
        {
            if (listMovie.get(i).getDebut().equals("Present"))
            {

                if (listTicket.size() > 0)
                {
                    countTicket = 0;
                    countRating = 0;
                    for (int j = 0; j < listTicket.size(); j++)
                    {
                        if (listTicket.get(j).getTitle().equals(listMovie.get(i).getName()))
                        {
                            countRating += Integer.parseInt(listTicket.get(j).getRating());
                            countTicket += 1;
                        }
                    }
                    if (countTicket == 0)
                    {
                        countRating = 5;
                    }
                    else
                    {
                        Double result = Double.parseDouble(countRating.toString()) / Double.parseDouble(countTicket.toString());
                        Integer resultInteger = 0;
                        if (((result * 10) % 10) >= 5)
                        {
                            resultInteger = Integer.parseInt(countRating.toString()) / Integer.parseInt(countTicket.toString()) + 1;
                        }
                        else {
                            resultInteger = Integer.parseInt(countRating.toString()) / Integer.parseInt(countTicket.toString());
                        }
                        countRating = resultInteger;
                    }
                }
                else
                {
                    countRating = 5;
                }

                listMovieToShow.add(new MovieClass(listMovie.get(i).getId(), listMovie.get(i).getName(), listMovie.get(i).getDirector(),
                        listMovie.get(i).getDuration(), listMovie.get(i).getDebut(), listMovie.get(i).getUrlPoster(),
                        countRating.toString()));
            }
        }

        adapterMovieClass.notifyDataSetChanged();

        resizeGridView();
    }

    private void addFutureMovies()
    {
        listMovieToShow.clear();
        listMovie.clear();
        listMovie = sqlHandle.MovieRead();

        for (int i = 0; i < listMovie.size(); i++)
        {
            if (listMovie.get(i).getDebut().equals("Future"))
            {
                listMovieToShow.add(new MovieClass(listMovie.get(i).getId(), listMovie.get(i).getName(), listMovie.get(i).getDirector(),
                        listMovie.get(i).getDuration(), listMovie.get(i).getDebut(), listMovie.get(i).getUrlPoster(),
                        listMovie.get(i).getRating()));
            }
        }

        adapterMovieClass.notifyDataSetChanged();

        resizeGridView();
    }

    private void switchPoster()
    {
        textViewPresent = view.findViewById(R.id.textViewPresent);
        textViewFuture = view.findViewById(R.id.textViewFuture);

        textViewPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPresent.setTextAppearance(R.style.boldText);
                textViewFuture.setTextAppearance(R.style.normalText);

                addPresentMovies();
            }
        });

        textViewFuture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewFuture.setTextAppearance(R.style.boldText);
                textViewPresent.setTextAppearance(R.style.normalText);

                addFutureMovies();
            }
        });
    }

    private void resizeGridView() {
        ViewGroup.LayoutParams params = gridViewPoster.getLayoutParams();
        if (listMovieToShow.size() % 2 == 0)
        {
            params.height = ((listMovieToShow.size() / 2) * 842);   // + 842 (old: 780)
        }
        else
        {
            params.height = (((listMovieToShow.size() / 2) + 1) * 842);   // + 842
        }
    }

    void saveData(int position)
    {
        handleData.saveData("id_movie", listMovieToShow.get(position).getId());
        handleData.saveData("title", listMovieToShow.get(position).getName());
    }

    void select()
    {
        gridViewPoster.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveData(position);
                startActivity(new Intent(getContext(), new ScheduleActivity().getClass()));
            }
        });
    }
}