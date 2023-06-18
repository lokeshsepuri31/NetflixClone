package com.example.netflix.data.room;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.data.room.entities.Users;
import com.example.netflix.ui.auth.WatchNowActivity;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseCallback {

    public Users logIn(DatabaseHandler databaseHandler, String username) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Set<Callable<Users>> callables = new HashSet<>();

        callables.add(new Callable<Users>() {
            @Override
            public Users call() throws Exception {
                return databaseHandler.usersDAO().getUserByUsername(username);
            }
        });

        Users user = null;
        try {
            user = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return user;
    }

    public boolean signUp(DatabaseHandler databaseHandler,Users users){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Boolean>> callables = new HashSet<>();
        callables.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                databaseHandler.usersDAO().insertUser(users);
                return Boolean.TRUE;
            }
        });
        Boolean added = Boolean.FALSE;
        try {
             added = executorService.invokeAny(callables);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return added.equals(Boolean.TRUE);
    }

    public int getUserIdByUsername(DatabaseHandler databaseHandler,String username){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Integer>> callables = new HashSet<>();
        callables.add(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return databaseHandler.usersDAO().getUserIdByUsername(username);
            }
        });

        Integer userId = null;

        try{
            userId = executorService.invokeAny(callables);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return userId.intValue();
    }


    public boolean addFavMovie(DatabaseHandler databaseHandler, FavoriteMovies favoriteMovies){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Boolean>> callables = new HashSet<>();
        Handler handler = new Handler(Looper.getMainLooper());
        callables.add(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                databaseHandler.favoriteMoviesDAO().insertFavMovie(favoriteMovies);
                return true;
            }
        });

        Boolean addedMovie = false;
        try{
            addedMovie = executorService.invokeAny(callables);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        return addedMovie;
    }

}
