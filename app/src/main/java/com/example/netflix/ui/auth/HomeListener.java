package com.example.netflix.ui.auth;

import okhttp3.Request;
import retrofit2.Response;

public abstract class HomeListener<T> {

    public abstract  void onSuccess(T response);
    public abstract void onFailure(int statusCode, String response);
    public abstract void onError(Request request, Exception e);

}
