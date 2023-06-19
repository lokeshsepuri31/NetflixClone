package com.example.netflix.ui.auth.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.R;
import com.example.netflix.ui.auth.WatchNowActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<SearchItem> searchItemList;

    public SearchAdapter(List<SearchItem> searchItemList){this.searchItemList = searchItemList;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewModel(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchViewModel searchViewModel = (SearchViewModel) holder;
        SearchItem searchItem = searchItemList.get(position);
        searchViewModel.movieTitle.setText(searchItem.getTitle());
        Picasso.get().load(searchItem.getUrl()).into(searchViewModel.movieImage);
        searchViewModel.movieImage.setOnClickListener((view)->{
            Intent intent = new Intent(searchItem.getActivity(), WatchNowActivity.class);
            ChildItem childItem = new ChildItem(searchItem.getTitle(),searchItem.getUrl(),searchItem.getActivity(),searchItem.getId());
            intent.putExtra(ChildItemAdapter.MOVIE_SELECTED,childItem);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            searchItem.getActivity().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return searchItemList.size();
    }

    class SearchViewModel extends RecyclerView.ViewHolder{
        private TextView movieTitle;
        private ImageView movieImage;
        public SearchViewModel(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.title_img);
            movieImage = itemView.findViewById(R.id.img_search);
        }
    }
}
