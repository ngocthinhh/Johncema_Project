package com.example.johncinema.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.service.autofill.Dataset;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class HandleMovie extends AppCompatActivity {

    // Back
    TextView textViewBack;

    // Input
    EditText editTextMovieID;
    EditText editTextMovieName;
    EditText editTextDirectorname;
    EditText editTextDuration;
    ImageView imageViewPoster;
    String stringUriPoster;
    Spinner spinnerDebut;
    String debutString = "non";

    // Event Button
    Button buttonAdd;
    Button buttonUpdate;
    Button buttonDelete;

    // Adapter Spinner
    String debut[] = {"Future", "Present"};
    ArrayAdapter<String> arrayAdapterDebut;

    // ListView
    ListView listView;

    // Test SQLite
    SQLHandle sqlHandle;
    ArrayList<MovieClass> listMovie;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_movie);

        textViewBack = findViewById(R.id.textViewBack);
        editTextMovieID = findViewById(R.id.editTextMovieID);
        editTextMovieName = findViewById(R.id.editTextMovieName);
        editTextDirectorname = findViewById(R.id.editTextDirectorname);
        editTextDuration = findViewById(R.id.editTextDuration);
        imageViewPoster = findViewById(R.id.imageViewPoster);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        listView = findViewById(R.id.listView);
        stringUriPoster = "";
        imageViewPoster.setImageResource(R.color.green);
        spinnerDebut = findViewById(R.id.spinnerDebut);


        sqlHandle = new SQLHandle(this);
        listMovie = new ArrayList<>();
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);

        listView.setAdapter(myadapter);


        close();
        pickPoster();
        setArrayAdapterDebut();
        handleButton();
        loadData();
        resizeListView();
        select();
    }

    void handleButton()
    {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sqlHandle.MovieInsert(editTextMovieID.getText().toString(),
                        editTextMovieName.getText().toString(),
                        editTextDirectorname.getText().toString(),
                        editTextDuration.getText().toString(),
                        debutString,
                        stringUriPoster,
                        "5");
                Toast.makeText(HandleMovie.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                resizeListView();
                refeshEditText();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sqlHandle.MovieUpdate(editTextMovieID.getText().toString(),
                        editTextMovieName.getText().toString(),
                        editTextDirectorname.getText().toString(),
                        editTextDuration.getText().toString(),
                        debutString,
                        stringUriPoster,
                        "5");
                Toast.makeText(HandleMovie.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                resizeListView();
                refeshEditText();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                String msg = sqlHandle.MovieDelete(editTextMovieID.getText().toString());
                Toast.makeText(HandleMovie.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                resizeListView();
                refeshEditText();
            }
        });
    }

    void loadData()
    {
        listMovie.clear();
        mylist.clear();
        listMovie = sqlHandle.MovieRead();
        for (int i = 0; i < listMovie.size(); i++)
        {
            mylist.add(listMovie.get(i).getId() + " - " +
                        listMovie.get(i).getName() + " - " +
                        listMovie.get(i).getDuration() + " - " +
                        listMovie.get(i).getDebut());
        }

        myadapter.notifyDataSetChanged();
    }

    private void resizeListView() {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = (mylist.size() * 135);
    }

    void select()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTextMovieID.setText(listMovie.get(position).getId());
                editTextMovieName.setText(listMovie.get(position).getName());
                editTextDuration.setText(listMovie.get(position).getDuration());
                debutString = listMovie.get(position).getDebut();
                if (debutString.equals("Future"))
                {
                    spinnerDebut.setSelection(0);
                }
                else if (debutString.equals("Present"))
                {
                    spinnerDebut.setSelection(1);
                }
                imageViewPoster.setImageURI(Uri.parse(listMovie.get(position).getUrlPoster()));
                stringUriPoster = listMovie.get(position).getUrlPoster();
            }
        });
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

    void refeshEditText()
    {
        editTextMovieID.setText("");
        editTextMovieName.setText("");
        editTextDirectorname.setText("");
        editTextDuration.setText("");
        spinnerDebut.setSelection(0);
        debutString = "non";
        stringUriPoster = "";
        imageViewPoster.setImageResource(R.color.green);
    }

    void setArrayAdapterDebut()
    {
        arrayAdapterDebut = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, debut);
        spinnerDebut.setAdapter(arrayAdapterDebut);
        spinnerDebut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                debutString = debut[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void pickPoster()
    {
        imageViewPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(HandleMovie.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        stringUriPoster = data.getData().toString();
        imageViewPoster.setImageURI(Uri.parse(stringUriPoster));
    }
}