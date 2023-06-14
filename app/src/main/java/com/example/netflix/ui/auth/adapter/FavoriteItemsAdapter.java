package com.example.netflix.ui.auth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.netflix.R;
import com.example.netflix.data.pojo.Items;
import com.example.netflix.ui.auth.WatchNowActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FavoriteItemsAdapter extends ArrayAdapter<Items> {

    List<Items> items;

    public FavoriteItemsAdapter(@NonNull Context context, @NotNull List<Items> countryList) {
        super(context, 0, countryList);
        items = new ArrayList<>(countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }


    public View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.favorite_items_view, parent, false);

        Items item = getItem(position);

        ImageView image = convertView.findViewById(R.id.image);
        TextView text = convertView.findViewById(R.id.text);
        TextView delete = convertView.findViewById(R.id.delete);
        delete.setOnClickListener((view)->{
            items.remove(item);
            this.notifyDataSetChanged();
        });

        if (item != null) {
            text.setText(item.getMovieTitle());
            Picasso.get().load(item.getUrl()).into(image);
        }
        return convertView;
    }
}