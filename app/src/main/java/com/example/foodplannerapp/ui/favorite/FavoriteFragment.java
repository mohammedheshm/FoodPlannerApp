
package com.example.foodplannerapp.ui.favorite;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.room.Week;
import com.example.foodplannerapp.databinding.FragmentFavoriteBinding;
import com.example.foodplannerapp.ui.common.Utils;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteInterface{
    private static final String TAG = "FavoriteFragment";
    private FragmentFavoriteBinding binding;
    private FavoritePresenter presenter;
    private FavoriteAdapter favoriteAdapter;
    private List<MealsItem> mealsItemList = new ArrayList<>();
    private  RecyclerView recyclerView;
    private MealPlan mealPlan;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        presenter = new FavoritePresenter(getContext(),this);

        View view = binding.getRoot();

        recyclerViewSetUp();
        presenter.getFavorites(this);

        return view;
    }

    private void recyclerViewSetUp() {
        recyclerView = Utils.recyclerViewHandler(binding.rvListFavorite,getContext());
        favoriteAdapter = new FavoriteAdapter(getContext(), mealsItemList, (item, position) -> {
            presenter.removeFavorite(item, new RepoInterface<Void>() {
                @Override
                public void onDataSuccessResponse(Void data) {
                    mealsItemList.remove(item);
                    favoriteAdapter.notifyItemRemoved(position);
                    if (mealsItemList.size() == 0)
                        showNoData();


                }

                @Override
                public void onDataFailedResponse(String message) {
                    Toast.makeText(getContext(), "Error Happened "+message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDataLoading() {

                }
            });
        });
        recyclerView.setAdapter(favoriteAdapter);
    }

    @Override
    public void onDataSuccessResponse(List<MealsItem> data) {
        if (data.size() == 0){
            showNoData();
        }else{
            showData();
            mealsItemList.clear();
            mealsItemList.addAll(data);
            favoriteAdapter.notifyDataSetChanged();
        }

    }

    private void showNoData() {
        binding.rvListFavorite.setVisibility(View.GONE);
        binding.noDataHolder.setVisibility(View.VISIBLE);

    }

    private void showData() {
        binding.rvListFavorite.setVisibility(View.VISIBLE);
        binding.noDataHolder.setVisibility(View.GONE);
    }

    private void showError() {
        binding.rvListFavorite.setVisibility(View.GONE);
        binding.noDataHolder.setVisibility(View.GONE);
    }

    public void createDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setIcon(R.drawable.ic_plane);
        dialogBuilder.setTitle("Choose any day you want");

        final ArrayAdapter<String> days = new ArrayAdapter<>(requireContext(), android.R.layout.select_dialog_singlechoice);
        days.add(Week.SATURDAY.toString());
        days.add(Week.SUNDAY.toString());
        days.add(Week.MONDAY.toString());
        days.add(Week.THURSDAY.toString());
        days.add(Week.WEDNESDAY.toString());
        days.add(Week.TUESDAY.toString());
        days.add(Week.FRIDAY.toString());

        dialogBuilder.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

        dialogBuilder.setAdapter(days, (dialog, which) -> {
            switch (which) {
                case 0: mealPlan.setDay(Week.SATURDAY); break;
                case 1: mealPlan.setDay(Week.SUNDAY); break;
                case 2: mealPlan.setDay(Week.MONDAY); break;
                case 3: mealPlan.setDay(Week.THURSDAY); break;
                case 4: mealPlan.setDay(Week.WEDNESDAY); break;
                case 5: mealPlan.setDay(Week.TUESDAY); break;
                case 6: mealPlan.setDay(Week.FRIDAY); break;
            }
            addToPlan(mealPlan);
            Toast.makeText(requireContext(), "Added to plan successfully", Toast.LENGTH_SHORT).show();
        });
        dialogBuilder.show();
    }


    @Override
    public void onDataFailedResponse(String message) {
        showError();
        Toast.makeText(getContext(), "Error Happened "+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataLoading() {
        Toast.makeText(getContext(), "Waitting data is loading...", Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void addToPlan(MealPlan mealPlan) {

    }

    @Override
    public void deleteFromPlan(MealPlan mealPlan) {

    }
}

