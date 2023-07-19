package com.example.netflix.ui.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.netflix.data.MoviesInterface;
import com.example.netflix.data.pojo.Movies;
import com.example.netflix.data.pojo.Titles;
import com.example.netflix.util.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchVM extends ViewModel {

   public List<Movies> moviesList;

   MoviesInterface moviesInterface = RetrofitClient.getClient().create(MoviesInterface.class);

   public void getMovieNameByTitle(String movieName, HomeListener<List<Movies>> homeListener){
      Call<Titles> titlesCall = moviesInterface.getMoviesByTitle(movieName);

      titlesCall.enqueue(new Callback<Titles>() {
         @Override
         public void onResponse(@NonNull Call<Titles> call, @NonNull Response<Titles> response) {
            Titles titles = response.body();
            if(titles != null && titles.moviesList!=null)
               moviesList = titles.moviesList;
            homeListener.onSuccess(moviesList);
         }
         @Override
         public void onFailure(@NonNull Call<Titles> call, @NonNull Throwable t) {
            titlesCall.cancel();
            homeListener.onFailure(call.hashCode(),t.getMessage());
         }
      });
   }

}
