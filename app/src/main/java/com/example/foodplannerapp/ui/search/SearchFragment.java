package com.example.foodplannerapp.ui.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;
import com.example.foodplannerapp.databinding.FragmentSearchBinding;
import com.example.foodplannerapp.ui.common.Utils;

import java.util.List;


public class SearchFragment extends Fragment implements SearchInterface{

    private FragmentSearchBinding binding;
    private SearchPresenter presenter;
    private RecyclerView recycler_Search;
    private SearchAdapter searchAdapter;

    private int type;
    private String query=  "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        presenter = new SearchPresenter(getContext(),this);
        recycler_Search = Utils.recyclerViewHandler(binding.rvSearch, getContext());
        searchAdapter = new SearchAdapter(getContext(), this);
        recycler_Search.setAdapter(searchAdapter);

        type = SearchFragmentArgs.fromBundle(getArguments()).getType();
        query = SearchFragmentArgs.fromBundle(getArguments()).getSearch();
        Utils.setAutoCompleteCash(getContext(), presenter.getCashList(type), binding.searchView);


        checkQueryType();
        return binding.getRoot();
    }

    private void checkQueryType() {
        if (!query.isEmpty()){
            binding.searchView.setText(query);
            presenter.getSearchResultMeals(type,query);
        }else{
            presenter.getSearchResultMeals(type, presenter.getRandomList(type));
        }

        if (type == SEARCH){
            binding.searchView.requestFocus();
            binding.searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    presenter.getSearchResultMeals(type,charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else{
            binding.searchView.showDropDown();
            binding.searchView.setOnItemClickListener((adapterView, view, i, l) -> {
                query = binding.searchView.getText().toString().trim();
                if (!query.isEmpty())
                    presenter.getSearchResultMeals(type,query);
            });

            binding.searchView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().isEmpty())
                        binding.searchView.showDropDown();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }


    }


    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }


    @Override
    public void onDataSuccessResponse(List<MealsItem> data) {
        if (data.size()==0){
            binding.rvSearch.setVisibility(View.GONE);
            binding.noNetworkHolder.setVisibility(View.GONE);
            binding.noDataHolder.setVisibility(View.VISIBLE);
        }else{
            binding.rvSearch.setVisibility(View.VISIBLE);
            binding.noNetworkHolder.setVisibility(View.GONE);
            binding.noDataHolder.setVisibility(View.GONE);
            searchAdapter.setItemsList(data);
        }

    }

    @Override
    public void onDataFailedResponse(String message) {
        binding.rvSearch.setVisibility(View.GONE);
        binding.noNetworkHolder.setVisibility(View.VISIBLE);
        binding.noDataHolder.setVisibility(View.GONE);
    }

    @Override
    public void onDataLoading() {
        binding.rvSearch.setVisibility(View.GONE);
        binding.noNetworkHolder.setVisibility(View.GONE);
        binding.noDataHolder.setVisibility(View.GONE);
    }

    @Override
    public void onSavePlane(MealsItem item) {

    }

    @Override
    public void onSaveFavorite(MealsItem item) {
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