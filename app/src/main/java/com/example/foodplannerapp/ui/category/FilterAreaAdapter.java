package com.example.foodplannerapp.ui.category;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.data.pojo.countries.Area;
import com.example.foodplannerapp.ui.common.Utils;
import com.example.foodplannerapp.ui.search.SearchInterface;

import java.util.ArrayList;
import java.util.List;


public class FilterAreaAdapter extends RecyclerView.Adapter<FilterAreaAdapter.ViewHolder> {
    private List<Area> itemsList = new ArrayList<>();
    public MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<Boolean>(false);
    CategoryInterface categoryInterface;
    private Context context;

    public FilterAreaAdapter(Context context, CategoryInterface categoryInterface) {
        this.context = context;
        this.categoryInterface = categoryInterface;
    }

    @NonNull
    @Override
    public FilterAreaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_area, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAreaAdapter.ViewHolder holder, int position) {
        Area item = itemsList.get(position);
        holder.title.setText(item.getStrArea());
        Utils.loadImage(context, item.getThumbnail(), holder.thumnailView);

        holder.linearLayout.setOnClickListener(view -> {
            Utils.navigatorCategoryToSearchFragment(view, SearchInterface.AREA, item.getStrArea());
        });
    }

    public void setItemsList(List<Area> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
        mutableLiveData.postValue(itemsList.size() > 0);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumnailView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            thumnailView = itemView.findViewById(R.id.profile_image);
            linearLayout = itemView.findViewById(R.id.item_holder);
        }
    }


}
