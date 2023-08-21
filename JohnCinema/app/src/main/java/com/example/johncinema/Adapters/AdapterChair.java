package com.example.johncinema.Adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterChair extends ArrayAdapter<ChairClass> {
    public AdapterChair(@NonNull Context context, ArrayList<ChairClass> arrayList) {
        super(context, 0, (List<ChairClass>) arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_chair, parent, false);
        }

        ChairClass chairClass = getItem(position);

        TextView textViewIdChair = listitemView.findViewById(R.id.textViewIdChair);
        ImageView imageViewBackgroundColor = listitemView.findViewById(R.id.imageViewBackgroundColor);

        textViewIdChair.setText(chairClass.getRow() + "-" + chairClass.getColumn());
        if (chairClass.getState().equals("no_sit"))
        {
            imageViewBackgroundColor.setBackground(getContext().getDrawable(R.color.blue));
        }
        else if (chairClass.getState().equals("sold"))
        {
            imageViewBackgroundColor.setBackground(getContext().getDrawable(R.color.gray));
        }

        return listitemView;
    }
}
