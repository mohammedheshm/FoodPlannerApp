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
}