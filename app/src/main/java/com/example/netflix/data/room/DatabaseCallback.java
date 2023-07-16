package com.example.netflix.data.room;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.data.room.entities.Users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseCallback {

    public Users logIn(DatabaseHandler databaseHandler, String username) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Set<Callable<Users>> callables = new HashSet<>();

        callables.add(() -> databaseHandler.usersDAO().getUserByUsername(username));

        Users user = null;
        try {
            user = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return user;
    }

    public boolean signUp(DatabaseHandler databaseHandler, Users users) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Boolean>> callables = new HashSet<>();
        callables.add(() -> {
            databaseHandler.usersDAO().insertUser(users);
            return Boolean.TRUE;
        });
        Boolean added = Boolean.FALSE;
        try {
            added = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return added.equals(Boolean.TRUE);
    }

    public int getUserIdByUsername(DatabaseHandler databaseHandler, String username) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Integer>> callables = new HashSet<>();
        callables.add(() -> databaseHandler.usersDAO().getUserIdByUsername(username));

        Integer userId = 0;

        try {
            userId = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return userId;
    }


    public boolean addFavMovie(DatabaseHandler databaseHandler, FavoriteMovies favoriteMovies) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Boolean>> callables = new HashSet<>();
        callables.add(() -> {
            databaseHandler.favoriteMoviesDAO().insertFavMovie(favoriteMovies);
            return true;
        });

        Boolean addedMovie = false;
        try {
            addedMovie = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return addedMovie;
    }

    public boolean isFavMovieAlreadyInLoggedInUser(DatabaseHandler databaseHandler, String id, int userId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Boolean>> callables = new HashSet<>();
        callables.add(() -> {
            FavoriteMovies favoriteMovies1 = databaseHandler.favoriteMoviesDAO().getFavMovieOfLoggedInUser(id, userId);
            return favoriteMovies1 != null;

        });
        Boolean isMovieAlreadyThere = false;
        try {
            isMovieAlreadyThere = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return isMovieAlreadyThere;
    }

    public List<FavoriteMovies> getAllFavoriteMovies(DatabaseHandler databaseHandler, int userId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<List<FavoriteMovies>>> callables = new HashSet<>();
        callables.add(() -> databaseHandler.favoriteMoviesDAO().getAllFavMoviesOfLoggedInUser(userId));
        List<FavoriteMovies> favoriteMovies = null;
        try {
            favoriteMovies = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return favoriteMovies;
    }

    public boolean deleteFavMovie(DatabaseHandler databaseHandler, int userId, String id) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Set<Callable<Boolean>> callables = new HashSet<>();
        callables.add(() -> databaseHandler.favoriteMoviesDAO().deleteFavMovie(userId, id) > 0);

        Boolean isMovieDeleted = false;
        try {
            isMovieDeleted = executorService.invokeAny(callables);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return isMovieDeleted;

    }

}
