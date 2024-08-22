package com.example.foodplannerapp.ui.plan;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.repository.Repository;

import java.util.List;


public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysAdapter.ViewHolder>{

    public final static String TAG ="RoomDB";
    private List<Week> dayWeek;
    private PlanMealsAdapter planMealsAdapter;
    Repository repository;
    private static Context context;


    public WeekDaysAdapter(Context context, List<Week> dayOfWeek) {
        this.context = context;
        dayWeek =dayOfWeek;
        repository=Repository.getInstance(context);
    }

    @NonNull
    @Override
    public WeekDaysAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.days_plan_item,parent,false);
        return new WeekDaysAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekDaysAdapter.ViewHolder holder, int position) {
        holder.dayTxtView.setText(dayWeek.get(position).toString());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.mealsRecyclerView.setLayoutManager(linearLayoutManager);
        switch (dayWeek.get(position))
        {
            case SATURDAY:
                repository.showPlanMealsByDay(Week.SATURDAY, new RepoInterface<List<MealPlan>>() {
                    @Override
                    public void onDataSuccessResponse(List<MealPlan> data) {
                        if(data.isEmpty())
                        {
                            holder.emptyView.setVisibility(View.VISIBLE);
                        }
                        planMealsAdapter =new PlanMealsAdapter(context,data);
                        holder.mealsRecyclerView.setAdapter(planMealsAdapter);
                        Log.i(TAG, " retrieved successfully from Room "+data.size());
                    }

                    @Override
                    public void onDataFailedResponse(String message) {

                    }

                    @Override
                    public void onDataLoading() {

                    }
                });
                break;
            case SUNDAY:
                repository.showPlanMealsByDay(Week.SUNDAY, new RepoInterface<List<MealPlan>>() {
                    @Override
                    public void onDataSuccessResponse(List<MealPlan> data) {
                        if(data.isEmpty())
                        {
                            holder.emptyView.setVisibility(View.VISIBLE);
                        }
                        planMealsAdapter =new PlanMealsAdapter(context,data);
                        holder.mealsRecyclerView.setAdapter(planMealsAdapter);
                        Log.i(TAG, " retrieved successfully from Room"+data.size());
                    }

                    @Override
                    public void onDataFailedResponse(String message) {

                    }

                    @Override
                    public void onDataLoading() {

                    }
                });
                break;
            case MONDAY:
                repository.showPlanMealsByDay(Week.MONDAY, new RepoInterface<List<MealPlan>>() {
                    @Override
                    public void onDataSuccessResponse(List<MealPlan> data) {
                        if(!data.isEmpty())
                        {
                            holder.emptyView.setText(" ");
                            planMealsAdapter = new PlanMealsAdapter(context, data);
                            holder.mealsRecyclerView.setAdapter(planMealsAdapter);
                        }
                        holder.emptyView.setText("View VISIBLE");

                        Log.i(TAG, "retrieved successfully from Room "+data.size());
                    }

                    @Override
                    public void onDataFailedResponse(String message) {

                    }

                    @Override
                    public void onDataLoading() {

                    }
                });
                break;
            case TUESDAY:
                repository.showPlanMealsByDay(Week.TUESDAY, new RepoInterface<List<MealPlan>>() {
                    @Override
                    public void onDataSuccessResponse(List<MealPlan> data) {
                        if(data.isEmpty())
                        {
                            holder.emptyView.setVisibility(View.VISIBLE);
                        }
                        planMealsAdapter =new PlanMealsAdapter(context,data);
                        holder.mealsRecyclerView.setAdapter(planMealsAdapter);
                        Log.i(TAG, " retrieved successfully from Room"+data.size());
                    }

                    @Override
                    public void onDataFailedResponse(String message) {

                    }

                    @Override
                    public void onDataLoading() {

                    }
                });
                break;
            case WEDNESDAY:
                repository.showPlanMealsByDay(Week.WEDNESDAY, new RepoInterface<List<MealPlan>>() {
                    @Override
                    public void onDataSuccessResponse(List<MealPlan> data) {
                        if(data.isEmpty())
                        {
                            holder.emptyView.setVisibility(View.VISIBLE);
                        }
                        planMealsAdapter =new PlanMealsAdapter(context,data);
                        holder.mealsRecyclerView.setAdapter(planMealsAdapter);
                        Log.i(TAG, "retrieved successfully from Room"+data.size());
                    }

                    @Override
                    public void onDataFailedResponse(String message) {

                    }

                    @Override
                    public void onDataLoading() {

                    }
                });
                break;
            case THURSDAY:
                repository.showPlanMealsByDay(Week.THURSDAY, new RepoInterface<List<MealPlan>>() {
                    @Override
                    public void onDataSuccessResponse(List<MealPlan> data) {
                        if(data.isEmpty())
                        {
                            holder.emptyView.setVisibility(View.VISIBLE);
                        }
                        planMealsAdapter =new PlanMealsAdapter(context,data);
                        holder.mealsRecyclerView.setAdapter(planMealsAdapter);
                        Log.i(TAG, " retrieved successfully from Room"+data.size());
                    }

                    @Override
                    public void onDataFailedResponse(String message) {

                    }

                    @Override
                    public void onDataLoading() {

                    }
                });
                break;

            case FRIDAY:
                repository.showPlanMealsByDay(Week.FRIDAY, new RepoInterface<List<MealPlan>>() {
                    @Override
                    public void onDataSuccessResponse(List<MealPlan> data) {
                        if(data.isEmpty())
                        {
                            holder.emptyView.setVisibility(View.VISIBLE);
                        }
                        planMealsAdapter =new PlanMealsAdapter(context,data);
                        holder.mealsRecyclerView.setAdapter(planMealsAdapter);

                        Log.i(TAG, " retrieved successfully from Room"+data.size());
                    }

                    @Override
                    public void onDataFailedResponse(String message) {

                    }

                    @Override
                    public void onDataLoading() {

                    }
                });
                break;
            default:

        }


    }

    @Override
    public int getItemCount() {
        return dayWeek.size();
    }


    public void updateData(List<Week> newWeekDays) {
        this.dayWeek = newWeekDays;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dayTxtView;
        RecyclerView mealsRecyclerView;
        private TextView emptyView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTxtView=itemView.findViewById(R.id.dayTextView);
            mealsRecyclerView=itemView.findViewById(R.id.mealsRecycleView);
            emptyView = itemView.findViewById(R.id.empty_view);

        }
    }
}
