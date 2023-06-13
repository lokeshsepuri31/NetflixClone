package com.example.netflix.ui.auth.adapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.netflix.R;
import com.example.netflix.ui.auth.HomeVM;
import com.example.netflix.ui.auth.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChildItem> childItemList;
    private View view;

    RelativeLayout relativeLayout;



    ChildItemAdapter(List<ChildItem> childItemList) {
        this.childItemList = childItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_item_view, viewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChildItem childItem = childItemList.get(position);
        ChildViewHolder childViewHolder = (ChildViewHolder) holder;
        childViewHolder.childItemTitle.setText(childItem.getChildItemTitle());
        Picasso.get().load(childItem.getUrl()).into(childViewHolder.childImage);
        relativeLayout = view.findViewById(R.id.card_relative);
        relativeLayout.setOnClickListener((v)->{
            onMovieSelected(position);
        });
    }

    public void onMovieSelected(int position){
        Intent intent = new Intent();
    }

    @Override
    public int getItemCount() {
        return childItemList.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView childItemTitle;

        ImageView childImage;

        ChildViewHolder(View itemView) {
            super(itemView);
            this.childItemTitle = itemView.findViewById(R.id.child_item_title);
            this.childImage = itemView.findViewById(R.id.img_child_item);
        }
    }
}

