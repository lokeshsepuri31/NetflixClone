package com.example.netflix.ui.auth;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ChildItemAdapter;
import com.example.netflix.util.ByteUtility;
import com.squareup.picasso.Picasso;


public class WatchNowActivity extends AppCompatActivity {

    TextView title;
    ImageView movieImage;

    Button share,favorite;

    ChildItem watchNowMovie;

    SharedPreferences sharedPreferences;

    DatabaseCallback databaseCallback = new DatabaseCallback();

    DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_now);
        watchNowMovie = getIntent().getExtras().getParcelable(ChildItemAdapter.MOVIE_SELECTED);
        title = findViewById(R.id.child_item_title);
        movieImage = findViewById(R.id.img_child_item);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        share = findViewById(R.id.share);
        favorite = findViewById(R.id.favorite_movies);

        databaseHandler = DatabaseHandler.getInstance(this);

        share.setOnClickListener((view)->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra("Data",title.getText().toString());
            startActivity(intent);
        });

        title.setText(watchNowMovie.getChildItemTitle());
        Picasso.get().load(watchNowMovie.getUrl()).into(movieImage);
        favorite.setOnClickListener((view)->{
            addToFavorites();
        });
        getSupportActionBar().hide();
    }

    public void addToFavorites(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = sharedPreferences.getInt(LoginActivity.LOGIN_USERID,0);
        Bitmap bitmap = ((BitmapDrawable)movieImage.getDrawable()).getBitmap();
        byte[] image = ByteUtility.getBitmapAsByteArray(bitmap);
        boolean isMovieAlreadyInDB = databaseCallback.isFavMovieAlreadyInLoggedInUser(databaseHandler,watchNowMovie.getId(),userId);
        if(!isMovieAlreadyInDB) {
            FavoriteMovies favoriteMovies = new FavoriteMovies(userId,watchNowMovie.getId(),image,watchNowMovie.getChildItemTitle());
            boolean addedMovie = databaseCallback.addFavMovie(databaseHandler, favoriteMovies);
            if (addedMovie) {
                Toast.makeText(this, favoriteMovies.getMovieTitle() + " is added to favorites.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "It is already added to favorites!!", Toast.LENGTH_SHORT).show();
        }
    }

}