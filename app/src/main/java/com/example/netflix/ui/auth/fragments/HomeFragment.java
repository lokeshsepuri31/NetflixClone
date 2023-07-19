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
import android.widget.ProgressBar;

import com.example.netflix.R;
import com.example.netflix.data.pojo.Movies;
import com.example.netflix.ui.auth.HomeVM;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ParentItem;
import com.example.netflix.ui.auth.adapter.ParentItemAdapter;
import com.example.netflix.util.NetworkCallbackAbstract;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    HomeVM homeVM;

    List<Movies> upcomingMovies;

    List<Movies> moviesList;

    RecyclerView parentRecyclerViewItem;

    ProgressBar progressBar;

    List<ParentItem> itemList = new ArrayList<>();

    NetworkCallbackAbstract networkCallbackAbstract;

    public List<ParentItem> getItemList() {
        return this.itemList;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (homeVM == null && getActivity() != null)
            homeVM = new ViewModelProvider(getActivity()).get(HomeVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parentRecyclerViewItem = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);

        if (getActivity() != null)
            networkCallbackAbstract = new NetworkCallbackAbstract(getActivity()) {
                @Override
                public void onSuccess() {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> {
                        if (homeVM.getMovieList().getValue() == null) {
                            homeVM.getMovies();
                            homeVM.getUpcomingMovies();
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> showSnackbar(parentRecyclerViewItem, message));
                }
            };

        homeVM.getMovieList().observe(getActivity(), observe -> {
            this.moviesList = homeVM.getMovieList().getValue();
            parentItemList("Movies", this.moviesList);
            parentItemList("Episodes", this.moviesList);
            renderMovies();
        });

        homeVM.getUpcomingMovieList().observe(getActivity(), observe -> {
            this.upcomingMovies = homeVM.getUpcomingMovieList().getValue();
            parentItemList("Upcoming Movies", this.upcomingMovies);
            parentItemList("Series", this.upcomingMovies);
            renderMovies();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        networkCallbackAbstract.register(networkCallbackAbstract);
    }

    public void renderMovies() {
        progressBar.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ParentItemAdapter parentItemAdapter = new ParentItemAdapter(getItemList());
        parentRecyclerViewItem.setAdapter(parentItemAdapter);
        parentRecyclerViewItem.setLayoutManager(layoutManager);
    }

    private void parentItemList(String title, List<Movies> moviesList) {
        ParentItem item = new ParentItem(title, childItemList(moviesList));
        itemList.add(item);
    }

    private List<ChildItem> childItemList(List<Movies> moviesList) {
        List<ChildItem> childItemList = new ArrayList<>();
        for (Movies movies : moviesList) {
            if (movies.getPrimaryImage() != null && movies.getPrimaryImage().getUrl() != null
                    && movies.getPrimaryImage().getCaption().getMovieName() != null) {
                String url = movies.getPrimaryImage().getUrl();
                String movieName = movies.getOriginalText().getMovieName();
                childItemList.add(new ChildItem(movieName, url, getActivity(), movies.getId()));
            }
        }
        return childItemList;
    }


    @Override
    public void onPause() {
        super.onPause();
        networkCallbackAbstract.unRegister(networkCallbackAbstract);
    }
}