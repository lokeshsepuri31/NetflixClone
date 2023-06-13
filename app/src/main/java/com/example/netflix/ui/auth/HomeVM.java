package com.example.netflix.ui.auth;

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

    List<Movies> moviesList;

    List<Movies> upcomingMovies;

    List<Movies> series;

    List<String> movieIds = new ArrayList<>();
    MoviesInterface moviesInterface = RetrofitClient.getClient().create(MoviesInterface.class);

    public void getMovies(HomeListener<List<Movies>> homeListener){

        Call<Titles> titlesCall = moviesInterface.getMovies();

        titlesCall.enqueue(new Callback<Titles>() {
            @Override
            public void onResponse(Call<Titles> call, Response<Titles> response) {
                Titles titles = response.body();
                if(titles.moviesList!=null)
                    moviesList = titles.moviesList;
                homeListener.onSuccess(moviesList);
            }

            @Override
            public void onFailure(Call<Titles> call, Throwable t) {
                titlesCall.cancel();
                homeListener.onFailure(call.hashCode(),t.getMessage());
            }
        });
    }

    public void getUpcomingMovies(List<Movies>moviesList,HomeListener homeListener){

        Call<Titles> titlesCall1 = moviesInterface.getUpcomingMovies();

        titlesCall1.enqueue(new Callback<Titles>() {
            @Override
            public void onResponse(Call<Titles> call, Response<Titles> response) {
                Titles titles = response.body();
                if(titles.moviesList!=null){
                    upcomingMovies = titles.moviesList;
                }
                upcomingMovies.addAll(moviesList);
            }

            @Override
            public void onFailure(Call<Titles> call, Throwable t) {
                titlesCall1.cancel();
                homeListener.onFailure(call.hashCode(),t.getMessage());
            }
        });
    }
}
