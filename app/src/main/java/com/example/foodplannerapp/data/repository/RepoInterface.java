package com.example.foodplannerapp.data.repository;

public interface RepoInterface<T> {
   //FetchData
    public void onDataSuccessResponse(T data);
    public void onDataFailedResponse(String message);
    public void onDataLoading();
}
