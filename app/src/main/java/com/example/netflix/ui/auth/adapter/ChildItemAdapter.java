package com.example.netflix.ui.auth.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.netflix.R;
import com.example.netflix.ui.auth.HomeActivity;
import com.example.netflix.ui.auth.fragments.WatchNowFragment;
import com.example.netflix.util.NetworkReceiverCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChildItem> childItemList;
    private View view;
    Activity activity;

    public static final String MOVIE_SELECTED = "Watch Now";


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
        activity = childItem.getActivity();
        Picasso.get().load(childItem.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .resize(350,500)
                .into(childViewHolder.childImage);
        childViewHolder.childImage.setOnClickListener((v)->{
            if(NetworkReceiverCallback.isConnection(activity))
                onMovieSelected(position);
            else
                NetworkReceiverCallback.showSnackbar(view);
        });
    }

    public void onMovieSelected(int position){
        ChildItem childItem = childItemList.get(position);
        WatchNowFragment watchNowFragment = new WatchNowFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_SELECTED,childItem);
        watchNowFragment.setArguments(bundle);
        HomeActivity homeActivity = (HomeActivity) activity;
        homeActivity.openWatchNowFragment(watchNowFragment);
    }

    @Override
    public int getItemCount() {
        return childItemList.size();
    }

    static class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView childItemTitle;

        ImageView childImage;

        ChildViewHolder(View itemView) {
            super(itemView);
            this.childItemTitle = itemView.findViewById(R.id.child_item_title);
            this.childImage = itemView.findViewById(R.id.img_child_item);
        }
    }
}

