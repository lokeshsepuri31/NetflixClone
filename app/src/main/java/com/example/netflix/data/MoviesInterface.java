package com.example.netflix.data;

import com.example.netflix.data.pojo.Titles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface MoviesInterface {

    @GET("/titles")
    Call<Titles> getMovies();

    @GET("/titles/x/upcoming")
    Call<Titles> getUpcomingMovies();

    @GET("/titles/x/upcoming")
    Call<Titles> getSeries();

    @GET("/titles/search/akas/{aka}")
    Call<Titles> getMoviesByTitle(@Path("aka") String titleName);
}
