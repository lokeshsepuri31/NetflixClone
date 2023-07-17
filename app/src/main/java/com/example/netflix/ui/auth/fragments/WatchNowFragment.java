package com.example.netflix.ui.auth.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.ui.auth.HomeActivity;
import com.example.netflix.ui.auth.LoginActivity;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ChildItemAdapter;
import com.example.netflix.util.ByteUtility;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class WatchNowFragment extends Fragment {

    MaterialButton favoriteLayout,share;

    ChildItem watchNowMovie;

    SharedPreferences sharedPreferences;

    TextView title,back;

    ImageView movieImage;

    DatabaseCallback databaseCallback = new DatabaseCallback();

    DatabaseHandler databaseHandler;

    boolean isMovieAlreadyInDB;

    int userId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watch_now, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null)
            watchNowMovie = bundle.getParcelable(ChildItemAdapter.MOVIE_SELECTED);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        favoriteLayout = view.findViewById(R.id.favorite_movies);

        back = view.findViewById(R.id.back);
        share = view.findViewById(R.id.share);
        movieImage = view.findViewById(R.id.img_child_item);
        title = view.findViewById(R.id.child_item_title);

        back.setOnClickListener(this::onBack);

        databaseHandler = DatabaseHandler.getInstance(getActivity());
        share.setOnClickListener((v)->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra("Data",title.getText().toString());
            startActivity(intent);
        });

        userId = sharedPreferences.getInt(LoginActivity.LOGIN_USERID,0);
        isMovieAlreadyInDB = databaseCallback.isFavMovieAlreadyInLoggedInUser(databaseHandler,watchNowMovie.getId(),userId);
        setColorOfIcon(isMovieAlreadyInDB);

        title.setText(watchNowMovie.getChildItemTitle());
        Picasso.get().load(watchNowMovie.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(movieImage);
        favoriteLayout.setOnClickListener(this::addToFavorites);
    }

    public void setColorOfIcon(boolean isMovieAlreadyInDB){
        if(isMovieAlreadyInDB)
            favoriteLayout.setIconTint(ColorStateList.valueOf(Color.parseColor("#FD011F")));
        else
            favoriteLayout.setIconTint(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
    }

    public void addToFavorites(View view){
        Bitmap bitmap = ((BitmapDrawable)movieImage.getDrawable()).getBitmap();
        byte[] image = ByteUtility.getBitmapAsByteArray(bitmap);
        isMovieAlreadyInDB = databaseCallback.isFavMovieAlreadyInLoggedInUser(databaseHandler,watchNowMovie.getId(),userId);
        if(!isMovieAlreadyInDB) {
            FavoriteMovies favoriteMovies = new FavoriteMovies(userId,watchNowMovie.getId(),image,watchNowMovie.getChildItemTitle());
            if (databaseCallback.addFavMovie(databaseHandler, favoriteMovies)) {
                setColorOfIcon(true);
                Toast.makeText(getActivity(), favoriteMovies.getMovieTitle() + " is added to favorites.", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (databaseCallback.deleteFavMovie(databaseHandler,userId, watchNowMovie.getId())) {
                setColorOfIcon(false);
                Toast.makeText(getActivity(), watchNowMovie.getChildItemTitle() + " is removed from your favorites.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBack(View view){
        HomeActivity homeActivity = (HomeActivity) getActivity();
        if(homeActivity != null)
            homeActivity.onBack();
    }
}