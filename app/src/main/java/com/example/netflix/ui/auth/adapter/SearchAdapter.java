package com.example.netflix.ui.auth.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ChildItem> searchItemList;

    public SearchAdapter(List<ChildItem> searchItemList){this.searchItemList = searchItemList;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewModel(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchViewModel searchViewModel = (SearchViewModel) holder;
        ChildItem searchItem = searchItemList.get(position);
        searchViewModel.movieTitle.setText(searchItem.getChildItemTitle());
        searchViewModel.movieImage.setOnClickListener((view)->{
            ChildItemAdapter childItemAdapter = new ChildItemAdapter();
            childItemAdapter.replaceFragment("Search",searchItem);
        });
        Picasso.get().load(searchItem.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .resize(350,460)
                .into(searchViewModel.movieImage);
    }

    @Override
    public int getItemCount() {
        return searchItemList.size();
    }

    static class SearchViewModel extends RecyclerView.ViewHolder{
        private final TextView movieTitle;
        private final ImageView movieImage;
        public SearchViewModel(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.title_img);
            movieImage = itemView.findViewById(R.id.img_search);
        }
    }
}
