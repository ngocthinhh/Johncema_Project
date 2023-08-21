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

import com.example.johncinema.Models.PosterClass;
import com.example.johncinema.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends ArrayAdapter<PosterClass> {

    public AdapterClass(@NonNull Context context, ArrayList<PosterClass> arrayList) {
        super(context, 0, (List<PosterClass>) arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_poster, parent, false);
        }

        PosterClass posterClass = getItem(position);
        TextView textView2 = listitemView.findViewById(R.id.textViewPoster);
        ImageView imageView2 = listitemView.findViewById(R.id.imageViewPoster);

        textView2.setText(posterClass.getTitle());
        imageView2.setImageURI(Uri.parse(posterClass.getUrlImage()));
        return listitemView;
    }
}
