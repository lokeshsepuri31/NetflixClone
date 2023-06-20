package com.example.netflix.ui.auth.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.netflix.R;
import com.example.netflix.data.pojo.Movies;
import com.example.netflix.ui.auth.HomeActivity;
import com.example.netflix.ui.auth.HomeListener;
import com.example.netflix.ui.auth.HomeVM;
import com.example.netflix.ui.auth.adapter.ChildItem;
import com.example.netflix.ui.auth.adapter.ParentItem;
import com.example.netflix.ui.auth.adapter.ParentItemAdapter;
import com.example.netflix.util.NetworkReceiverCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    HomeVM homeVM;

    List<Movies> upcomingMovies;

    RecyclerView parentRecyclerViewItem;

    ProgressBar progressBar;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentRecyclerViewItem = view.findViewById(R.id.recycler_view);
        homeVM = new ViewModelProvider(getActivity()).get(HomeVM.class);
        progressBar = view.findViewById(R.id.progress_bar);
        getMovies();
    }

    public void getMovies(){
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

    public void renderMovies(List<Movies> upcomingMovies){
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
        ParentItem item1 = new ParentItem("Upcoming Movies",childItemList(upcomingMovies));
        itemList.add(item1);
        ParentItem item2 = new ParentItem("Series",childItemList(upcomingMovies));
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
                childItemList.add(new ChildItem(movieName,url,getActivity(),movies.getId()));
            }
        }
        return childItemList;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!NetworkReceiverCallback.isConnection(getActivity()) && upcomingMovies == null){
            NetworkReceiverCallback.showSnackbar(parentRecyclerViewItem);
            HomeActivity.selectBottomNavigationViewMenuItem(HomeActivity.FAVORITE_POSITION);
        } else if (NetworkReceiverCallback.isConnection(getActivity()) && upcomingMovies == null) {
            getMovies();
        }
    }
}