package com.example.netflix.ui.auth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ChildItemAdapter;
import com.squareup.picasso.Picasso;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class WatchNowActivity extends AppCompatActivity {

    TextView title;
    ImageView movieImage;

    Button share,favorite;
    ChildItem watchNowMovie;

    public static List<ChildItem> favoriteMovies = new ArrayList<>();

    SharedPreferences sharedPreferences;

    public static final String FAVORITE_MOVIE_KEY = "Favorite Movie";


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

        share.setOnClickListener((view)->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra("Data",title.getText().toString());
            startActivity(intent);
        });

        Log.d(TAG, "onCreate: WatchNowActivity");

        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: WatchNowActivity");
        title.setText(watchNowMovie.getChildItemTitle());
        Picasso.get().load(watchNowMovie.getUrl()).into(movieImage);
        favorite.setOnClickListener((view)->{
            favoriteMovies.add(watchNowMovie);
            String jsonString = new GsonBuilder().create().toJson(favoriteMovies);
            sharedPreferences.edit().putString(FAVORITE_MOVIE_KEY,jsonString).apply();
            Toast.makeText(this, title.getText().toString()+" is added to your favorites", Toast.LENGTH_SHORT).show();
        });
    }
}