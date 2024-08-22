package com.example.foodplannerapp.ui.details;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static com.example.foodplannerapp.R.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.meals.MealPlan;
import com.example.foodplannerapp.data.pojo.meals.MealsItem;
import com.example.foodplannerapp.data.repository.RepoInterface;
import com.example.foodplannerapp.ui.plan.Week;
import com.example.foodplannerapp.databinding.FragmentDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;



public class DetailsFragment extends Fragment implements DetailsInterface{

    private boolean isFavorite = false;
    private TextView detailsName;
    private TextView categoryName;
    private TextView areaName;
    private TextView ingridientsTv;
    private TextView instructionsTv;
    private AppCompatImageView addTofavBtn;
    private AppCompatButton addToPlan;
    private MealsItem mealsItem;
    private String mealId;
    private FragmentDetailsBinding binding;
    private DetailsPresenter presenter;
    private View root;
    private List<String> ingridients;
    private MealPlan mealPlan;
    private ImageView imageView;
    private YouTubePlayerView youTubePlayerView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        initUi();
        presenter = new DetailsPresenter(getContext(),this);


        mealId=DetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        presenter.getMeal(mealId, new RepoInterface<List<MealsItem>>() {


            @Override
            public void onDataSuccessResponse(List<MealsItem> data) {
                mealsItem=data.get(0);
                mealPlan=mealsItem.convertMealsItemToMealsPlan(mealsItem);
                Log.i("TAG", "meal plan obj: "+mealPlan.getStrCategory());
                detailsName.setText(mealsItem.getStrMeal());
                areaName.setText(mealsItem.getStrArea());
                categoryName.setText(mealsItem.getStrCategory());
                Log.i("TAG", "category: "+mealsItem.getStrCategory());
                instructionsTv.setText(mealsItem.getStrInstructions());
                Log.i("TAG", "instructions: "+mealsItem.getStrInstructions());
                ingridients=mealsItem.getIngredients();

                for(int i=0;i<ingridients.size();i++)
                {
                    ingridientsTv.append("."+ingridients.get(i)+"\n");
                    Log.i("TAG", "ingridients: "+ingridients.get(i));
                }
                Glide.with(requireContext()).
                        load(mealsItem.getStrMealThumb())
                        .apply(new RequestOptions().override(1920,1080).

                                placeholder(drawable.randomloadingimg).error(drawable.randomloadingimg)).
                        into(imageView);

                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String[] videoIdParts = mealsItem.getStrYoutube().split("=");
                        String videoId = videoIdParts.length > 1 ? videoIdParts[1] : null;

                        if (videoId != null && !videoId.isEmpty()) {
                            try {
                                youTubePlayer.loadVideo(videoId, 0);
                            } catch (ArrayIndexOutOfBoundsException exception) {
                                exception.printStackTrace();
                                showVideoLoadErrorDialog("Video playback error. Please try again later.");
                            }
                        } else {
                            showVideoLoadErrorDialog("Invalid video ID. Unable to load the video.");
                        }
                    }
                });

            }

            @Override
            public void onDataFailedResponse(String message) {

            }

            @Override
            public void onDataLoading() {

            }
        });

        addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserAuthenticated()) {
                    createDialog();
                } else {
                    Toast.makeText(requireContext(), "You are in Guest Mode !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addTofavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserAuthenticated()) {
                    if (!isFavorite) {
                        addTofavBtn.setImageResource(drawable.solid_favorite_24); // Replace with your solid icon
                        addToFav(mealsItem);
                        Toast.makeText(requireContext(), "Meal added to your favorites successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        addTofavBtn.setImageResource(drawable.ic_favorite_border_black_menu_24dp); //
                    }
                    isFavorite = !isFavorite;
                } else {
                    Toast.makeText(requireContext(), "You are in Guest Mode !", Toast.LENGTH_SHORT).show();
                    // Optionally, redirect to login activity
                    // startActivity(new Intent(requireContext(), LoginActivity.class));
                }
            }
        });

        return root;
    }

    private void showVideoLoadErrorDialog(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Playback Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }


    public void initUi(){
        root = binding.getRoot();
        detailsName=root.findViewById(id.nameDetailsTextView);
        categoryName=root.findViewById(id.categoryTextView);
        areaName=root.findViewById(id.countryTextView);
        addTofavBtn=root.findViewById(id.addToFavoriteButton);
        addToPlan=root.findViewById(id.addTOPlanButton);
        imageView=root.findViewById(id.mealImageView);
        ingridientsTv=root.findViewById(id.ingredientsTextView);
        instructionsTv=root.findViewById(id.instructionsTextView);
        youTubePlayerView = root.findViewById(id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


    }


    @Override
    public void onStart() {
        super.onStart();
        DetailsFragment.this.requireActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        youTubePlayerView.release();
        binding = null;
    }


    private boolean isUserAuthenticated() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    public void addToPlan(MealPlan mealPlan) {
        presenter.addToPlan(mealPlan, new RepoInterface<Void>() {
            @Override
            public void onDataSuccessResponse(Void data) {

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
    public void addToFav(MealsItem mealsItem) {
        presenter.addToFav(mealsItem, new RepoInterface<Void>() {
            @Override
            public void onDataSuccessResponse(Void data) {

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
    public void deleteFromPlan(MealPlan mealPlan) {
        presenter.deleteFromPlan(mealPlan);
    }

    @Override
    public void deleteFromFav(MealsItem mealsItem) {
        presenter.deleteFromFav(mealsItem);
    }



    public void createDialog(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());
        dialogBuilder.setIcon(R.drawable.ic_plane);
        dialogBuilder.setTitle("Pick a day that suits you");

        final ArrayAdapter<String> days = new ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice);
        days.add(Week.SATURDAY.toString());
        days.add(Week.SUNDAY.toString());
        days.add(Week.MONDAY.toString());
        days.add(Week.THURSDAY.toString());
        days.add(Week.WEDNESDAY.toString());
        days.add(Week.TUESDAY.toString());
        days.add(Week.FRIDAY.toString());

        dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialogBuilder.setAdapter(days, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("TAG", "meal plan at dialog: " + mealPlan.getStrCategory());
                if(which==0)
                {
                    mealPlan.setDay(Week.SATURDAY);
                }
                if(which==1)
                {
                    mealPlan.setDay(Week.SUNDAY);
                }
                if(which==2)
                {
                    mealPlan.setDay(Week.MONDAY);
                }
                if(which==3)
                {
                    mealPlan.setDay(Week.THURSDAY);
                }
                if(which==4)
                {
                    mealPlan.setDay(Week.WEDNESDAY);
                }
                if(which==5)
                {
                    mealPlan.setDay(Week.TUESDAY);
                }
                if(which==6)
                {
                    mealPlan.setDay(Week.FRIDAY);
                }
                addToPlan(mealPlan);
                Toast.makeText(requireContext(),"Added to plan successfully",Toast.LENGTH_SHORT).show();

            }
        });
        dialogBuilder.show();
    }
}