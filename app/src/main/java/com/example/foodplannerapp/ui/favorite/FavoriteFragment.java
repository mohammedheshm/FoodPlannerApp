package com.example.foodplannerapp.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.databinding.FragmentFavoriteBinding;
import com.example.foodplannerapp.ui.common.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteInterface {
    private FragmentFavoriteBinding binding;
    private FavoritePresenter presenter;
    private FavoriteAdapter favoriteAdapter;
    private List<MealsItem> mealsItemList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        presenter = new FavoritePresenter(getContext(), this);

        View view = binding.getRoot();

        checkUserStatusAndSetUpUI();

        return view;
    }

    private void checkUserStatusAndSetUpUI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // User is a guest, show message and hide the favorite list
            showGuestUI();
        } else {
            // User is logged in, show the favorite list
            recyclerViewSetUp();
            presenter.getFavorites(this);
        }
    }

    private void showGuestUI() {
        Toast.makeText(getContext(), "You are in Guest Mode!", Toast.LENGTH_SHORT).show();
        binding.rvListFavorite.setVisibility(View.GONE);
        // binding.guestMessageHolder.setVisibility(View.VISIBLE);
        binding.noDataHolder.setVisibility(View.GONE);
    }

    private void recyclerViewSetUp() {
        recyclerView = Utils.recyclerViewHandler(binding.rvListFavorite, getContext());
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
                    Toast.makeText(getContext(), "Error Happened " + message, Toast.LENGTH_SHORT).show();
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
        if (data.size() == 0) {
            showNoData();
        } else {
            showData();
            mealsItemList.clear();
            mealsItemList.addAll(data);
            favoriteAdapter.notifyDataSetChanged();
        }
    }

    private void showNoData() {
        binding.rvListFavorite.setVisibility(View.GONE);
        binding.noDataHolder.setVisibility(View.VISIBLE);
        // binding.guestMessageHolder.setVisibility(View.GONE);
    }

    private void showData() {
        binding.rvListFavorite.setVisibility(View.VISIBLE);
        binding.noDataHolder.setVisibility(View.GONE);
        // binding.guestMessageHolder.setVisibility(View.GONE);
    }

    @Override
    public void onDataFailedResponse(String message) {
        showError();
        Toast.makeText(getContext(), "Error Happened " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataLoading() {

    }

    private void showError() {
        binding.rvListFavorite.setVisibility(View.GONE);
        binding.noDataHolder.setVisibility(View.GONE);
        // binding.guestMessageHolder.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void addToPlan(MealPlan mealPlan) {
        // Implement adding to plan logic here
    }

    @Override
    public void deleteFromPlan(MealPlan mealPlan) {
        // Implement deleting from plan logic here
    }
}
