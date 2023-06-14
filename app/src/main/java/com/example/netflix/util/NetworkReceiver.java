package com.example.netflix.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.netflix.ui.auth.ErrorActivity;


public class NetworkReceiver extends BroadcastReceiver {
    Activity activity;

    public NetworkReceiver(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (activity != null) {
                Intent restartIntent = new Intent(activity, activity.getClass());
                restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(restartIntent);
            }
        } else {
            context.startActivity(new Intent(context, ErrorActivity.class));
        }
    }
}

