package com.example.foodplannerapp.ui.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.data.room.Week;
import com.example.foodplannerapp.databinding.FragmentHomeBinding;
import com.example.foodplannerapp.ui.common.Utils;

import java.util.List;

public class HomeFragment extends Fragment implements HomeInterface {

    private FragmentHomeBinding binding;
    private HomePresenter presenter;
    private MealPlan mealPlan;
    public boolean isFavorite = false;
    ImageView logoImage;
    TextView titleText;
    TextView subtitleText;
    private boolean isAnimationExecuted = false;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        presenter = new HomePresenter(getContext(), this);

        recycleriewIngredientsSettings();
        recycleriewAreaSettings();
        recycleriewCategorySettings();
        randomMealHandling(view);
    }

    private void recycleriewAreaSettings() {
        RecyclerView rvRandomArea = Utils.recyclerViewHandler(binding.rvRandomArea, getContext());
        HomeAdapter homeFeedAdapterArea = new HomeAdapter(getContext(), this);
        rvRandomArea.setAdapter(homeFeedAdapterArea);
        presenter.getRandomMeals(HomePresenter.AREA, new RepoInterface<List<MealsItem>>() {
            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                homeFeedAdapterArea.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {
            }

            @Override
            public void onDataLoading() {
            }
        });
    }

    private void recycleriewCategorySettings() {
        RecyclerView rvRandomCategory = Utils.recyclerViewHandler(binding.rvRandomCategory, getContext());
        HomeAdapter homeFeedAdapterCategory = new HomeAdapter(getContext(), this);
        rvRandomCategory.setAdapter(homeFeedAdapterCategory);

        presenter.getRandomMeals(HomePresenter.CATEGORY, new RepoInterface<List<MealsItem>>() {
            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                homeFeedAdapterCategory.setItemsList(data);
            }

            @Override
            public void onDataFailedResponse(String message) {
            }

            @Override
            public void onDataLoading() {
            }
        });
        homeFeedAdapterCategory.isHaveData.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean haveData) {
                if (haveData) {
                    // Handle UI updates
                } else {
                    // Handle UI updates
                }
            }
        });
    }

    private void randomMealHandling(View view) {
        ImageView imageViewSingleMeal = view.findViewById(R.id.image_thum);
        TextView foodSingleName = view.findViewById(R.id.food_name);
        TextView plane_btn = view.findViewById(R.id.plane_btn);
        ImageButton fav_btn = view.findViewById(R.id.fav_btn);
        presenter.getRandomMeals(HomePresenter.SINGLE, new RepoInterface<List<MealsItem>>() {
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
                plane_btn.setOnClickListener(view1 -> onSavePlane(mealsItem));

                isFavorite = checkIfFavorite(mealsItem);
                updateFavoriteButtonIcon(fav_btn);
                fav_btn.setOnClickListener(view12 -> {
                    isFavorite = !isFavorite;
                    updateFavoriteButtonIcon(fav_btn);
                    onSaveFavorite(mealsItem);
                });

            }

            @Override
            public void onDataFailedResponse(String message) {
            }

            @Override
            public void onDataLoading() {
            }
        });
    }

    private void updateFavoriteButtonIcon(ImageButton fav_btn) {
        fav_btn.setImageResource(isFavorite ? R.drawable.solid_favorite_24 : R.drawable.favorite_border_24);
    }

    private boolean checkIfFavorite(MealsItem item) {
        return false;
    }


    public void createDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setIcon(R.drawable.ic_plane);
        dialogBuilder.setTitle("Pick a day that suits you");

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
                case 0:
                    mealPlan.setDay(Week.SATURDAY);
                    break;
                case 1:
                    mealPlan.setDay(Week.SUNDAY);
                    break;
                case 2:
                    mealPlan.setDay(Week.MONDAY);
                    break;
                case 3:
                    mealPlan.setDay(Week.THURSDAY);
                    break;
                case 4:
                    mealPlan.setDay(Week.WEDNESDAY);
                    break;
                case 5:
                    mealPlan.setDay(Week.TUESDAY);
                    break;
                case 6:
                    mealPlan.setDay(Week.FRIDAY);
                    break;
            }
            addToPlan(mealPlan);
            Toast.makeText(requireContext(), "Added to plan successfully", Toast.LENGTH_SHORT).show();
        });
        dialogBuilder.show();
    }

    private void recycleriewIngredientsSettings() {
        RecyclerView rvRandomIngredien = Utils.recyclerViewHandler(binding.rvRandomIngredien, getContext());
        HomeAdapter homeFeedAdapterIngredien = new HomeAdapter(getContext(), this);
        rvRandomIngredien.setAdapter(homeFeedAdapterIngredien);
        presenter.getRandomMeals(HomePresenter.INGREDIENT, new RepoInterface<List<MealsItem>>() {
            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                binding.rvRandomIngredien.setVisibility(View.VISIBLE);
                homeFeedAdapterIngredien.setItemsList(data);
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

        View view = binding.getRoot();
        logoImage = binding.foodplannerlogo;
        titleText = binding.foodplannerTitle;
        subtitleText = binding.foodplannerSubtitle;


        if (!isAnimationExecuted) {
            executeAnimations(logoImage, titleText, subtitleText);
            isAnimationExecuted = true; // Mark animation as executed
        }


//        ObjectAnimator slideInImage = ObjectAnimator.ofFloat(logoImage, "translationX", -500f, 0f);
//        slideInImage.setDuration(2000);
//
//        ObjectAnimator translateTitle = ObjectAnimator.ofFloat(titleText, "translationY", -200f, 0f);
//        ObjectAnimator translateSubtitle = ObjectAnimator.ofFloat(subtitleText, "translationY", -200f, 0f);
//        translateTitle.setDuration(2000);
//        translateSubtitle.setDuration(2000);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playTogether(slideInImage, translateTitle, translateSubtitle);
//        animatorSet.start();


//        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
//        Animation translateUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_up);
//
//        logoImage.startAnimation(fadeInAnimation);
//
//        titleText.startAnimation(translateUpAnimation);
//        subtitleText.startAnimation(translateUpAnimation);


        return view;
    }

    private void executeAnimations(ImageView logoImage, TextView titleText, TextView subtitleText) {
        ObjectAnimator slideInImage = ObjectAnimator.ofFloat(logoImage, "translationX", -500f, 0f);
        slideInImage.setDuration(2000);

        ObjectAnimator translateTitle = ObjectAnimator.ofFloat(titleText, "translationY", -200f, 0f);
        ObjectAnimator translateSubtitle = ObjectAnimator.ofFloat(subtitleText, "translationY", -200f, 0f);
        translateTitle.setDuration(2000);
        translateSubtitle.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(slideInImage, translateTitle, translateSubtitle);
        animatorSet.start();
    }


    @Override
    public void onSavePlane(MealsItem item) {
        Toast.makeText(getContext(), item.getStrMeal() + " will be added to plan", Toast.LENGTH_SHORT).show();
        mealPlan = new MealPlan(item.getStrMeal(), item.getStrCategory(), item.getStrMealThumb(), item.getIdMeal());
        createDialog();
    }

    @Override
    public void onSaveFavorite(MealsItem item) {
        presenter.saveFavorite(item, new RepoInterface<Void>() {

            @Override
            public void onDataSuccessResponse(Void data) {
                Toast.makeText(getContext(), item.getStrMeal() + " added to favorite", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataFailedResponse(String message) {
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataLoading() {
            }
        });
    }

    @Override
    public void addToPlan(MealPlan mealPlan) {
        presenter.addToPlan(mealPlan, new RepoInterface<Void>() {
            @Override
            public void onDataSuccessResponse(Void data) {
                Toast.makeText(getContext(), mealPlan.getStrMeal() + " added to plan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataFailedResponse(String message) {
                Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDataLoading() {
            }
        });
    }

    @Override
    public void deleteFromPlan(MealPlan mealPlan) {
        presenter.deleteFromPlan(mealPlan);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
