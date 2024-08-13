package com.example.foodplannerapp.data.repository;

public interface DataFetch<T> {
    public void onDataSuccessResponse(T data);
    public void onDataFailedResponse(String message);
    public void onDataLoading();
}
