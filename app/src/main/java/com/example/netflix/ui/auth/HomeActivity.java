package com.example.netflix.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.ui.auth.adapter.FragmentAdapter;
import com.example.netflix.ui.auth.fragments.FavoriteFragment;
import com.example.netflix.ui.auth.fragments.HomeFragment;
import com.example.netflix.ui.auth.fragments.NoInternetFragment;
import com.example.netflix.ui.auth.fragments.ProfileFragment;
import com.example.netflix.ui.auth.fragments.SearchFragment;
import com.example.netflix.ui.auth.fragments.WatchNowFragment;
import com.example.netflix.util.NetworkReceiverCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;

@SuppressWarnings("ALL")
public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ViewPager2 viewPager2;

    public static BottomNavigationView bottomNavigationView;

    Instant startTime;

    FragmentAdapter fragmentAdapter;
    public static final int HOME_POSITION = 0;
    public static final int SEARCH_POSITION = 1;
    public static final int FAVORITE_POSITION = 2;
    public static final int PROFILE_POSITION = 3;

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

        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(getSupportActionBar() != null)
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
        switch (item.getItemId()) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant endTime = Instant.now();
            if(startTime == null){
                startTime = Instant.now();
                Toast.makeText(this, "If you want to exit back press again!", Toast.LENGTH_SHORT).show();
            }else {
                Duration duration = Duration.between(startTime, endTime);
                if(duration.toMillis() < 10000){
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                }else {
                    startTime = endTime;
                    Toast.makeText(this, "If you want to exit back press again!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void openWatchNowFragment(String fragment,WatchNowFragment watchNowFragment){
        if(fragment.equals("Home"))
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, watchNowFragment)
                .addToBackStack("watch now").commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_search,watchNowFragment)
                    .addToBackStack("watch now").commit();
    }

    public void onBack(){
        getSupportFragmentManager().popBackStack("watch now",FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    public void noInternet(){
        NoInternetFragment noInternetFragment = new NoInternetFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home,noInternetFragment,"No Internet")
                .addToBackStack("No Internet")
                .commit();
    }

    public void backToOnline(){
        Fragment noInternetFragment = getSupportFragmentManager().findFragmentByTag("No Internet");
            if(noInternetFragment != null)
                getSupportFragmentManager().popBackStack("No Internet",FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}