package com.example.netflix.ui.auth.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.ui.auth.fragments.FavoriteFragment;
import com.example.netflix.util.ByteUtility;

import java.util.List;

public class FavoriteItemsAdapter extends RecyclerView.Adapter<FavoriteItemsViewHolder> {

    List<FavoriteMovies> items;

    public FavoriteItemsAdapter(List<FavoriteMovies> items){
        this.items = items;
    }

    DatabaseCallback databaseCallback = new DatabaseCallback();

    @NonNull
    @Override
    public FavoriteItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteItemsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_items_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteItemsViewHolder holder, int position) {
        FavoriteMovies favoriteMovies = items.get(position);
        holder.movieTitle.setText(favoriteMovies.getMovieTitle());
        holder.movieImage.setImageBitmap(ByteUtility.getBitmapFromByteArray(favoriteMovies.getImage()));
        holder.delete.setOnClickListener((view) -> {
            items.remove(favoriteMovies);
            if (onMovieDeleted(favoriteMovies)) {
                this.notifyDataSetChanged();
                Toast.makeText(view.getContext(), favoriteMovies.getMovieTitle() + " is deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onMovieDeleted(FavoriteMovies favoriteMovies) {
        return databaseCallback.deleteFavMovie(FavoriteFragment.databaseHandler, favoriteMovies);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}

class FavoriteItemsViewHolder extends RecyclerView.ViewHolder {

    TextView movieTitle, delete;

    ImageView movieImage;


    public FavoriteItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        movieImage = itemView.findViewById(R.id.image);
        movieTitle = itemView.findViewById(R.id.title);
        delete = itemView.findViewById(R.id.delete);
    }
}