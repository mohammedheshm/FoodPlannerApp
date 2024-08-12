package com.example.foodplannerapp.ui.boarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.ui.sign_in_with_google.SignUpOrLoginActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class BoardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    TabLayout tabLayout;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.food_type_three, "Organize your weekly meal plan\n" +
                "better with us ", "Simply click to add any meal you like to your plan"));
        sliderItems.add(new SliderItem(R.drawable.food_type_two, "Savor your lunchtime", "Sit back and relax we've got your meals covered. Our personalized meal plans are tailored to your needs, so you don’t have to worry about what to eat"));

        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        tabLayout = findViewById(R.id.tabLayoutIndicator);
        nextBtn = findViewById(R.id.nextButton);

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Meal Plan");
                    break;
                case 1:
                    tab.setText("Lunch Time");
                    break;
                default:
                    tab.setText("Tab " + (position + 1));
                    break;
            }
        }).attach();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoardingActivity.this, SignUpOrLoginActivity.class));
                finish();
            }
        });
    }
}
