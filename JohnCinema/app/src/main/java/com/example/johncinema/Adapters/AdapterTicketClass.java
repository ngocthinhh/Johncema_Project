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
import com.example.johncinema.Models.TicketClass;
import com.example.johncinema.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTicketClass extends ArrayAdapter<TicketClass> {

    public AdapterTicketClass(@NonNull Context context, ArrayList<TicketClass> arrayList) {
        super(context, 0, (List<TicketClass>) arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_ticket, parent, false);
        }

        TicketClass ticketClass = getItem(position);
        TextView textViewTitle = listitemView.findViewById(R.id.textViewTitle);
        ImageView imageViewTicket = listitemView.findViewById(R.id.imageViewTicket);
        TextView textViewTheaterName = listitemView.findViewById(R.id.textViewTheaterName);
        TextView textViewTime = listitemView.findViewById(R.id.textViewTime);
        TextView textViewDay = listitemView.findViewById(R.id.textViewDay);

        textViewTitle.setText(ticketClass.getTitle());
        imageViewTicket.setImageURI(Uri.parse(ticketClass.getIdImage()));
        textViewTheaterName.setText(ticketClass.getTheaterName());
        textViewTime.setText(ticketClass.getTime());
        textViewDay.setText(ticketClass.getDay());

        return listitemView;
    }
}
