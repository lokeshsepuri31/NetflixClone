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
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.pojo.Movies;
import com.example.netflix.ui.auth.HomeListener;
import com.example.netflix.ui.auth.HomeVM;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ParentItem;
import com.example.netflix.ui.auth.adapter.ParentItemAdapter;
import com.example.netflix.util.NetworkCallbackAbstract;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class HomeFragment extends Fragment {

    HomeVM homeVM;

    List<Movies> upcomingMovies;

    RecyclerView parentRecyclerViewItem;

    ProgressBar progressBar;

    NetworkCallbackAbstract networkCallbackAbstract;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (homeVM == null)
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

        networkCallbackAbstract = new NetworkCallbackAbstract(getActivity()) {
            @Override
            public void onSuccess() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    if (homeVM.moviesList == null)
                        getMovies();
                    else
                        renderMovies(homeVM.moviesList);
                });
            }

            @Override
            public void onFailure(String message) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    if (homeVM.moviesList != null)
                        renderMovies(homeVM.moviesList);
                    showSnackbar(parentRecyclerViewItem, message);

                });
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        networkCallbackAbstract.register(networkCallbackAbstract);
    }

    public void getMovies() {
        homeVM.getMovies(new HomeListener<List<Movies>>() {
            @Override
            public void onSuccess(List<Movies> response) {
                renderMovies(response);
            }

            @Override
            public void onFailure(int statusCode, String response) {
                Toast.makeText(getActivity(), "" + response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void renderMovies(List<Movies> upcomingMovies) {
        this.upcomingMovies = upcomingMovies;
        progressBar.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ParentItemAdapter parentItemAdapter = new ParentItemAdapter(parentItemList());
        parentRecyclerViewItem.setAdapter(parentItemAdapter);
        parentRecyclerViewItem.setLayoutManager(layoutManager);
    }

    private List<ParentItem> parentItemList() {
        List<ParentItem> itemList = new ArrayList<>();
        ParentItem item = new ParentItem("Movies", childItemList(upcomingMovies));
        itemList.add(item);
        ParentItem item1 = new ParentItem("Upcoming Movies", childItemList(upcomingMovies));
        itemList.add(item1);
        ParentItem item2 = new ParentItem("Series", childItemList(upcomingMovies));
        itemList.add(item2);
        return itemList;
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