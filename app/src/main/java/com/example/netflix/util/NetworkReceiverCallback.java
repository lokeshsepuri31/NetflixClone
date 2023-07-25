package com.example.netflix.util;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class NetworkReceiverCallback {

    public static boolean isConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean isConnected;

        if ((nInfo != null) && nInfo.isAvailable() && nInfo.isConnected())
            isConnected = true;
        else
            isConnected = false;
        return isConnected && hasInternetAccess(cm);
    }

    private static boolean hasInternetAccess(ConnectivityManager cm) {
        Network activeNetwork = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            activeNetwork = cm.getActiveNetwork();
        }
        if (activeNetwork != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        return false;
    }



    public static void showSnackbar(View view){
        Snackbar snackbar = Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG);
        View v = snackbar.getView();
        snackbar.setBackgroundTint(Color.BLACK);
        TextView textView = new TextView(v.getContext());
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

}
