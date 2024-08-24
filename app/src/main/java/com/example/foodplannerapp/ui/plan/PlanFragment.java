package com.example.foodplannerapp.ui.plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.databinding.FragmentPlanBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class PlanFragment extends Fragment implements PlanInterface {

    private FragmentPlanBinding binding;
    private WeekDaysAdapter daysAdapter;
    private PlanPresenter presenter;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();
        presenter = new PlanPresenter(getContext(), this);

        if (isUserLoggedIn()) {
            setupRecyclerView();
            presenter.loadWeekDays();
        } else {
            // Show a message if the user is not logged in
            Toast.makeText(getContext(), "You are in Guest Mode !", Toast.LENGTH_SHORT).show();
            // Optionally, i can navigate the user to a login page or hide the RecyclerView
            binding.daysRecycleView.setVisibility(View.GONE);
        }

        return view;
    }

    private boolean isUserLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.daysRecycleView.setLayoutManager(linearLayoutManager);
        daysAdapter = new WeekDaysAdapter(requireContext(), null);
        binding.daysRecycleView.setAdapter(daysAdapter);
    }

    @Override
    public void showWeekDays(List<Week> weekDays) {
        daysAdapter.updateData(weekDays);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String message) {
    }
}
