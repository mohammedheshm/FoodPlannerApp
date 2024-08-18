package com.example.foodplannerapp.ui.search;

import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;

import java.util.List;

public interface SearchInterface extends DataFetch<List<MealsItem>> {
}
