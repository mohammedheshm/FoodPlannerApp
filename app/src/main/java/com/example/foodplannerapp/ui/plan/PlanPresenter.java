package com.example.foodplannerapp.ui.plan;

import android.content.Context;

import com.example.foodplannerapp.data.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class PlanPresenter {

    private final PlanInterface planInterface;
    private final Repository repository;

    public PlanPresenter(Context context, PlanInterface planInterface) {
        this.planInterface = planInterface;
        repository = Repository.getInstance(context);
    }

    public void loadWeekDays() {
        planInterface.showLoading();
        List<Week> weekDays = new ArrayList<>();
        weekDays.add(Week.SATURDAY);
        weekDays.add(Week.SUNDAY);
        weekDays.add(Week.MONDAY);
        weekDays.add(Week.TUESDAY);
        weekDays.add(Week.WEDNESDAY);
        weekDays.add(Week.THURSDAY);
        weekDays.add(Week.FRIDAY);

        planInterface.showWeekDays(weekDays);
        planInterface.hideLoading();
    }
}
