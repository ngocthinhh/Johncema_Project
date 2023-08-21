package com.example.johncinema.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.PosterClass;
import com.example.johncinema.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMovieClass extends ArrayAdapter<MovieClass> {
    public AdapterMovieClass(@NonNull Context context, ArrayList<MovieClass> arrayList) {
        super(context, 0, (List<MovieClass>) arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_poster, parent, false);
        }

        MovieClass movieClass = getItem(position);
        TextView textView2 = listitemView.findViewById(R.id.textViewPoster);
        ImageView imageView2 = listitemView.findViewById(R.id.imageViewPoster);
        TextView textViewRating = listitemView.findViewById(R.id.textViewRating);

        textView2.setText(movieClass.getName());
        imageView2.setImageURI(Uri.parse(movieClass.getUrlPoster()));
        textViewRating.setText(movieClass.getRating());
        return listitemView;
    }
}
