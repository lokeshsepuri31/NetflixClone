package com.example.netflix.ui.auth;


public abstract class HomeListener<T> {

    public abstract  void onSuccess(T response);
    public abstract void onFailure(int statusCode, String response);

}
