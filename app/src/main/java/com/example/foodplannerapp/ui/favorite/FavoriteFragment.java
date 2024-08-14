package com.example.foodplannerapp.ui.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.data.model.meals.MealsItem;
import com.example.foodplannerapp.data.repository.DataFetch;
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
            presenter.removeFavorite(item, new DataFetch<Void>() {
                @Override
                public void onDataSuccessResponse(Void data) {
                    mealsItemList.remove(item);
                    favoriteAdapter.notifyItemRemoved(position);
                    if (mealsItemList.size() == 0)
                        showNoData();


                }

                @Override
                public void onDataFailedResponse(String message) {
                    Toast.makeText(getContext(), "Problem happened during delete "+message, Toast.LENGTH_SHORT).show();
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
        binding.noNetworkHolder.setVisibility(View.GONE);

    }

    private void showData() {
        binding.rvListFavorite.setVisibility(View.VISIBLE);
        binding.noDataHolder.setVisibility(View.GONE);
        binding.noNetworkHolder.setVisibility(View.GONE);
    }

    private void showError() {
        binding.rvListFavorite.setVisibility(View.GONE);
        binding.noDataHolder.setVisibility(View.GONE);
        binding.noNetworkHolder.setVisibility(View.VISIBLE);
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

}