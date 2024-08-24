package com.example.foodplannerapp.ui.plan;


import java.util.List;

public interface PlanInterface {
    void showWeekDays(List<Week> weekDays);

    void showLoading();

    void hideLoading();

    void showError(String message);
}
