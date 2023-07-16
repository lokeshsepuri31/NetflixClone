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
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ChildItemAdapter;
import com.example.netflix.util.ByteUtility;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;


public class WatchNowActivity extends AppCompatActivity {

    TextView title,description;
    ImageView movieImage;

    Button share;

    MaterialButton favoriteLayout;

    ChildItem watchNowMovie;

    SharedPreferences sharedPreferences;

    DatabaseCallback databaseCallback = new DatabaseCallback();

    DatabaseHandler databaseHandler;

    boolean isMovieAlreadyInDB;

    int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_now);
        watchNowMovie = getIntent().getExtras().getParcelable(ChildItemAdapter.MOVIE_SELECTED);
        title = findViewById(R.id.child_item_title);
        description = findViewById(R.id.description);
        movieImage = findViewById(R.id.img_child_item);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        share = findViewById(R.id.share);
        favoriteLayout = findViewById(R.id.favorite_movies);

        databaseHandler = DatabaseHandler.getInstance(this);
        share.setOnClickListener((view)->{
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
        Picasso.get().load(watchNowMovie.getUrl()).into(movieImage);
        favoriteLayout.setOnClickListener((view)->{
            addToFavorites();
        });

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    public void setColorOfIcon(boolean isMovieAlreadyInDB){
        if(isMovieAlreadyInDB)
            favoriteLayout.setIconTint(ColorStateList.valueOf(Color.parseColor("#FD011F")));
        else
            favoriteLayout.setIconTint(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
    }

    public void addToFavorites(){
        Bitmap bitmap = ((BitmapDrawable)movieImage.getDrawable()).getBitmap();
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