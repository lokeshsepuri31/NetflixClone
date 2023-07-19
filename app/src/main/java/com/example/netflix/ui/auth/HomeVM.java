package com.example.netflix.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.netflix.data.MoviesInterface;
import com.example.netflix.data.pojo.Movies;
import com.example.netflix.data.pojo.Titles;
import com.example.netflix.util.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeVM extends ViewModel {

    public MutableLiveData<List<Movies>> moviesList = new MutableLiveData<>();

    MutableLiveData<List<Movies>> upcomingMovies = new MutableLiveData<>();

    public String example;

    public LiveData<List<Movies>> getMovieList(){
        return moviesList;
    }

    public LiveData<List<Movies>> getUpcomingMovieList(){
        return upcomingMovies;
    }

    MoviesInterface moviesInterface = RetrofitClient.getClient().create(MoviesInterface.class);

    public void getMovies(){

        Call<Titles> titlesCall = moviesInterface.getMovies();

        titlesCall.enqueue(new Callback<Titles>() {
            @Override
            public void onResponse(Call<Titles> call, Response<Titles> response) {
                Titles titles = response.body();
                if(titles.moviesList!=null)
                    moviesList.setValue(titles.moviesList);
            }

            @Override
            public void onFailure(Call<Titles> call, Throwable t) {
                titlesCall.cancel();
                moviesList.setValue(new ArrayList());
            }
        });
    }

    public void getUpcomingMovies(){

        Call<Titles> titlesCall1 = moviesInterface.getUpcomingMovies();

        titlesCall1.enqueue(new Callback<Titles>() {
            @Override
            public void onResponse(Call<Titles> call, Response<Titles> response) {
                Titles titles = response.body();
                if(titles.moviesList!=null){
                    upcomingMovies.setValue(titles.moviesList);
                }
            }

            @Override
            public void onFailure(Call<Titles> call, Throwable t) {
                titlesCall1.cancel();
                upcomingMovies.setValue(new ArrayList());
            }
        });
    }
}
