package com.example.netflix.ui.auth.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.netflix.R;

import java.util.List;


public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder> {

    private List<ParentItem> itemList;
    public ParentItemAdapter(List<ParentItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ParentViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parent_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder parentViewHolder, int position) {

        ParentItem parentItem = itemList.get(position);
        parentViewHolder.parentItemTitle.setText(parentItem.getParentItemTitle());
        LinearLayoutManager layoutManager = new LinearLayoutManager(parentViewHolder.childRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL,position%2 ==0);
        ChildItemAdapter childItemAdapter = new ChildItemAdapter(parentItem.getChildItemList());
        parentViewHolder.childRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.childRecyclerView.setAdapter(childItemAdapter);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {

        private TextView parentItemTitle;
        private RecyclerView childRecyclerView;

        ParentViewHolder(final View itemView) {
            super(itemView);
            parentItemTitle = itemView.findViewById(R.id.parent_item_title);
            childRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}


