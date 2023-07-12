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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.pojo.Movies;
import com.example.netflix.ui.auth.HomeListener;
import com.example.netflix.ui.auth.SearchVM;
import com.example.netflix.ui.auth.adapter.SearchAdapter;
import com.example.netflix.ui.auth.adapter.SearchItem;
import com.example.netflix.util.NetworkCallbackAbstract;
import com.example.netflix.util.NetworkReceiverCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText search_movies;

    SearchVM searchVM;

    RecyclerView movieListRecyclerView;

    List<Movies> moviesList;

    NetworkCallbackAbstract networkCallbackAbstract;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(searchVM == null)
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
        search_movies.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
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
            }
        });

        networkCallbackAbstract = new NetworkCallbackAbstract(getActivity()) {
            @Override
            public void onSuccess() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (searchVM.moviesList == null) {

                        }else{
                            renderMovieSearch(searchVM.moviesList);
                        }
                    }
                });
            }

            @Override
            public void onFailure(String message) {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (searchVM.moviesList != null)
                            renderMovieSearch(searchVM.moviesList);
                        showSnackbar(movieListRecyclerView, message);
                    }
                });
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        networkCallbackAbstract.register(networkCallbackAbstract);
    }

    public void searchMovie(String movie){
        searchVM.getMovieNameByTitle(movie, new HomeListener<List<Movies>>() {
            @Override
            public void onSuccess(List<Movies> response) {
                renderMovieSearch(response);
            }

            @Override
            public void onFailure(int statusCode, String response) {
                Toast.makeText(getActivity(), "No Search result found!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Request request, Exception e) {

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

    public List<SearchItem> searchAdapterItems() {
        List<SearchItem> searchItemList = new ArrayList<>();
        for (Movies movies : moviesList) {
            if (movies.getPrimaryImage() != null && movies.getPrimaryImage().getUrl() != null
                    && movies.getPrimaryImage().getCaption().getMovieName() != null) {
                String url = movies.getPrimaryImage().getUrl();
                String movieName = movies.getOriginalText().getMovieName();
                searchItemList.add(new SearchItem(movieName, url, getActivity(), movies.getId()));
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