package com.example.netflix.util;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.netflix.R;
import com.google.android.material.snackbar.Snackbar;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkReceiverCallback {

    public static boolean isConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean isConnected = false;

        if ((nInfo != null) && nInfo.isAvailable() && nInfo.isConnected())
            isConnected = true;
        else
            isConnected = false;
        return isConnected;
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
