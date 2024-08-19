package com.example.foodplannerapp.ui.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.example.foodplannerapp.data.pojo.category.Category;
import com.example.foodplannerapp.data.pojo.countries.Area;
import com.example.foodplannerapp.data.pojo.ingredient.Ingredient;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.databinding.FragmentCategoryBinding;
import com.example.foodplannerapp.ui.common.Utils;
import com.example.foodplannerapp.ui.search.SearchInterface;

import java.util.List;


public class CategoryFragment extends Fragment implements CategoryInterface {


    private FragmentCategoryBinding binding;
    private CategoryPresenter categoryPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryPresenter = new CategoryPresenter(getContext());

        setUpRecyclerCategory();
        setUpRecyclerArea();
        setUpRecyclerIngredients();
        navigateToShowAll();


    }
    private void setUpRecyclerArea() {
        RecyclerView recyclerViewArea = Utils.recyclerViewHandler(binding.rvAreas, getContext());
        FilterAreaAdapter filterAreaAdapter = new FilterAreaAdapter(getContext(), this);
        recyclerViewArea.setAdapter(filterAreaAdapter);
        categoryPresenter.getFilterAreaResults(new RepoInterface<List<Area>>() {
            @Override
            public void onDataSuccessResponse(List<Area> data) {
                filterAreaAdapter.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {

            }

            @Override
            public void onDataLoading() {

            }
        });


    }

    private void setUpRecyclerIngredients() {
        RecyclerView recyclerViewIngredient = Utils.recyclerViewHandler(binding.rvIngredient, getContext());
        FilterIngredientAdapter filterIngredientAdapter = new FilterIngredientAdapter(getContext(), this);
        recyclerViewIngredient.setAdapter(filterIngredientAdapter);
        categoryPresenter.getFilterIngredientResults(new RepoInterface<List<Ingredient>>() {
            @Override
            public void onDataSuccessResponse(List<Ingredient> data) {
                filterIngredientAdapter.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {

            }

            @Override
            public void onDataLoading() {

            }
        });
    }

    private void setUpRecyclerCategory() {
        RecyclerView recyclerViewCategory = Utils.recyclerViewHandler(binding.rvCategory, getContext());
        FilterCategoryAdapter filterCategoryAdapter = new FilterCategoryAdapter(getContext(), this);
        recyclerViewCategory.setAdapter(filterCategoryAdapter);

        categoryPresenter.getFilterCategoryResults(new RepoInterface<List<Category>>() {
            @Override
            public void onDataSuccessResponse(List<Category> data) {
                filterCategoryAdapter.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {

            }

            @Override
            public void onDataLoading() {

            }
        });
    }

    @Override
    public void onItemClicked() {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    private void navigateToShowAll() {
        binding.seeMoreArea.setOnClickListener(view -> {
            Utils.navigatorCategoryToSearchFragment(view, SearchInterface.AREA, "");
        });
        binding.seeMoreCategory.setOnClickListener(view -> {
            Utils.navigatorCategoryToSearchFragment(view, SearchInterface.CATEGORY, "");
        });
        binding.seeMoreIngredient.setOnClickListener(view -> {
            Utils.navigatorCategoryToSearchFragment(view, SearchInterface.INGREDIENT, "");
        });
        binding.searchView.setOnClickListener(view -> {
            Utils.navigatorCategoryToSearchFragment(view, SearchInterface.SEARCH, "");
        });

    }
}