package com.example.netflix.ui.auth.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.netflix.R;
import com.example.netflix.data.room.DatabaseCallback;
import com.example.netflix.data.room.DatabaseHandler;
import com.example.netflix.data.room.entities.FavoriteMovies;
import com.example.netflix.ui.auth.LoginActivity;
import com.example.netflix.ui.auth.adapter.FavoriteItemsAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPreferences;
    RecyclerView favoriteMoviesList;
    FavoriteItemsAdapter adapter;

    DatabaseCallback databaseCallback = new DatabaseCallback();

    public static DatabaseHandler databaseHandler;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteMoviesList = view.findViewById(R.id.favorite_movies_list);
        databaseHandler = DatabaseHandler.getInstance(getActivity());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        int userId = sharedPreferences.getInt(LoginActivity.LOGIN_USERID,0);
        List<FavoriteMovies> favoriteMovies = getFavoriteMovies(userId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        adapter = new FavoriteItemsAdapter(favoriteMovies);
        favoriteMoviesList.setAdapter(adapter);
        favoriteMoviesList.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        System.out.println("onResume: "+getClass());
    }

    public List<FavoriteMovies> getFavoriteMovies(int userId){
        return databaseCallback.getAllFavoriteMovies(databaseHandler,userId);
    }
}