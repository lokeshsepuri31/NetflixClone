package com.example.netflix.ui.auth;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.databinding.ActivityWatchNowBinding;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ChildItemAdapter;
import com.example.netflix.util.ByteUtility;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;


public class WatchNowActivity extends AppCompatActivity {

    MaterialButton favoriteLayout;

    ChildItem watchNowMovie;

    SharedPreferences sharedPreferences;

    DatabaseCallback databaseCallback = new DatabaseCallback();

    DatabaseHandler databaseHandler;

    boolean isMovieAlreadyInDB;

    int userId;

    ActivityWatchNowBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityWatchNowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        watchNowMovie = getIntent().getExtras().getParcelable(ChildItemAdapter.MOVIE_SELECTED);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        favoriteLayout = findViewById(R.id.favorite_movies);

        binding.back.setOnClickListener(this::onBack);

        databaseHandler = DatabaseHandler.getInstance(this);
        binding.share.setOnClickListener((view)->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra("Data",binding.childItemTitle.getText().toString());
            startActivity(intent);
        });

        userId = sharedPreferences.getInt(LoginActivity.LOGIN_USERID,0);
        isMovieAlreadyInDB = databaseCallback.isFavMovieAlreadyInLoggedInUser(databaseHandler,watchNowMovie.getId(),userId);
        setColorOfIcon(isMovieAlreadyInDB);

        binding.childItemTitle.setText(watchNowMovie.getChildItemTitle());
        Picasso.get().load(watchNowMovie.getUrl()).into(binding.imgChildItem);
        favoriteLayout.setOnClickListener(this::addToFavorites);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    public void setColorOfIcon(boolean isMovieAlreadyInDB){
        if(isMovieAlreadyInDB)
            favoriteLayout.setIconTint(ColorStateList.valueOf(Color.parseColor("#FD011F")));
        else
            favoriteLayout.setIconTint(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
    }

    public void addToFavorites(View view){
        Bitmap bitmap = ((BitmapDrawable)binding.imgChildItem.getDrawable()).getBitmap();
        byte[] image = ByteUtility.getBitmapAsByteArray(bitmap);
        isMovieAlreadyInDB = databaseCallback.isFavMovieAlreadyInLoggedInUser(databaseHandler,watchNowMovie.getId(),userId);
        if(!isMovieAlreadyInDB) {
            FavoriteMovies favoriteMovies = new FavoriteMovies(userId,watchNowMovie.getId(),image,watchNowMovie.getChildItemTitle());
            if (databaseCallback.addFavMovie(databaseHandler, favoriteMovies)) {
                setColorOfIcon(true);
                Toast.makeText(this, favoriteMovies.getMovieTitle() + " is added to favorites.", Toast.LENGTH_SHORT).show();
            }
        }else {
            if (databaseCallback.deleteFavMovie(databaseHandler,userId, watchNowMovie.getId())) {
                setColorOfIcon(false);
                Toast.makeText(this, watchNowMovie.getChildItemTitle() + " is removed from your favorites.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBack(View view){
        onBackPressed();
    }
}