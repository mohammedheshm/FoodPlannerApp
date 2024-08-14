package com.example.foodplannerapp.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;
import com.example.foodplannerapp.databinding.FragmentHomeBinding;
import com.example.foodplannerapp.ui.common.Utils;

import java.util.List;

public class HomeFragment extends Fragment implements HomeInterface {

        private FragmentHomeBinding binding;
        private HomePresenter presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new HomePresenter(getContext(), this);
        recycleriewIngredientsSettings();
        recycleriewAreaSettings();
        recycleriewCategorySettings();
        randomMealCardSettings(view);
    }

    private void randomMealCardSettings (View view){
        ImageView imageViewSingleMeal = view.findViewById(R.id.image_thum);
        TextView foodSingleName = view.findViewById(R.id.food_name);
        TextView plane_btn = view.findViewById(R.id.plane_btn);
        ImageButton fav_btn = view.findViewById(R.id.fav_btn);
        presenter.getRandomMeals(HomePresenter.SINGLE, new DataFetch<List<MealsItem>>() {
            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                MealsItem mealsItem = data.get(0);
                Glide.with(getContext())
                        .load(mealsItem.getStrMealThumb())
                        .apply(new RequestOptions()
                                .override(400, 300)
                                .placeholder(R.drawable.randomloadingimg)
                                .error(R.drawable.ic_close_black_24dp))
                        .into(imageViewSingleMeal);

                foodSingleName.setText(mealsItem.getStrMeal());
                plane_btn.setOnClickListener(view1 -> {
                    onSavePlane(mealsItem);
                });

                fav_btn.setOnClickListener(view12 -> onSaveFavorite(mealsItem));
            }

            @Override
            public void onDataFailedResponse(String message) {

            }

            @Override
            public void onDataLoading() {

            }
        });


    }



    private void recycleriewAreaSettings () {
        RecyclerView rvRandomArea = Utils.recyclerViewHandler(binding.rvRandomArea, getContext());
        HomeAdapter homeAdapterArea = new HomeAdapter(getContext(), this);
        rvRandomArea.setAdapter(homeAdapterArea);
        presenter.getRandomMeals(HomePresenter.AREA, new DataFetch<List<MealsItem>>() {
            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                homeAdapterArea.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {

            }

            @Override
            public void onDataLoading() {

            }
        });

    }

    private void recycleriewCategorySettings () {
        RecyclerView rvRandomCategory = Utils.recyclerViewHandler(binding.rvRandomCategory, getContext());
        HomeAdapter homeAdapterCategory = new HomeAdapter(getContext(), this);
        rvRandomCategory.setAdapter(homeAdapterCategory);

        presenter.getRandomMeals(HomePresenter.CATEGORY, new DataFetch<List<MealsItem>>() {
            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                homeAdapterCategory.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {

            }

            @Override
            public void onDataLoading() {

            }
        });
        homeAdapterCategory.isHaveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean haveData) {
                if (haveData) {

                } else {

                }
            }
        });
    }


    private void recycleriewIngredientsSettings() {
        RecyclerView rvRandomIngredien = Utils.recyclerViewHandler(binding.rvRandomIngredien, getContext());
        HomeAdapter homeAdapterIngredien = new HomeAdapter(getContext(), this);
        rvRandomIngredien.setAdapter(homeAdapterIngredien);
        presenter.getRandomMeals(HomePresenter.INGREDIENT, new DataFetch<List<MealsItem>>() {
            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                binding.rvRandomIngredien.setVisibility(View.VISIBLE);
                homeAdapterIngredien.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {
                binding.rvRandomIngredien.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDataLoading() {
                binding.rvRandomIngredien.setVisibility(View.GONE);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onSavePlane (MealsItem item){
        Toast.makeText(getContext(), item.getStrMeal() + " Will Be Add to plane", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSaveFavorite (MealsItem item){
        presenter.saveFavorite(item, new DataFetch<Void>() {
            @Override
            public void onDataSuccessResponse(Void data) {
                Toast.makeText(getContext(), item.getStrMeal() + " Added To Favorite", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataFailedResponse(String message) {
                Toast.makeText(getContext(), " Error Happened: " + message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDataLoading() {

            }
        });
    }

}