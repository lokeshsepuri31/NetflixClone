package com.example.netflix.util;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

public abstract class NetworkCallbackAbstract extends ConnectivityManager.NetworkCallback {

    ConnectivityManager cm;
    NetworkRequest networkRequest;

    public NetworkCallbackAbstract(Context context){
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        onSuccess();
    }

    @Override
    public void onUnavailable() {
        super.onUnavailable();
        onFailure("Something went wrong!");
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        onFailure("No Internet Connection!");
    }

    public abstract void onSuccess();

    public abstract void onFailure(String message);

    public void register(NetworkCallbackAbstract networkCallbackAbstract){
        cm.registerNetworkCallback(this.networkRequest,networkCallbackAbstract);
    }

    public void unRegister(NetworkCallbackAbstract networkCallbackAbstract){
        cm.unregisterNetworkCallback(networkCallbackAbstract);
    }

    public static void showSnackbar(View view,String message){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View v = snackbar.getView();
        snackbar.setBackgroundTint(Color.BLACK);
        TextView textView = new TextView(v.getContext());
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}

