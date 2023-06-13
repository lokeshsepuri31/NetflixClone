package com.example.netflix.data;

import com.example.netflix.data.pojo.Titles;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface MoviesInterface {

    @Headers({"X-RapidAPI-Key:b9dc4e53d0msh5ec9d0aa590aa00p17a594jsn7c891b7f6507","X-RapidAPI-Host:moviesdatabase.p.rapidapi.com"})
    @GET("/titles")
    Call<Titles> getMovies();

    @Headers({"X-RapidAPI-Key:b9dc4e53d0msh5ec9d0aa590aa00p17a594jsn7c891b7f6507","X-RapidAPI-Host:moviesdatabase.p.rapidapi.com"})
    @GET("/titles/x/upcoming")
    Call<Titles> getUpcomingMovies();

    @Headers({"X-RapidAPI-Key:b9dc4e53d0msh5ec9d0aa590aa00p17a594jsn7c891b7f6507","X-RapidAPI-Host:moviesdatabase.p.rapidapi.com"})
    @GET("/titles/x/upcoming")
    Call<Titles> getSeries();

    @Headers({"X-RapidAPI-Key:b9dc4e53d0msh5ec9d0aa590aa00p17a594jsn7c891b7f6507","X-RapidAPI-Host:moviesdatabase.p.rapidapi.com"})
    @GET("/titles/search/akas/{aka}")
    Call<Titles> getMoviesByTitle(@Path("aka") String titleName);
}
