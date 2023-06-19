package com.example.netflix.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.ui.auth.adapter.FragmentAdapter;
import com.example.netflix.ui.auth.fragments.FavoriteFragment;
import com.example.netflix.ui.auth.fragments.HomeFragment;
import com.example.netflix.ui.auth.fragments.ProfileFragment;
import com.example.netflix.ui.auth.fragments.SearchFragment;
import com.example.netflix.util.NetworkReceiverCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {
    ViewPager2 viewPager2;

    static BottomNavigationView bottomNavigationView;

    FragmentAdapter fragmentAdapter;
    public static final int HOME_POSITION = 0;
    public static final int SEARCH_POSITION = 1;
    public static final int FAVORITE_POSITION = 2;
    public static final int PROFILE_POSITION = 3;

    public int backCounts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentAdapter = new FragmentAdapter(this);

        viewPager2 = findViewById(R.id.viewPager2);
        fragmentAdapter.add(new HomeFragment());
        fragmentAdapter.add(new SearchFragment());
        fragmentAdapter.add(new FavoriteFragment());
        fragmentAdapter.add(new ProfileFragment());

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewPager2.setAdapter(fragmentAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                selectBottomNavigationViewMenuItem(position);
            }
        });
        getSupportActionBar().hide();
    }

    public static void selectBottomNavigationViewMenuItem(int id) {
        switch (id) {
            case SEARCH_POSITION:
                bottomNavigationView.setSelectedItemId(R.id.search);
                break;
            case HOME_POSITION:
                bottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case FAVORITE_POSITION:
                bottomNavigationView.setSelectedItemId(R.id.favorite);
                break;
            case PROFILE_POSITION:
                bottomNavigationView.setSelectedItemId(R.id.profile);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                viewPager2.setCurrentItem(SEARCH_POSITION);
                break;
            case R.id.home:
                viewPager2.setCurrentItem(HOME_POSITION);
                break;
            case R.id.favorite:
                viewPager2.setCurrentItem(FAVORITE_POSITION);
                break;
            case R.id.profile:
                viewPager2.setCurrentItem(PROFILE_POSITION);
                break;
        }
        return true;
    }



    @Override
    public void onBackPressed() {
        if(backCounts == 0){
            Toast.makeText(this, "If you want to exit, Press back again!", Toast.LENGTH_SHORT).show();
            backCounts++;
        }
        else{
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }
}