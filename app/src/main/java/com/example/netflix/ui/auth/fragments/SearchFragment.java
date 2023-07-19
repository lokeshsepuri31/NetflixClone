package com.example.netflix.ui.auth.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.pojo.Movies;
import com.example.netflix.ui.auth.HomeListener;
import com.example.netflix.ui.auth.SearchVM;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.SearchAdapter;
import com.example.netflix.util.NetworkCallbackAbstract;
import com.example.netflix.util.NetworkReceiverCallback;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    EditText search_movies;

    SearchVM searchVM;

    RecyclerView movieListRecyclerView;

    List<Movies> moviesList;

    NetworkCallbackAbstract networkCallbackAbstract;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (searchVM == null && getActivity() != null)
            searchVM = new ViewModelProvider(getActivity()).get(SearchVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search_movies = view.findViewById(R.id.search_movies);
        movieListRecyclerView = view.findViewById(R.id.search_recycler_view);
        search_movies.requestFocus();
        if (getContext() != null)
            search_movies.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE && NetworkReceiverCallback.isConnection(getContext())) {
                    if (!search_movies.getText().toString().equals(""))
                        searchMovie(search_movies.getText().toString());
                    else
                        Toast.makeText(getActivity(), "Please provide a movie name!", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    NetworkReceiverCallback.showSnackbar(search_movies);
                }
                return false;
            });

        if (getActivity() != null)
            networkCallbackAbstract = new NetworkCallbackAbstract(getActivity()) {
                @Override
                public void onSuccess() {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        if (searchVM.moviesList != null)
                            renderMovieSearch(searchVM.moviesList);
                    });
                }

                @Override
                public void onFailure(String message) {

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        if (searchVM.moviesList != null)
                            renderMovieSearch(searchVM.moviesList);
                        showSnackbar(movieListRecyclerView, message);
                    });
                }
            };
    }

    @Override
    public void onResume() {
        super.onResume();
        networkCallbackAbstract.register(networkCallbackAbstract);
    }

    public void searchMovie(String movie) {
        searchVM.getMovieNameByTitle(movie, new HomeListener<List<Movies>>() {
            @Override
            public void onSuccess(List<Movies> response) {
                renderMovieSearch(response);
            }

            @Override
            public void onFailure(int statusCode, String response) {
                Toast.makeText(getActivity(), "No Search result found!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void renderMovieSearch(List<Movies> moviesList) {
        this.moviesList = moviesList;
        if (moviesList.size() != 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            SearchAdapter searchAdapter = new SearchAdapter(searchAdapterItems());
            movieListRecyclerView.setAdapter(searchAdapter);
            movieListRecyclerView.setLayoutManager(layoutManager);
        } else
            Toast.makeText(getActivity(), "No results found!", Toast.LENGTH_SHORT).show();
    }

    public List<ChildItem> searchAdapterItems() {
        List<ChildItem> searchItemList = new ArrayList<>();
        for (Movies movies : moviesList) {
            if (movies.getPrimaryImage() != null && movies.getPrimaryImage().getUrl() != null
                    && movies.getPrimaryImage().getCaption().getMovieName() != null) {
                String url = movies.getPrimaryImage().getUrl();
                String movieName = movies.getOriginalText().getMovieName();
                searchItemList.add(new ChildItem(movieName, url, getActivity(), movies.getId()));
            }
        }
        return searchItemList;
    }

    @Override
    public void onPause() {
        super.onPause();
        networkCallbackAbstract.unRegister(networkCallbackAbstract);
    }
}