package com.example.foodplannerapp.ui.plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodplannerapp.data.room.Week;
import com.example.foodplannerapp.databinding.FragmentPlanBinding;
import java.util.List;

public class PlanFragment extends Fragment implements PlanInterface {

    private FragmentPlanBinding binding;
    private WeekDaysAdapter daysAdapter;
    private PlanPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        presenter = new PlanPresenter(getContext(), this);

        setupRecyclerView();
        presenter.loadWeekDays();

        return view;
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
